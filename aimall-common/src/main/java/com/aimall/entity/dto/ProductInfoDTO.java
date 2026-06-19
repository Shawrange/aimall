package com.aimall.entity.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;

@Document(indexName = "aimall-index")
public class ProductInfoDTO {

    /**
     * 鍟嗗搧ID
     */
    @Field(type = FieldType.Keyword)
    @Id
    private String productId;

    /**
     * 鍟嗗搧鍚嶇О
     */
    @Field(type = FieldType.Text,
            analyzer = "ik_max_word",
            searchAnalyzer = "ik_smart",
            fielddata = true)
    private String productName;

    /**
     * 灏侀潰
     */
    @Field(type = FieldType.Keyword, index = false)
    private String cover;


    /**
     * 鏈€浣庝环鏍?
     */
    @Field(type = FieldType.Double)
    private BigDecimal minPrice;

    /**
     * 鏈€楂樹环鏍?
     */
    @Field(type = FieldType.Double)
    private BigDecimal maxPrice;

    /**
     * 閿€閲?
     */
    @Field(type = FieldType.Integer)
    private Integer totalSale;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Integer getTotalSale() {
        return totalSale;
    }

    public void setTotalSale(Integer totalSale) {
        this.totalSale = totalSale;
    }
}

