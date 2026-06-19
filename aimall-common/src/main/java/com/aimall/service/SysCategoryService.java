package com.aimall.service;

import com.aimall.entity.po.SysCategory;
import com.aimall.entity.query.SysCategoryQuery;
import com.aimall.entity.vo.PaginationResultVO;

import java.util.List;


/**
 * 涓氬姟鎺ュ彛
 */
public interface SysCategoryService {

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    List<SysCategory> findListByParam(SysCategoryQuery param);

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    Integer findCountByParam(SysCategoryQuery param);

    /**
     * 鍒嗛〉鏌ヨ
     */
    PaginationResultVO<SysCategory> findListByPage(SysCategoryQuery param);

    /**
     * 鏂板
     */
    Integer add(SysCategory bean);

    /**
     * 鎵归噺鏂板
     */
    Integer addBatch(List<SysCategory> listBean);

    /**
     * 鎵归噺鏂板/淇敼
     */
    Integer addOrUpdateBatch(List<SysCategory> listBean);

    /**
     * 澶氭潯浠舵洿鏂?
     */
    Integer updateByParam(SysCategory bean, SysCategoryQuery param);

    /**
     * 澶氭潯浠跺垹闄?
     */
    Integer deleteByParam(SysCategoryQuery param);

    /**
     * 鏍规嵁CategoryId鏌ヨ瀵硅薄
     */
    SysCategory getSysCategoryByCategoryId(String categoryId);


    /**
     * 鏍规嵁CategoryId淇敼
     */
    Integer updateSysCategoryByCategoryId(SysCategory bean, String categoryId);


    /**
     * 鏍规嵁CategoryId鍒犻櫎
     */
    Integer deleteSysCategoryByCategoryId(String categoryId);

    void saveCategoryInfo(SysCategory bean);

    void delCategory(String categoryId);

    void changeSort(String categoryIds);

    List<SysCategory> getAllCategoryList();
}
