package com.aimall.entity.query;


/**
 * 鍙傛暟
 */
public class SysCategoryQuery extends BaseParam {


    /**
     * 鍒嗙被ID
     */
    private String categoryId;

    private String categoryIdFuzzy;

    /**
     * 鍒嗙被鍚嶇О
     */
    private String categoryName;

    private String categoryNameFuzzy;

    /**
     * 鐖禝D
     */
    private String pCategoryId;

    private String pCategoryIdFuzzy;

    /**
     * 鎺掑簭鍙?
     */
    private Integer sort;

    private String categoryIdOrPCategoryId;

    private Boolean queryProperty;

    private Boolean convert2Tree = true;

    public Boolean getConvert2Tree() {
        return convert2Tree;
    }

    public void setConvert2Tree(Boolean convert2Tree) {
        this.convert2Tree = convert2Tree;
    }

    public Boolean getQueryProperty() {
        return queryProperty;
    }

    public void setQueryProperty(Boolean queryProperty) {
        this.queryProperty = queryProperty;
    }

    public String getCategoryIdOrPCategoryId() {
        return categoryIdOrPCategoryId;
    }

    public void setCategoryIdOrPCategoryId(String categoryIdOrPCategoryId) {
        this.categoryIdOrPCategoryId = categoryIdOrPCategoryId;
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

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryNameFuzzy(String categoryNameFuzzy) {
        this.categoryNameFuzzy = categoryNameFuzzy;
    }

    public String getCategoryNameFuzzy() {
        return this.categoryNameFuzzy;
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

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getSort() {
        return this.sort;
    }

}

