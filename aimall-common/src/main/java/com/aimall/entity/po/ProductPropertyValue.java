package com.aimall.entity.po;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;


/**
 *
 */
public class ProductPropertyValue implements Serializable {


    /**
     * 鍟嗗搧ID
     */
    private String productId;

    /**
     * 灞炴€D
     */
    @NotEmpty
    private String propertyId;

    /**
     * 灞炴€у悕绉?
     */
    @NotEmpty
    private String propertyName;

    /**
     * 灞炴€ф帓搴?
     */
    @NotEmpty
    private Integer propertySort;

    /**
     * 0:鏃犻渶浼犲皝闈?1:闇€浼犲皝闈?
     */
    @NotNull
    private Integer coverType;

    /**
     * 灞炴€D
     */
    @NotEmpty
    private String propertyValueId;

    /**
     * 灞炴€у皝闈?
     */
    private String propertyCover;

    /**
     * 灞炴€у€?
     */
    @NotEmpty
    private String propertyValue;

    /**
     * 澶囨敞
     */
    private String propertyRemark;

    /**
     * 灞炴€у€兼帓搴?
     */
    private Integer sort;


    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return this.productId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getPropertyId() {
        return this.propertyId;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return this.propertyName;
    }

    public void setPropertySort(Integer propertySort) {
        this.propertySort = propertySort;
    }

    public Integer getPropertySort() {
        return this.propertySort;
    }

    public void setCoverType(Integer coverType) {
        this.coverType = coverType;
    }

    public Integer getCoverType() {
        return this.coverType;
    }

    public void setPropertyValueId(String propertyValueId) {
        this.propertyValueId = propertyValueId;
    }

    public String getPropertyValueId() {
        return this.propertyValueId;
    }

    public void setPropertyCover(String propertyCover) {
        this.propertyCover = propertyCover;
    }

    public String getPropertyCover() {
        return this.propertyCover;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public String getPropertyValue() {
        return this.propertyValue;
    }

    public void setPropertyRemark(String propertyRemark) {
        this.propertyRemark = propertyRemark;
    }

    public String getPropertyRemark() {
        return this.propertyRemark;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getSort() {
        return this.sort;
    }

    @Override
    public String toString() {
        return "鍟嗗搧ID:" + (productId == null ? "绌? : productId) + "锛屽睘鎬D:" + (propertyId == null ? "绌? : propertyId) + "锛屽睘鎬у悕绉?" + (propertyName == null ? "绌? :
                propertyName) + "锛屽睘鎬ф帓搴?" + (propertySort == null ? "绌? : propertySort) + "锛?:鏃犻渶浼犲皝闈?1:闇€浼犲皝闈?" + (coverType == null ? "绌? : coverType) + "锛宲ropertyValueId" +
                ":" + (propertyValueId == null ? "绌? : propertyValueId) + "锛屽睘鎬у皝闈?" + (propertyCover == null ? "绌? : propertyCover) + "锛屽睘鎬у€?" + (propertyValue == null ?
                "绌? : propertyValue) + "锛屽娉?" + (propertyRemark == null ? "绌? : propertyRemark) + "锛屽睘鎬у€兼帓搴?" + (sort == null ? "绌? : sort);
    }
}

