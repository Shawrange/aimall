package com.aimall.service.impl;

import com.aimall.entity.enums.AgentMessageStatusEnum;
import com.aimall.entity.enums.PageSize;
import com.aimall.entity.po.AgentMessage;
import com.aimall.entity.query.AgentMessageQuery;
import com.aimall.entity.query.SimplePage;
import com.aimall.entity.vo.PaginationResultVO;
import com.aimall.mappers.AgentMessageMapper;
import com.aimall.service.AgentMessageService;
import com.aimall.utils.StringTools;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * 涓氬姟鎺ュ彛瀹炵幇
 */
@Service("agentMessageService")
public class AgentMessageServiceImpl implements AgentMessageService {

    @Resource
    private AgentMessageMapper<AgentMessage, AgentMessageQuery> agentMessageMapper;

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    @Override
    public List<AgentMessage> findListByParam(AgentMessageQuery param) {
        return this.agentMessageMapper.selectList(param);
    }

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    @Override
    public Integer findCountByParam(AgentMessageQuery param) {
        return this.agentMessageMapper.selectCount(param);
    }

    /**
     * 鍒嗛〉鏌ヨ鏂规硶
     */
    @Override
    public PaginationResultVO<AgentMessage> findListByPage(AgentMessageQuery param) {
        int count = this.findCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

        SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
        param.setSimplePage(page);
        List<AgentMessage> list = this.findListByParam(param);
        PaginationResultVO<AgentMessage> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 鏂板
     */
    @Override
    public Integer add(AgentMessage bean) {
        return this.agentMessageMapper.insert(bean);
    }

    /**
     * 鎵归噺鏂板
     */
    @Override
    public Integer addBatch(List<AgentMessage> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.agentMessageMapper.insertBatch(listBean);
    }

    /**
     * 鎵归噺鏂板鎴栬€呬慨鏀?
     */
    @Override
    public Integer addOrUpdateBatch(List<AgentMessage> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.agentMessageMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 澶氭潯浠舵洿鏂?
     */
    @Override
    public Integer updateByParam(AgentMessage bean, AgentMessageQuery param) {
        StringTools.checkParam(param);
        return this.agentMessageMapper.updateByParam(bean, param);
    }

    /**
     * 澶氭潯浠跺垹闄?
     */
    @Override
    public Integer deleteByParam(AgentMessageQuery param) {
        StringTools.checkParam(param);
        return this.agentMessageMapper.deleteByParam(param);
    }

    /**
     * 鏍规嵁MessageId鑾峰彇瀵硅薄
     */
    @Override
    public AgentMessage getAgentMessageByMessageId(Integer messageId) {
        return this.agentMessageMapper.selectByMessageId(messageId);
    }

    /**
     * 鏍规嵁MessageId淇敼
     */
    @Override
    public Integer updateAgentMessageByMessageId(AgentMessage bean, Integer messageId) {
        return this.agentMessageMapper.updateByMessageId(bean, messageId);
    }

    /**
     * 鏍规嵁MessageId鍒犻櫎
     */
    @Override
    public Integer deleteAgentMessageByMessageId(Integer messageId) {
        return this.agentMessageMapper.deleteByMessageId(messageId);
    }

    @Override
    public AgentMessage saveMessage(String userId, String userMessage) {
        AgentMessage agentMessage = new AgentMessage();
        agentMessage.setUserMessage(userMessage);
        agentMessage.setUserId(userId);
        agentMessage.setSendTime(new Date());
        agentMessage.setStatus(AgentMessageStatusEnum.NORMAL.getStatus());
        this.agentMessageMapper.insert(agentMessage);
        return agentMessage;
    }


    public void cancelMessage(String userId, Integer messageId) {
        AgentMessage agentMessage = new AgentMessage();
        agentMessage.setAssistantMessage("鐢ㄦ埛鍙栨秷");
        agentMessage.setStatus(AgentMessageStatusEnum.CANCEL.getStatus());

        AgentMessageQuery agentMessageQuery = new AgentMessageQuery();
        agentMessageQuery.setStatus(AgentMessageStatusEnum.NORMAL.getStatus());
        agentMessageQuery.setMessageId(messageId);
        this.agentMessageMapper.updateByParam(agentMessage, agentMessageQuery);
    }

    @Override
    public void completeMessage(Integer messageId, String bizType, String assistantMessage, String bizData) {
        AgentMessage agentMessage = new AgentMessage();
        agentMessage.setStatus(AgentMessageStatusEnum.COMPLETE.getStatus());
        agentMessage.setAssistantMessage(assistantMessage);
        agentMessage.setBizType(bizType);
        agentMessage.setBizData(bizData);
        
        AgentMessageQuery agentMessageQuery = new AgentMessageQuery();
        agentMessageQuery.setStatus(AgentMessageStatusEnum.NORMAL.getStatus());
        agentMessageQuery.setMessageId(messageId);
        this.agentMessageMapper.updateByParam(agentMessage, agentMessageQuery);
    }
}
