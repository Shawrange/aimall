package com.aimall.entity.query;



/**
 * 鍙傛暟
 */
public class SysProductPropertyQuery extends BaseParam {


	/**
	 * 灞炴€D
	 */
	private String propertyId;

	private String propertyIdFuzzy;

	/**
	 * 灞炴€у悕绉?
	 */
	private String propertyName;

	private String propertyNameFuzzy;

	/**
	 * 涓€绾у垎绫?
	 */
	private String pCategoryId;

	private String pCategoryIdFuzzy;

	/**
	 * 浜岀骇鍒嗙被
	 */
	private String categoryId;

	private String categoryIdFuzzy;

	/**
	 * 鎺掑簭
	 */
	private Integer sort;

	/**
	 * 0:鏃犻渶浼犲皝闈?1:闇€浼犲皝闈?
	 */
	private Integer coverType;


	public void setPropertyId(String propertyId){
		this.propertyId = propertyId;
	}

	public String getPropertyId(){
		return this.propertyId;
	}

	public void setPropertyIdFuzzy(String propertyIdFuzzy){
		this.propertyIdFuzzy = propertyIdFuzzy;
	}

	public String getPropertyIdFuzzy(){
		return this.propertyIdFuzzy;
	}

	public void setPropertyName(String propertyName){
		this.propertyName = propertyName;
	}

	public String getPropertyName(){
		return this.propertyName;
	}

	public void setPropertyNameFuzzy(String propertyNameFuzzy){
		this.propertyNameFuzzy = propertyNameFuzzy;
	}

	public String getPropertyNameFuzzy(){
		return this.propertyNameFuzzy;
	}

	public void setpCategoryId(String pCategoryId){
		this.pCategoryId = pCategoryId;
	}

	public String getpCategoryId(){
		return this.pCategoryId;
	}

	public void setpCategoryIdFuzzy(String pCategoryIdFuzzy){
		this.pCategoryIdFuzzy = pCategoryIdFuzzy;
	}

	public String getpCategoryIdFuzzy(){
		return this.pCategoryIdFuzzy;
	}

	public void setCategoryId(String categoryId){
		this.categoryId = categoryId;
	}

	public String getCategoryId(){
		return this.categoryId;
	}

	public void setCategoryIdFuzzy(String categoryIdFuzzy){
		this.categoryIdFuzzy = categoryIdFuzzy;
	}

	public String getCategoryIdFuzzy(){
		return this.categoryIdFuzzy;
	}

	public void setSort(Integer sort){
		this.sort = sort;
	}

	public Integer getSort(){
		return this.sort;
	}

	public void setCoverType(Integer coverType){
		this.coverType = coverType;
	}

	public Integer getCoverType(){
		return this.coverType;
	}

}

