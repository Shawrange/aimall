package com.aimall.service;

import com.aimall.entity.dto.ProductSaveDTO;
import com.aimall.entity.po.ProductInfo;
import com.aimall.entity.query.ProductInfoQuery;
import com.aimall.entity.vo.PaginationResultVO;
import com.aimall.entity.vo.ProductInfoDetailVO;
import com.aimall.entity.vo.ProductListVO;

import java.util.List;


/**
 * 涓氬姟鎺ュ彛
 */
public interface ProductInfoService {

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    List<ProductInfo> findListByParam(ProductInfoQuery param);

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    Integer findCountByParam(ProductInfoQuery param);

    /**
     * 鍒嗛〉鏌ヨ
     */
    PaginationResultVO<ProductInfo> findListByPage(ProductInfoQuery param);

    /**
     * 鏂板
     */
    Integer add(ProductInfo bean);

    /**
     * 鎵归噺鏂板
     */
    Integer addBatch(List<ProductInfo> listBean);

    /**
     * 鎵归噺鏂板/淇敼
     */
    Integer addOrUpdateBatch(List<ProductInfo> listBean);

    /**
     * 澶氭潯浠舵洿鏂?
     */
    Integer updateByParam(ProductInfo bean, ProductInfoQuery param);

    /**
     * 澶氭潯浠跺垹闄?
     */
    Integer deleteByParam(ProductInfoQuery param);

    /**
     * 鏍规嵁ProductId鏌ヨ瀵硅薄
     */
    ProductInfo getProductInfoByProductId(String productId);


    /**
     * 鏍规嵁ProductId淇敼
     */
    Integer updateProductInfoByProductId(ProductInfo bean, String productId);


    /**
     * 鏍规嵁ProductId鍒犻櫎
     */
    Integer deleteProductInfoByProductId(String productId);

    void saveProduct(ProductSaveDTO productSaveDTO);

    ProductInfoDetailVO getProductInfo(String productId);

    PaginationResultVO<ProductListVO> findListByPage4ListVO(ProductInfoQuery param);

    void updateStatus(String productId, Integer status);

    void deleteProduct(String productId);
}
