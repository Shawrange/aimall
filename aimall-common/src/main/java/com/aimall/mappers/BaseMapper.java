package com.aimall.mappers;


 import org.apache.ibatis.annotations.Param;

 import java.util.List;

 interface BaseMapper<T, P> {

    /**
 	 * selectList:(鏍规嵁鍙傛暟鏌ヨ闆嗗悎)
 	 */
 	 List<T> selectList(@Param("query") P p);

 	/**
 	 * selectCount:(鏍规嵁闆嗗悎鏌ヨ鏁伴噺)
 	 */
 	 Integer selectCount(@Param("query") P p);

	/**
	 * insert:(鎻掑叆)
	 */
	 Integer insert(@Param("bean") T t);


	/**
	 * insertOrUpdate:(鎻掑叆鎴栬€呮洿鏂?
	 */
	 Integer insertOrUpdate(@Param("bean") T t);


	/**
	 * insertBatch:(鎵归噺鎻掑叆)
	 */
	 Integer insertBatch(@Param("list") List<T> list);


    /**
	 * insertOrUpdateBatch:(鎵归噺鎻掑叆鎴栨洿鏂?
	 */
	 Integer insertOrUpdateBatch(@Param("list") List<T> list);


	 /**
      * updateByParams:(澶氭潯浠舵洿鏂?
      */
     Integer updateByParam(@Param("bean") T t,@Param("query") P p);

     /**
       * deleteByParam:(澶氭潯浠跺垹闄?
     */
     Integer deleteByParam(@Param("query") P p);
}

