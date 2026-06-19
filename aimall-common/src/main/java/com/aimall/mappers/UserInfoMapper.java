package com.aimall.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * 鐢ㄦ埛淇℃伅 鏁版嵁搴撴搷浣滄帴鍙?
 */
public interface UserInfoMapper<T,P> extends BaseMapper<T,P> {

	/**
	 * 鏍规嵁UserId鏇存柊
	 */
	 Integer updateByUserId(@Param("bean") T t,@Param("userId") String userId);


	/**
	 * 鏍规嵁UserId鍒犻櫎
	 */
	 Integer deleteByUserId(@Param("userId") String userId);


	/**
	 * 鏍规嵁UserId鑾峰彇瀵硅薄
	 */
	 T selectByUserId(@Param("userId") String userId);


	/**
	 * 鏍规嵁Email鏇存柊
	 */
	 Integer updateByEmail(@Param("bean") T t,@Param("email") String email);


	/**
	 * 鏍规嵁Email鍒犻櫎
	 */
	 Integer deleteByEmail(@Param("email") String email);


	/**
	 * 鏍规嵁Email鑾峰彇瀵硅薄
	 */
	 T selectByEmail(@Param("email") String email);


	/**
	 * 鏍规嵁NickName鏇存柊
	 */
	 Integer updateByNickName(@Param("bean") T t,@Param("nickName") String nickName);


	/**
	 * 鏍规嵁NickName鍒犻櫎
	 */
	 Integer deleteByNickName(@Param("nickName") String nickName);


	/**
	 * 鏍规嵁NickName鑾峰彇瀵硅薄
	 */
	 T selectByNickName(@Param("nickName") String nickName);


}

