package com.aimall.mappers;

import org.apache.ibatis.annotations.Param;

/**
 *  鏁版嵁搴撴搷浣滄帴鍙?
 */
public interface UserAddressMapper<T,P> extends BaseMapper<T,P> {

	/**
	 * 鏍规嵁AddressId鏇存柊
	 */
	 Integer updateByAddressId(@Param("bean") T t,@Param("addressId") String addressId);


	/**
	 * 鏍规嵁AddressId鍒犻櫎
	 */
	 Integer deleteByAddressId(@Param("addressId") String addressId);


	/**
	 * 鏍规嵁AddressId鑾峰彇瀵硅薄
	 */
	 T selectByAddressId(@Param("addressId") String addressId);


}

