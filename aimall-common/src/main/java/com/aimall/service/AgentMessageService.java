package com.aimall.service;

import com.aimall.entity.po.AgentMessage;
import com.aimall.entity.query.AgentMessageQuery;
import com.aimall.entity.vo.PaginationResultVO;

import java.util.List;


/**
 * 涓氬姟鎺ュ彛
 */
public interface AgentMessageService {

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    List<AgentMessage> findListByParam(AgentMessageQuery param);

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    Integer findCountByParam(AgentMessageQuery param);

    /**
     * 鍒嗛〉鏌ヨ
     */
    PaginationResultVO<AgentMessage> findListByPage(AgentMessageQuery param);

    /**
     * 鏂板
     */
    Integer add(AgentMessage bean);

    /**
     * 鎵归噺鏂板
     */
    Integer addBatch(List<AgentMessage> listBean);

    /**
     * 鎵归噺鏂板/淇敼
     */
    Integer addOrUpdateBatch(List<AgentMessage> listBean);

    /**
     * 澶氭潯浠舵洿鏂?
     */
    Integer updateByParam(AgentMessage bean, AgentMessageQuery param);

    /**
     * 澶氭潯浠跺垹闄?
     */
    Integer deleteByParam(AgentMessageQuery param);

    /**
     * 鏍规嵁MessageId鏌ヨ瀵硅薄
     */
    AgentMessage getAgentMessageByMessageId(Integer messageId);


    /**
     * 鏍规嵁MessageId淇敼
     */
    Integer updateAgentMessageByMessageId(AgentMessage bean, Integer messageId);


    /**
     * 鏍规嵁MessageId鍒犻櫎
     */
    Integer deleteAgentMessageByMessageId(Integer messageId);

    AgentMessage saveMessage(String userId, String userMessage);

    void cancelMessage(String userId, Integer messageId);

    void completeMessage(Integer messageId, String bizType, String assistantMessage, String bizData);
}
