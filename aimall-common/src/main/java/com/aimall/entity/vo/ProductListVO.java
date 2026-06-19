package com.aimall.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

public class ProductListVO {

    /**
     * 鍟嗗搧ID
     */
    private String productId;

    /**
     * 鍟嗗搧鍚嶇О
     */
    private String productName;

    /**
     * 灏侀潰
     */
    private String cover;

    /**
     * 鍒涘缓鏃堕棿
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 0:涓嬫灦  1:涓婃灦
     */
    private Integer status;

    /**
     * 鏈€浣庝环
     */
    private BigDecimal minPrice;

    /**
     * 鏈€楂樹环
     */
    private BigDecimal maxPrice;

    /**
     * 鎬诲簱瀛?
     */
    private Integer totalStock;

    /**
     * sku鏁伴噺
     */
    private Integer skuCount;

    /**
     * 鎬婚攢鍞暟閲?
     */
    private Integer totalSale;

    /**
     * 鍒嗙被鍚嶇О
     */
    private String categoryName;

    private Integer commendType;

    public Integer getCommendType() {
        return commendType;
    }

    public void setCommendType(Integer commendType) {
        this.commendType = commendType;
    }

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getSkuCount() {
        return skuCount;
    }

    public void setSkuCount(Integer skuCount) {
        this.skuCount = skuCount;
    }

    public Integer getTotalSale() {
        return totalSale;
    }

    public void setTotalSale(Integer totalSale) {
        this.totalSale = totalSale;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(Integer totalStock) {
        this.totalStock = totalStock;
    }
}

