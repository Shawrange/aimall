package com.aimall.service;

import com.aimall.entity.po.SysProductProperty;
import com.aimall.entity.query.SysProductPropertyQuery;
import com.aimall.entity.vo.PaginationResultVO;

import java.util.List;


/**
 * 涓氬姟鎺ュ彛
 */
public interface SysProductPropertyService {

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    List<SysProductProperty> findListByParam(SysProductPropertyQuery param);

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    Integer findCountByParam(SysProductPropertyQuery param);

    /**
     * 鍒嗛〉鏌ヨ
     */
    PaginationResultVO<SysProductProperty> findListByPage(SysProductPropertyQuery param);

    /**
     * 鏂板
     */
    Integer add(SysProductProperty bean);

    /**
     * 鎵归噺鏂板
     */
    Integer addBatch(List<SysProductProperty> listBean);

    /**
     * 鎵归噺鏂板/淇敼
     */
    Integer addOrUpdateBatch(List<SysProductProperty> listBean);

    /**
     * 澶氭潯浠舵洿鏂?
     */
    Integer updateByParam(SysProductProperty bean, SysProductPropertyQuery param);

    /**
     * 澶氭潯浠跺垹闄?
     */
    Integer deleteByParam(SysProductPropertyQuery param);

    /**
     * 鏍规嵁PropertyId鏌ヨ瀵硅薄
     */
    SysProductProperty getSysProductPropertyByPropertyId(String propertyId);


    /**
     * 鏍规嵁PropertyId淇敼
     */
    Integer updateSysProductPropertyByPropertyId(SysProductProperty bean, String propertyId);


    /**
     * 鏍规嵁PropertyId鍒犻櫎
     */
    Integer deleteSysProductPropertyByPropertyId(String propertyId);

    void saveSysProductPropertyService(SysProductProperty productProperty);

    void deleteSysProductPropertyService(String propertyId);
}
