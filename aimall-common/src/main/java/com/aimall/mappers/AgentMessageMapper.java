package com.aimall.mappers;

import org.apache.ibatis.annotations.Param;

/**
 *  鏁版嵁搴撴搷浣滄帴鍙?
 */
public interface AgentMessageMapper<T,P> extends BaseMapper<T,P> {

	/**
	 * 鏍规嵁MessageId鏇存柊
	 */
	 Integer updateByMessageId(@Param("bean") T t,@Param("messageId") Integer messageId);


	/**
	 * 鏍规嵁MessageId鍒犻櫎
	 */
	 Integer deleteByMessageId(@Param("messageId") Integer messageId);


	/**
	 * 鏍规嵁MessageId鑾峰彇瀵硅薄
	 */
	 T selectByMessageId(@Param("messageId") Integer messageId);


}

