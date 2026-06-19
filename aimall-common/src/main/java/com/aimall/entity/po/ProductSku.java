package com.aimall.entity.po;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 *
 */
public class ProductSku implements Serializable {


    /**
     * 鍟嗗搧ID
     */
    private String productId;

    /**
     * 灞炴€у€糹d缁?
     */
    @NotEmpty
    private String propertyValueIdHash;


    @NotEmpty
    private String propertyValueIds;

    /**
     * 浠锋牸
     */
    @NotEmpty
    private BigDecimal price;

    /**
     * 搴撳瓨
     */
    private Integer stock;

    /**
     * 鎺掑簭
     */
    @NotNull
    private Integer sort;

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return this.productId;
    }

    public String getPropertyValueIdHash() {
        return propertyValueIdHash;
    }

    public void setPropertyValueIdHash(String propertyValueIdHash) {
        this.propertyValueIdHash = propertyValueIdHash;
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

    public String getPropertyValueIds() {
        return propertyValueIds;
    }

    public void setPropertyValueIds(String propertyValueIds) {
        this.propertyValueIds = propertyValueIds;
    }
}

