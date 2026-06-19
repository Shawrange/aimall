package com.aimall.mappers;

import com.aimall.entity.po.SysCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 鏁版嵁搴撴搷浣滄帴鍙?
 */
public interface SysCategoryMapper<T, P> extends BaseMapper<T, P> {

    /**
     * 鏍规嵁CategoryId鏇存柊
     */
    Integer updateByCategoryId(@Param("bean") T t, @Param("categoryId") String categoryId);


    /**
     * 鏍规嵁CategoryId鍒犻櫎
     */
    Integer deleteByCategoryId(@Param("categoryId") String categoryId);


    /**
     * 鏍规嵁CategoryId鑾峰彇瀵硅薄
     */
    T selectByCategoryId(@Param("categoryId") String categoryId);

    Integer selectMaxSort(@Param("pCategoryId") String pCategoryId);

    void updateSortBatch(@Param("categoryList") List<SysCategory> categoryList);
}

