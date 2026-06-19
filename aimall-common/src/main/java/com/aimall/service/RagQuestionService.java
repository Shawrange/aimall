package com.aimall.service;

import com.aimall.entity.po.RagQuestion;
import com.aimall.entity.query.RagQuestionQuery;
import com.aimall.entity.vo.PaginationResultVO;

import java.util.List;


/**
 * rag闂 涓氬姟鎺ュ彛
 */
public interface RagQuestionService {

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    List<RagQuestion> findListByParam(RagQuestionQuery param);

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    Integer findCountByParam(RagQuestionQuery param);

    /**
     * 鍒嗛〉鏌ヨ
     */
    PaginationResultVO<RagQuestion> findListByPage(RagQuestionQuery param);

    /**
     * 鏂板
     */
    Integer add(RagQuestion bean);

    /**
     * 鎵归噺鏂板
     */
    Integer addBatch(List<RagQuestion> listBean);

    /**
     * 鎵归噺鏂板/淇敼
     */
    Integer addOrUpdateBatch(List<RagQuestion> listBean);

    /**
     * 澶氭潯浠舵洿鏂?
     */
    Integer updateByParam(RagQuestion bean, RagQuestionQuery param);

    /**
     * 澶氭潯浠跺垹闄?
     */
    Integer deleteByParam(RagQuestionQuery param);

    /**
     * 鏍规嵁QuestionId鏌ヨ瀵硅薄
     */
    RagQuestion getRagQuestionByQuestionId(Integer questionId);


    /**
     * 鏍规嵁QuestionId淇敼
     */
    Integer updateRagQuestionByQuestionId(RagQuestion bean, Integer questionId);


    /**
     * 鏍规嵁QuestionId鍒犻櫎
     */
    Integer deleteRagQuestionByQuestionId(Integer questionId);

    void saveRagQuestion(RagQuestion ragQuestion);

    void delRagQuestion(Integer questionId);
}
