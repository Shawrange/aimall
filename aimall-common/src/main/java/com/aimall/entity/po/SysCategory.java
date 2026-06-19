package com.aimall.entity.po;

import java.io.Serializable;
import java.util.List;


/**
 *
 */
public class SysCategory implements Serializable {


    /**
     * 鍒嗙被ID
     */
    private String categoryId;

    /**
     * 鍒嗙被鍚嶇О
     */
    private String categoryName;

    /**
     * 鐖禝D
     */
    private String pCategoryId;

    /**
     * 鎺掑簭鍙?
     */
    private Integer sort;

    private List<SysCategory> children;

    private List<SysProductProperty> productPropertyList;

    public List<SysProductProperty> getProductPropertyList() {
        return productPropertyList;
    }

    public void setProductPropertyList(List<SysProductProperty> productPropertyList) {
        this.productPropertyList = productPropertyList;
    }

    public List<SysCategory> getChildren() {
        return children;
    }

    public void setChildren(List<SysCategory> children) {
        this.children = children;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setpCategoryId(String pCategoryId) {
        this.pCategoryId = pCategoryId;
    }

    public String getpCategoryId() {
        return this.pCategoryId;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getSort() {
        return this.sort;
    }

    @Override
    public String toString() {
        return "鍒嗙被ID:" + (categoryId == null ? "绌? : categoryId) + "锛屽垎绫诲悕绉?" + (categoryName == null ? "绌? : categoryName) + "锛岀埗ID:" + (pCategoryId == null ? "绌? :
                pCategoryId) + "锛屾帓搴忓彿:" + (sort == null ? "绌? : sort);
    }
}

