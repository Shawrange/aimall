package com.aimall.service;

import com.aimall.entity.po.UserAddress;
import com.aimall.entity.query.UserAddressQuery;
import com.aimall.entity.vo.PaginationResultVO;

import java.util.List;


/**
 * 涓氬姟鎺ュ彛
 */
public interface UserAddressService {

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    List<UserAddress> findListByParam(UserAddressQuery param);

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    Integer findCountByParam(UserAddressQuery param);

    /**
     * 鍒嗛〉鏌ヨ
     */
    PaginationResultVO<UserAddress> findListByPage(UserAddressQuery param);

    /**
     * 鏂板
     */
    Integer add(UserAddress bean);

    /**
     * 鎵归噺鏂板
     */
    Integer addBatch(List<UserAddress> listBean);

    /**
     * 鎵归噺鏂板/淇敼
     */
    Integer addOrUpdateBatch(List<UserAddress> listBean);

    /**
     * 澶氭潯浠舵洿鏂?
     */
    Integer updateByParam(UserAddress bean, UserAddressQuery param);

    /**
     * 澶氭潯浠跺垹闄?
     */
    Integer deleteByParam(UserAddressQuery param);

    /**
     * 鏍规嵁AddressId鏌ヨ瀵硅薄
     */
    UserAddress getUserAddressByAddressId(String addressId);


    /**
     * 鏍规嵁AddressId淇敼
     */
    Integer updateUserAddressByAddressId(UserAddress bean, String addressId);


    /**
     * 鏍规嵁AddressId鍒犻櫎
     */
    Integer deleteUserAddressByAddressId(String addressId);

    void updateDefaultAddress(String addressId, String userId);

    void saveAdderss(UserAddress userAddress);
}
