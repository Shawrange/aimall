package com.aimall.component;

import com.aimall.entity.dto.ProductInfoDTO;
import com.aimall.entity.vo.PaginationResultVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EsSearchComponentTest {

    @Mock
    private ElasticsearchOperations elasticsearchOperations;

    @InjectMocks
    private EsSearchComponent esSearchComponent;

    @Test
    void searchProductsUsesBroadRecallQueryForShortKeyword() {
        ProductInfoDTO phone = product("P1001", "手机支架", "19.90", 120);
        mockSearchHits(1, phone);

        PaginationResultVO<ProductInfoDTO> result = esSearchComponent.searchProducts(
                "手机",
                new BigDecimal("10"),
                new BigDecimal("30"),
                "asc",
                "price",
                2
        );

        CriteriaQuery query = capturedQuery();
        String criteria = dumpCriteria(query.getCriteria());

        assertThat(criteria)
                .contains("productName")
                .contains("手机")
                .contains("*手机*")
                .contains("minPrice");
        assertThat(query.getPageable().getPageNumber()).isEqualTo(1);
        assertThat(query.getPageable().getPageSize()).isEqualTo(15);
        assertThat(query.getPageable().getSort().getOrderFor("minPrice").getDirection()).isEqualTo(Sort.Direction.ASC);
        assertThat(result.getTotalCount()).isEqualTo(1);
        assertThat(result.getPageNo()).isEqualTo(2);
        assertThat(result.getList()).containsExactly(phone);
    }

    @Test
    void searchProductsUsesNormalNameQueryForLongKeyword() {
        ProductInfoDTO headset = product("P1002", "无线蓝牙耳机", "129.00", 88);
        mockSearchHits(1, headset);

        PaginationResultVO<ProductInfoDTO> result = esSearchComponent.searchProducts(
                "无线蓝牙耳机",
                null,
                null,
                "desc",
                "sale",
                1
        );

        CriteriaQuery query = capturedQuery();
        String criteria = dumpCriteria(query.getCriteria());

        assertThat(criteria)
                .contains("productName")
                .contains("无线蓝牙耳机")
                .doesNotContain("*无线蓝牙耳机*");
        assertThat(query.getPageable().getSort().getOrderFor("totalSale").getDirection()).isEqualTo(Sort.Direction.DESC);
        assertThat(result.getPageTotal()).isEqualTo(1);
    }

    @Test
    void searchProductsFallsBackToDefaultSortWhenSortParamsAreUnknown() {
        mockSearchHits(0);

        PaginationResultVO<ProductInfoDTO> result = esSearchComponent.searchProducts(
                "电脑",
                null,
                null,
                "unknown",
                "unknown",
                null
        );

        CriteriaQuery query = capturedQuery();

        assertThat(query.getPageable().getPageNumber()).isZero();
        assertThat(query.getPageable().getSort().getOrderFor("_score").getDirection()).isEqualTo(Sort.Direction.DESC);
        assertThat(result.getPageNo()).isEqualTo(1);
        assertThat(result.getList()).isEmpty();
    }

    @Test
    void searchProductsReturnsEmptyPageWhenElasticsearchFails() {
        when(elasticsearchOperations.search(org.mockito.ArgumentMatchers.any(CriteriaQuery.class), eq(ProductInfoDTO.class)))
                .thenThrow(new RuntimeException("es unavailable"));

        PaginationResultVO<ProductInfoDTO> result = esSearchComponent.searchProducts(
                "平板",
                null,
                null,
                "desc",
                "composite",
                3
        );

        assertThat(result.getTotalCount()).isZero();
        assertThat(result.getPageSize()).isEqualTo(15);
        assertThat(result.getPageNo()).isEqualTo(3);
        assertThat(result.getPageTotal()).isZero();
        assertThat(result.getList()).isEmpty();
    }

    private void mockSearchHits(long totalHits, ProductInfoDTO... products) {
        SearchHits<ProductInfoDTO> searchHits = mock(SearchHits.class);
        List<SearchHit<ProductInfoDTO>> hits = java.util.Arrays.stream(products)
                .map(product -> {
                    SearchHit<ProductInfoDTO> hit = mock(SearchHit.class);
                    when(hit.getContent()).thenReturn(product);
                    return hit;
                })
                .toList();
        when(searchHits.getSearchHits()).thenReturn(hits);
        when(searchHits.getTotalHits()).thenReturn(totalHits);
        when(elasticsearchOperations.search(org.mockito.ArgumentMatchers.any(CriteriaQuery.class), eq(ProductInfoDTO.class)))
                .thenReturn(searchHits);
    }

    private CriteriaQuery capturedQuery() {
        ArgumentCaptor<CriteriaQuery> queryCaptor = ArgumentCaptor.forClass(CriteriaQuery.class);
        verify(elasticsearchOperations).search(queryCaptor.capture(), eq(ProductInfoDTO.class));
        return queryCaptor.getValue();
    }

    private String dumpCriteria(Criteria criteria) {
        StringBuilder dump = new StringBuilder();
        for (Criteria item : criteria.getCriteriaChain()) {
            dumpCriteriaItem(item, dump);
        }
        return dump.toString();
    }

    private void dumpCriteriaItem(Criteria criteria, StringBuilder dump) {
        if (criteria.getField() != null) {
            dump.append(criteria.getField().getName()).append(' ');
        }
        criteria.getQueryCriteriaEntries().forEach(entry -> dump
                .append(entry.getKey())
                .append('=')
                .append(entry.getValue())
                .append(' '));
        criteria.getSubCriteria().forEach(subCriteria -> dumpCriteriaItem(subCriteria, dump));
        criteria.getCriteriaChain().stream()
                .filter(chainItem -> chainItem != criteria)
                .forEach(chainItem -> dumpCriteriaItem(chainItem, dump));
    }

    private ProductInfoDTO product(String productId, String productName, String minPrice, Integer totalSale) {
        ProductInfoDTO product = new ProductInfoDTO();
        product.setProductId(productId);
        product.setProductName(productName);
        product.setMinPrice(new BigDecimal(minPrice));
        product.setTotalSale(totalSale);
        return product;
    }
}
