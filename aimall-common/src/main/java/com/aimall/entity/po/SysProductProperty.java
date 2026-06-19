package com.aimall.entity.po;

import java.io.Serializable;


/**
 * 
 */
public class SysProductProperty implements Serializable {


	/**
	 * 灞炴€D
	 */
	private String propertyId;

	/**
	 * 灞炴€у悕绉?
	 */
	private String propertyName;

	/**
	 * 涓€绾у垎绫?
	 */
	private String pCategoryId;

	/**
	 * 浜岀骇鍒嗙被
	 */
	private String categoryId;

	/**
	 * 鎺掑簭
	 */
	private Integer propertySort;

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

	public void setPropertyName(String propertyName){
		this.propertyName = propertyName;
	}

	public String getPropertyName(){
		return this.propertyName;
	}

	public void setpCategoryId(String pCategoryId){
		this.pCategoryId = pCategoryId;
	}

	public String getpCategoryId(){
		return this.pCategoryId;
	}

	public void setCategoryId(String categoryId){
		this.categoryId = categoryId;
	}

	public String getCategoryId(){
		return this.categoryId;
	}

	public void setPropertySort(Integer propertySort){
		this.propertySort = propertySort;
	}

	public Integer getPropertySort(){
		return this.propertySort;
	}

	public void setCoverType(Integer coverType){
		this.coverType = coverType;
	}

	public Integer getCoverType(){
		return this.coverType;
	}

	@Override
	public String toString (){
		return "灞炴€D:"+(propertyId == null ? "绌? : propertyId)+"锛屽睘鎬у悕绉?"+(propertyName == null ? "绌? : propertyName)+"锛屼竴绾у垎绫?"+(pCategoryId == null ? "绌? : pCategoryId)+"锛屼簩绾у垎绫?"+(categoryId == null ? "绌? : categoryId)+"锛屾帓搴?"+(propertySort == null ? "绌? : propertySort)+"锛?:鏃犻渶浼犲皝闈?1:闇€浼犲皝闈?"+(coverType == null ? "绌? : coverType);
	}
}

