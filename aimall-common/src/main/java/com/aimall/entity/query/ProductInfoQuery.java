package com.aimall.entity.query;

import java.math.BigDecimal;
import java.util.List;


/**
 * 鍟嗗搧淇℃伅鍙傛暟
 */
public class ProductInfoQuery extends BaseParam {


    /**
     * 鍟嗗搧ID
     */
    private String productId;

    private String productIdFuzzy;

    /**
     * 鍟嗗搧鍚嶇О
     */
    private String productName;

    private String productNameFuzzy;

    /**
     * 鍟嗗搧鎻忚堪
     */
    private String productDesc;

    private String productDescFuzzy;

    /**
     * 灏侀潰
     */
    private String cover;

    private String coverFuzzy;

    /**
     * 鍒涘缓鏃堕棿
     */
    private String createTime;

    private String createTimeStart;

    private String createTimeEnd;

    /**
     * 鍒嗙被ID
     */
    private String categoryId;

    private String categoryIdFuzzy;

    /**
     * 鍒嗙被鐖禝D
     */
    private String pCategoryId;

    private String pCategoryIdFuzzy;

    /**
     * -1:宸插垹闄?0:涓嬫灦  1:涓婃灦
     */
    private Integer status;

    /**
     * 鏈€浣庝环鏍?
     */
    private BigDecimal minPrice;

    /**
     * 鏈€楂樹环鏍?
     */
    private BigDecimal maxPrice;

    /**
     * 閿€閲?
     */
    private Integer totalSale;

    /**
     * 0:鏈帹鑽?1:宸茬粡鎺ㄨ崘
     */
    private Integer commendType;


    private String categoryIdOrPCategoryId;

    private List<String> productIdList;

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

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductNameFuzzy(String productNameFuzzy) {
        this.productNameFuzzy = productNameFuzzy;
    }

    public String getProductNameFuzzy() {
        return this.productNameFuzzy;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductDesc() {
        return this.productDesc;
    }

    public void setProductDescFuzzy(String productDescFuzzy) {
        this.productDescFuzzy = productDescFuzzy;
    }

    public String getProductDescFuzzy() {
        return this.productDescFuzzy;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCover() {
        return this.cover;
    }

    public void setCoverFuzzy(String coverFuzzy) {
        this.coverFuzzy = coverFuzzy;
    }

    public String getCoverFuzzy() {
        return this.coverFuzzy;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTimeStart(String createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public String getCreateTimeStart() {
        return this.createTimeStart;
    }

    public void setCreateTimeEnd(String createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public String getCreateTimeEnd() {
        return this.createTimeEnd;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryIdFuzzy(String categoryIdFuzzy) {
        this.categoryIdFuzzy = categoryIdFuzzy;
    }

    public String getCategoryIdFuzzy() {
        return this.categoryIdFuzzy;
    }

    public void setpCategoryId(String pCategoryId) {
        this.pCategoryId = pCategoryId;
    }

    public String getpCategoryId() {
        return this.pCategoryId;
    }

    public void setpCategoryIdFuzzy(String pCategoryIdFuzzy) {
        this.pCategoryIdFuzzy = pCategoryIdFuzzy;
    }

    public String getpCategoryIdFuzzy() {
        return this.pCategoryIdFuzzy;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMinPrice() {
        return this.minPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public BigDecimal getMaxPrice() {
        return this.maxPrice;
    }

    public void setTotalSale(Integer totalSale) {
        this.totalSale = totalSale;
    }

    public Integer getTotalSale() {
        return this.totalSale;
    }

    public void setCommendType(Integer commendType) {
        this.commendType = commendType;
    }

    public Integer getCommendType() {
        return this.commendType;
    }

    public String getCategoryIdOrPCategoryId() {
        return categoryIdOrPCategoryId;
    }

    public void setCategoryIdOrPCategoryId(String categoryIdOrPCategoryId) {
        this.categoryIdOrPCategoryId = categoryIdOrPCategoryId;
    }

    public List<String> getProductIdList() {
        return productIdList;
    }

    public void setProductIdList(List<String> productIdList) {
        this.productIdList = productIdList;
    }
}

