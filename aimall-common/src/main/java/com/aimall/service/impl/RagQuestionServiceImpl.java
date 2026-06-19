package com.aimall.service.impl;

import com.aimall.component.RedisComponent;
import com.aimall.constants.Constants;
import com.aimall.entity.dto.RagDataDTO;
import com.aimall.entity.enums.PageSize;
import com.aimall.entity.enums.RagDataTypeEnum;
import com.aimall.entity.po.RagQuestion;
import com.aimall.entity.query.RagQuestionQuery;
import com.aimall.entity.query.SimplePage;
import com.aimall.entity.vo.PaginationResultVO;
import com.aimall.mappers.RagQuestionMapper;
import com.aimall.service.RagQuestionService;
import com.aimall.utils.StringTools;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * rag闂 涓氬姟鎺ュ彛瀹炵幇
 */
@Service("ragQuestionService")
public class RagQuestionServiceImpl implements RagQuestionService {

    @Resource
    private RagQuestionMapper<RagQuestion, RagQuestionQuery> ragQuestionMapper;

    @Resource
    private RedisComponent redisComponent;

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    @Override
    public List<RagQuestion> findListByParam(RagQuestionQuery param) {
        return this.ragQuestionMapper.selectList(param);
    }

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    @Override
    public Integer findCountByParam(RagQuestionQuery param) {
        return this.ragQuestionMapper.selectCount(param);
    }

    /**
     * 鍒嗛〉鏌ヨ鏂规硶
     */
    @Override
    public PaginationResultVO<RagQuestion> findListByPage(RagQuestionQuery param) {
        int count = this.findCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

        SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
        param.setSimplePage(page);
        List<RagQuestion> list = this.findListByParam(param);
        PaginationResultVO<RagQuestion> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 鏂板
     */
    @Override
    public Integer add(RagQuestion bean) {
        return this.ragQuestionMapper.insert(bean);
    }

    /**
     * 鎵归噺鏂板
     */
    @Override
    public Integer addBatch(List<RagQuestion> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.ragQuestionMapper.insertBatch(listBean);
    }

    /**
     * 鎵归噺鏂板鎴栬€呬慨鏀?
     */
    @Override
    public Integer addOrUpdateBatch(List<RagQuestion> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.ragQuestionMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 澶氭潯浠舵洿鏂?
     */
    @Override
    public Integer updateByParam(RagQuestion bean, RagQuestionQuery param) {
        StringTools.checkParam(param);
        return this.ragQuestionMapper.updateByParam(bean, param);
    }

    /**
     * 澶氭潯浠跺垹闄?
     */
    @Override
    public Integer deleteByParam(RagQuestionQuery param) {
        StringTools.checkParam(param);
        return this.ragQuestionMapper.deleteByParam(param);
    }

    /**
     * 鏍规嵁QuestionId鑾峰彇瀵硅薄
     */
    @Override
    public RagQuestion getRagQuestionByQuestionId(Integer questionId) {
        return this.ragQuestionMapper.selectByQuestionId(questionId);
    }

    /**
     * 鏍规嵁QuestionId淇敼
     */
    @Override
    public Integer updateRagQuestionByQuestionId(RagQuestion bean, Integer questionId) {
        return this.ragQuestionMapper.updateByQuestionId(bean, questionId);
    }

    /**
     * 鏍规嵁QuestionId鍒犻櫎
     */
    @Override
    public Integer deleteRagQuestionByQuestionId(Integer questionId) {
        return this.ragQuestionMapper.deleteByQuestionId(questionId);
    }

    @Override
    public void saveRagQuestion(RagQuestion ragQuestion) {
        if (ragQuestion.getQuestionId() == null) {
            ragQuestion.setCreateTime(new Date());
            this.add(ragQuestion);
        } else {
            this.updateRagQuestionByQuestionId(ragQuestion, ragQuestion.getQuestionId());
        }
        redisComponent.sendMessage(Constants.REDIS_QUEUE_RAG_DATA, new RagDataDTO(ragQuestion.getQuestionId().toString(), RagDataTypeEnum.FAQ.getType()));
    }

    @Override
    public void delRagQuestion(Integer questionId) {
        this.deleteRagQuestionByQuestionId(questionId);
        redisComponent.sendMessage(Constants.REDIS_QUEUE_RAG_DATA, new RagDataDTO(questionId.toString(), RagDataTypeEnum.FAQ.getType()));
    }
}
