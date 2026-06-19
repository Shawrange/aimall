package com.aimall.service.impl;

import com.aimall.entity.enums.*;
import com.aimall.entity.po.OrderComment;
import com.aimall.entity.po.OrderInfo;
import com.aimall.entity.po.OrderItem;
import com.aimall.entity.query.OrderCommentQuery;
import com.aimall.entity.query.OrderInfoQuery;
import com.aimall.entity.query.OrderItemQuery;
import com.aimall.entity.query.SimplePage;
import com.aimall.entity.vo.PaginationResultVO;
import com.aimall.exception.BusinessException;
import com.aimall.mappers.OrderCommentMapper;
import com.aimall.mappers.OrderInfoMapper;
import com.aimall.mappers.OrderItemMapper;
import com.aimall.service.OrderCommentService;
import com.aimall.utils.StringTools;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * 涓氬姟鎺ュ彛瀹炵幇
 */
@Service("orderCommentService")
public class OrderCommentServiceImpl implements OrderCommentService {

    @Resource
    private OrderCommentMapper<OrderComment, OrderCommentQuery> orderCommentMapper;

    @Resource
    private OrderInfoMapper<OrderInfo, OrderInfoQuery> orderInfoMapper;

    @Resource
    private OrderItemMapper<OrderItem, OrderItemQuery> orderItemMapper;

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    @Override
    public List<OrderComment> findListByParam(OrderCommentQuery param) {
        return this.orderCommentMapper.selectList(param);
    }

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    @Override
    public Integer findCountByParam(OrderCommentQuery param) {
        return this.orderCommentMapper.selectCount(param);
    }

    /**
     * 鍒嗛〉鏌ヨ鏂规硶
     */
    @Override
    public PaginationResultVO<OrderComment> findListByPage(OrderCommentQuery param) {
        int count = this.findCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

        SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
        param.setSimplePage(page);
        List<OrderComment> list = this.findListByParam(param);
        PaginationResultVO<OrderComment> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 鏂板
     */
    @Override
    public Integer add(OrderComment bean) {
        return this.orderCommentMapper.insert(bean);
    }

    /**
     * 鎵归噺鏂板
     */
    @Override
    public Integer addBatch(List<OrderComment> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.orderCommentMapper.insertBatch(listBean);
    }

    /**
     * 鎵归噺鏂板鎴栬€呬慨鏀?
     */
    @Override
    public Integer addOrUpdateBatch(List<OrderComment> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.orderCommentMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 澶氭潯浠舵洿鏂?
     */
    @Override
    public Integer updateByParam(OrderComment bean, OrderCommentQuery param) {
        StringTools.checkParam(param);
        return this.orderCommentMapper.updateByParam(bean, param);
    }

    /**
     * 澶氭潯浠跺垹闄?
     */
    @Override
    public Integer deleteByParam(OrderCommentQuery param) {
        StringTools.checkParam(param);
        return this.orderCommentMapper.deleteByParam(param);
    }

    /**
     * 鏍规嵁OrderId鑾峰彇瀵硅薄
     */
    @Override
    public OrderComment getOrderCommentByOrderId(String orderId) {
        return this.orderCommentMapper.selectByOrderId(orderId);
    }

    /**
     * 鏍规嵁OrderId淇敼
     */
    @Override
    public Integer updateOrderCommentByOrderId(OrderComment bean, String orderId) {
        return this.orderCommentMapper.updateByOrderId(bean, orderId);
    }

    /**
     * 鏍规嵁OrderId鍒犻櫎
     */
    @Override
    public Integer deleteOrderCommentByOrderId(String orderId) {
        return this.orderCommentMapper.deleteByOrderId(orderId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void postComment(String userId, String orderId, String commentContent, String commentImages, Integer star) {
        OrderInfo orderInfo = orderInfoMapper.selectByOrderId(orderId);
        if (!orderInfo.getUserId().equals(userId)) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }

        if (!OrderStatusEnum.COMPLETED.getStatus().equals(orderInfo.getOrderStatus())) {
            throw new BusinessException("鎶辨瓑璁㈠崟娌℃湁纭鏀惰揣鏃犳硶璇勪环锛岃鍏堢‘璁ゆ敹璐у悗鍐嶈瘎浠?);
        }

        if (!OrderCommentStatusEnum.NOT_EVALUATED.getStatus().equals(orderInfo.getCommentStatus())) {
            throw new BusinessException("宸茬粡璇勪环鏃犳硶鍐嶆璇勪环");
        }
        OrderInfo updateInfo = new OrderInfo();
        updateInfo.setCommentStatus(OrderCommentStatusEnum.EVALUATED.getStatus());
        OrderInfoQuery orderInfoQuery = new OrderInfoQuery();
        orderInfoQuery.setCommentStatus(OrderCommentStatusEnum.NOT_EVALUATED.getStatus());
        orderInfoQuery.setOrderId(orderId);
        Integer updateCount = orderInfoMapper.updateByParam(updateInfo, orderInfoQuery);
        if (updateCount == 0) {
            throw new BusinessException("宸茬粡璇勪环鏃犳硶鍐嶆璇勪环");
        }
        OrderItemQuery orderItemQuery = new OrderItemQuery();
        orderItemQuery.setOrderId(orderId);
        orderItemQuery.setOrderItemStatus(OrderItemStatusEnum.NORMAL.getStatus());
        List<OrderItem> orderItemList = orderItemMapper.selectList(orderItemQuery);

        OrderItem orderItem = orderItemList.get(0);
        OrderComment orderComment = new OrderComment();
        orderComment.setPropertyInfo(orderItem.getPropertyInfo());
        orderComment.setProductId(orderItem.getProductId());
        orderComment.setCommentContent(commentContent);
        orderComment.setCommentImages(commentImages);
        orderComment.setOrderId(orderId);
        orderComment.setUserId(userId);
        orderComment.setCommentTime(new Date());
        orderComment.setStar(star);
        orderCommentMapper.insert(orderComment);
    }

    @Transactional(rollbackFor = Exception.class)
    public void postReComment(String userId, String orderId, String reCommentContent, String reCommentImages) {
        OrderInfo orderInfo = orderInfoMapper.selectByOrderId(orderId);
        if (!orderInfo.getUserId().equals(userId)) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        if (!OrderCommentStatusEnum.EVALUATED.getStatus().equals(orderInfo.getCommentStatus())) {
            throw new BusinessException("宸茬粡杩借瘎");
        }
        OrderInfo updateInfo = new OrderInfo();
        updateInfo.setCommentStatus(OrderCommentStatusEnum.ADDITIONAL_EVALUATED.getStatus());
        OrderInfoQuery orderInfoQuery = new OrderInfoQuery();
        orderInfoQuery.setCommentStatus(OrderCommentStatusEnum.EVALUATED.getStatus());
        orderInfoQuery.setOrderId(orderId);
        Integer updateCount = orderInfoMapper.updateByParam(updateInfo, orderInfoQuery);
        if (updateCount == 0) {
            throw new BusinessException("宸茬粡杩借瘎");
        }

        OrderComment orderComment = new OrderComment();
        orderComment.setRecommentContent(reCommentContent);
        orderComment.setRecommentImages(reCommentImages);
        orderComment.setRecommentTime(new Date());
        orderCommentMapper.updateByOrderId(orderComment, orderId);
    }

    public void postBizComment(String orderId, String commentBizReply) {
        OrderInfo orderInfo = orderInfoMapper.selectByOrderId(orderId);
        if (OrderCommentStatusEnum.NOT_EVALUATED.getStatus().equals(orderInfo.getCommentStatus())) {
            throw new BusinessException("鏃犳硶杩涜鍟嗗鍥炲");
        }
        OrderComment orderComment = new OrderComment();
        orderComment.setCommentBizReply(commentBizReply);
        orderCommentMapper.updateByOrderId(orderComment, orderId);
    }
}
