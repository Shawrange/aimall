package com.aimall.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * 鏁版嵁搴撴搷浣滄帴鍙?
 */
public interface SysProductPropertyMapper<T, P> extends BaseMapper<T, P> {

    /**
     * 鏍规嵁PropertyId鏇存柊
     */
    Integer updateByPropertyId(@Param("bean") T t, @Param("propertyId") String propertyId);


    /**
     * 鏍规嵁PropertyId鍒犻櫎
     */
    Integer deleteByPropertyId(@Param("propertyId") String propertyId);


    /**
     * 鏍规嵁PropertyId鑾峰彇瀵硅薄
     */
    T selectByPropertyId(@Param("propertyId") String propertyId);

    Integer selectMaxSort(@Param("categoryId") String categoryId);

}

