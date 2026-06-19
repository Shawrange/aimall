package com.aimall.entity.po;

import com.aimall.entity.enums.DateTimePatternEnum;
import com.aimall.entity.valid.UpdateGroup;
import com.aimall.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 鍟嗗搧淇℃伅
 */
public class ProductInfo implements Serializable {

    /**
     * 鍟嗗搧ID
     */
    @NotEmpty(groups = {UpdateGroup.class})
    private String productId;

    /**
     * 鍟嗗搧鍚嶇О
     */
    @NotEmpty
    private String productName;

    /**
     * 鍟嗗搧鎻忚堪
     */
    @NotEmpty
    private String productDesc;

    /**
     * 灏侀潰
     */
    @NotEmpty
    private String cover;

    /**
     * 鍒涘缓鏃堕棿
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 鍒嗙被ID
     */
    @NotEmpty
    private String categoryId;

    /**
     * 鍒嗙被鐖禝D
     */
    @NotEmpty
    private String pCategoryId;

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


    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return this.productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductDesc() {
        return this.productDesc;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCover() {
        return this.cover;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public void setpCategoryId(String pCategoryId) {
        this.pCategoryId = pCategoryId;
    }

    public String getpCategoryId() {
        return this.pCategoryId;
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

    @Override
    public String toString() {
        return "鍟嗗搧ID:" + (productId == null ? "绌? : productId) + "锛屽晢鍝佸悕绉?" + (productName == null ? "绌? : productName) + "锛屽晢鍝佹弿杩?" + (productDesc == null ? "绌? :
                productDesc) + "锛屽皝闈?" + (cover == null ? "绌? : cover) + "锛屽垱寤烘椂闂?" + (createTime == null ? "绌? : DateUtil.format(createTime,
                DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern())) + "锛屽垎绫籌D:" + (categoryId == null ? "绌? : categoryId) + "锛屽垎绫荤埗ID:" + (pCategoryId == null ? "绌? :
                pCategoryId) + "锛?1:宸插垹闄?0:涓嬫灦  1:涓婃灦:" + (status == null ? "绌? : status) + "锛屾渶浣庝环鏍?" + (minPrice == null ? "绌? : minPrice) + "锛屾渶楂樹环鏍?" + (maxPrice == null
                ? "绌? : maxPrice) + "锛岄攢閲?" + (totalSale == null ? "绌? : totalSale) + "锛?:鏈帹鑽?1:宸茬粡鎺ㄨ崘:" + (commendType == null ? "绌? : commendType);
    }
}

