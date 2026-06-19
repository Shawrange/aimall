package com.aimall.entity.query;

import java.math.BigDecimal;
import java.util.List;


/**
 * 鍙傛暟
 */
public class ProductSkuQuery extends BaseParam {


    /**
     * 鍟嗗搧ID
     */
    private String productId;

    private String productIdFuzzy;

    /**
     * 灞炴€у€糹d缁刪ash
     */
    private String propertyValueIdHash;

    private String propertyValueIdHashFuzzy;

    /**
     * 灞炴€у€糹d缁?
     */
    private String propertyValueIds;

    private String propertyValueIdsFuzzy;

    /**
     * 浠锋牸
     */
    private BigDecimal price;

    /**
     * 搴撳瓨
     */
    private Integer stock;

    /**
     * 鎺掑簭
     */
    private Integer sort;

    private List<String> productIdList;

    /**
     * 鍓╀綑搴撳瓨
     */
    private Integer lessStock;

    public Integer getLessStock() {
        return lessStock;
    }

    public void setLessStock(Integer lessStock) {
        this.lessStock = lessStock;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return this.productId;
    }

    public void setProductIdFuzzy(String productIdFuzzy) {
        this.productIdFuzzy = productIdFuzzy;
    }

    public String getProductIdFuzzy() {
        return this.productIdFuzzy;
    }

    public void setPropertyValueIdHash(String propertyValueIdHash) {
        this.propertyValueIdHash = propertyValueIdHash;
    }

    public String getPropertyValueIdHash() {
        return this.propertyValueIdHash;
    }

    public void setPropertyValueIdHashFuzzy(String propertyValueIdHashFuzzy) {
        this.propertyValueIdHashFuzzy = propertyValueIdHashFuzzy;
    }

    public String getPropertyValueIdHashFuzzy() {
        return this.propertyValueIdHashFuzzy;
    }

    public void setPropertyValueIds(String propertyValueIds) {
        this.propertyValueIds = propertyValueIds;
    }

    public String getPropertyValueIds() {
        return this.propertyValueIds;
    }

    public void setPropertyValueIdsFuzzy(String propertyValueIdsFuzzy) {
        this.propertyValueIdsFuzzy = propertyValueIdsFuzzy;
    }

    public String getPropertyValueIdsFuzzy() {
        return this.propertyValueIdsFuzzy;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStock() {
        return this.stock;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getSort() {
        return this.sort;
    }

    public List<String> getProductIdList() {
        return productIdList;
    }

    public void setProductIdList(List<String> productIdList) {
        this.productIdList = productIdList;
    }
}

