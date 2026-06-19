package com.aimall.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * rag闂 鏁版嵁搴撴搷浣滄帴鍙?
 */
public interface RagQuestionMapper<T,P> extends BaseMapper<T,P> {

	/**
	 * 鏍规嵁QuestionId鏇存柊
	 */
	 Integer updateByQuestionId(@Param("bean") T t,@Param("questionId") Integer questionId);


	/**
	 * 鏍规嵁QuestionId鍒犻櫎
	 */
	 Integer deleteByQuestionId(@Param("questionId") Integer questionId);


	/**
	 * 鏍规嵁QuestionId鑾峰彇瀵硅薄
	 */
	 T selectByQuestionId(@Param("questionId") Integer questionId);


}

