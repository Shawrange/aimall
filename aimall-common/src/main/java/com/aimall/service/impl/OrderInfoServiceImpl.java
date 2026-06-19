package com.aimall.service.impl;

import com.aimall.component.EsSearchComponent;
import com.aimall.component.RedisComponent;
import com.aimall.component.SpringContext;
import com.aimall.constants.Constants;
import com.aimall.entity.config.AppConfig;
import com.aimall.entity.dto.*;
import com.aimall.entity.enums.*;
import com.aimall.entity.po.*;
import com.aimall.entity.query.*;
import com.aimall.entity.vo.PaginationResultVO;
import com.aimall.exception.BusinessException;
import com.aimall.mappers.*;
import com.aimall.service.OrderInfoService;
import com.aimall.service.PayChannel;
import com.aimall.utils.StringTools;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 璁㈠崟淇℃伅 涓氬姟鎺ュ彛瀹炵幇
 */
@Service("orderInfoService")
public class OrderInfoServiceImpl implements OrderInfoService {

    @Resource
    private OrderInfoMapper<OrderInfo, OrderInfoQuery> orderInfoMapper;

    @Resource
    private ProductInfoMapper<ProductInfo, ProductInfoQuery> productInfoMapper;

    @Resource
    private ProductSkuMapper<ProductSku, ProductSkuQuery> productSkuMapper;

    @Resource
    private ProductPropertyValueMapper<ProductPropertyValue, ProductPropertyValueQuery> productPropertyValueMapper;

    @Resource
    private OrderItemMapper<OrderItem, OrderItemQuery> orderItemMapper;

    @Resource
    private ProductCartMapper<ProductCart, ProductCartQuery> productCartMapper;

    @Resource
    private OrderLogisticsInfoMapper<OrderLogisticsInfo, OrderLogisticsInfoQuery> orderLogisticsInfoMapper;

    @Resource
    private OrderLogisticsInfoRecordMapper<OrderLogisticsInfoRecord, OrderLogisticsInfoRecordQuery> orderLogisticsInfoRecordMapper;

    @Resource
    private UserAddressMapper<UserAddress, UserAddressQuery> userAddressMapper;

    @Resource
    private AppConfig appConfig;

    @Resource
    private RedisComponent redisComponent;

    @Resource
    private EsSearchComponent esSearchComponent;

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    @Override
    public List<OrderInfo> findListByParam(OrderInfoQuery param) {
        return this.orderInfoMapper.selectList(param);
    }

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    @Override
    public Integer findCountByParam(OrderInfoQuery param) {
        return this.orderInfoMapper.selectCount(param);
    }

    /**
     * 鍒嗛〉鏌ヨ鏂规硶
     */
    @Override
    public PaginationResultVO<OrderInfo> findListByPage(OrderInfoQuery param) {
        int count = this.findCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

        SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
        param.setSimplePage(page);
        List<OrderInfo> list = this.findListByParam(param);
        PaginationResultVO<OrderInfo> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 鏂板
     */
    @Override
    public Integer add(OrderInfo bean) {
        return this.orderInfoMapper.insert(bean);
    }

    /**
     * 鎵归噺鏂板
     */
    @Override
    public Integer addBatch(List<OrderInfo> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.orderInfoMapper.insertBatch(listBean);
    }

    /**
     * 鎵归噺鏂板鎴栬€呬慨鏀?
     */
    @Override
    public Integer addOrUpdateBatch(List<OrderInfo> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.orderInfoMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 澶氭潯浠舵洿鏂?
     */
    @Override
    public Integer updateByParam(OrderInfo bean, OrderInfoQuery param) {
        StringTools.checkParam(param);
        return this.orderInfoMapper.updateByParam(bean, param);
    }

    /**
     * 澶氭潯浠跺垹闄?
     */
    @Override
    public Integer deleteByParam(OrderInfoQuery param) {
        StringTools.checkParam(param);
        return this.orderInfoMapper.deleteByParam(param);
    }

    /**
     * 鏍规嵁OrderId鑾峰彇瀵硅薄
     */
    @Override
    public OrderInfo getOrderInfoByOrderId(String orderId) {
        return this.orderInfoMapper.selectByOrderId(orderId);
    }

    /**
     * 鏍规嵁OrderId淇敼
     */
    @Override
    public Integer updateOrderInfoByOrderId(OrderInfo bean, String orderId) {
        return this.orderInfoMapper.updateByOrderId(bean, orderId);
    }

    /**
     * 鏍规嵁OrderId鍒犻櫎
     */
    @Override
    public Integer deleteOrderInfoByOrderId(String orderId) {
        return this.orderInfoMapper.deleteByOrderId(orderId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayInfoDTO postOrder(String userId, PostOrderDTO postOrderDTO) {
        PayChannelEnum payChannelEnum = PayChannelEnum.getByPayScene(postOrderDTO.getPayMethod());
        if (payChannelEnum == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }

        OrderFromTypeEnum orderFromTypeEnum = OrderFromTypeEnum.getByType(postOrderDTO.getOrderFrom());
        if (orderFromTypeEnum == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }

        List<PostOrderItemDTO> itemDTOList = postOrderDTO.getOrderList();

        List<String> productIdList = itemDTOList.stream().map(PostOrderItemDTO::getProductId).toList();

        //鏌ヨ鍦板潃淇℃伅 鏈€绠€鍗曠殑鏌ヨ
        UserAddress userAddress = userAddressMapper.selectByAddressId(postOrderDTO.getAddressId());
        if (userAddress == null || !userAddress.getUserId().equals(userId)) {
            throw new BusinessException("鍦板潃淇℃伅涓嶅瓨鍦?);
        }
        //鏌ヨ鍟嗗搧淇℃伅
        ProductInfoQuery productInfoQuery = new ProductInfoQuery();
        productInfoQuery.setProductIdList(productIdList);
        List<ProductInfo> productInfoList = productInfoMapper.selectList(productInfoQuery);
        Map<String, ProductInfo> tempProductInfoMap = productInfoList.stream().collect(Collectors.toMap(item -> item.getProductId(), Function.identity(), (data1,
                                                                                                                                                           data2) -> data2));
        //鏌ヨ鍟嗗搧灞炴€т俊鎭?
        ProductPropertyValueQuery productPropertyValueQuery = new ProductPropertyValueQuery();
        productPropertyValueQuery.setProductIdList(productIdList);
        List<ProductPropertyValue> productPropertyValueList = productPropertyValueMapper.selectList(productPropertyValueQuery);
        Map<String, ProductPropertyValue> productPropertyValueMap =
                productPropertyValueList.stream().collect(Collectors.toMap(item -> item.getProductId() + item.getPropertyValueId(), Function.identity(),
                        (data1, data2) -> data2));
        //鏌ヨsku淇℃伅
        ProductSkuQuery productSkuQuery = new ProductSkuQuery();
        productSkuQuery.setProductIdList(productIdList);
        List<ProductSku> productSkuList = productSkuMapper.selectList(productSkuQuery);
        Map<String, ProductSku> productSkuMap = productSkuList.stream().collect(Collectors.toMap(item -> item.getProductId() + item.getPropertyValueIds(),
                Function.identity(), (data1, data2) -> data2));


        Date curDate = new Date();
        //涓昏鍗?
        List<OrderInfo> orderList = new ArrayList<>();
        //璁㈠崟璇︽儏
        List<OrderItem> orderItemList = new ArrayList<>();
        //璐墿杞︿俊鎭?
        List<ProductCart> productCartList = new ArrayList<>();
        //璁㈠崟鐗╂祦淇℃伅
        List<OrderLogisticsInfo> orderLogisticsInfoList = new ArrayList<>();

        Map<String, OrderInfo> orderInfoMap = new HashMap<>();

        String payOrderId = StringTools.createPayOrderId();

        //鍙戣揣淇℃伅
        LogisticsSendDTO sendDTO = redisComponent.getLogisticsInfo();

        for (PostOrderItemDTO itemDTO : itemDTOList) {
            ProductInfo productInfo = tempProductInfoMap.get(itemDTO.getProductId());
            if (productInfo == null || !ProductStatusEnum.ON_SALE.getStatus().equals(productInfo.getStatus())) {
                throw new BusinessException("鍟嗗搧涓嶅瓨鍦ㄦ垨宸蹭笅鏋?);
            }
            String propertyValueIds = itemDTO.getPropertyValueIds();
            String[] propertyValueIdArray = propertyValueIds.split("-");
            List<String> propertyData = new ArrayList<>();
            String cover = null;
            for (String propertyValueId : propertyValueIdArray) {
                ProductPropertyValue productPropertyValue = productPropertyValueMap.get(itemDTO.getProductId() + propertyValueId);
                if (productPropertyValue == null) {
                    throw new BusinessException("鍟嗗搧灞炴€т笉瀛樺湪");
                }
                propertyData.add(productPropertyValue.getPropertyName() + ":" + productPropertyValue.getPropertyValue());
                if (cover == null && !StringTools.isEmpty(productPropertyValue.getPropertyCover())) {
                    cover = productPropertyValue.getPropertyCover();
                }
            }

            ProductSku productSku = productSkuMap.get(itemDTO.getProductId() + propertyValueIds);
            if (productSku == null) {
                throw new BusinessException("鍟嗗搧sku涓嶅瓨鍦?);
            }
            if (productSku.getStock() < itemDTO.getBuyCount()) {
                throw new BusinessException("鍟嗗搧銆? + productInfo.getProductName() + "銆戝簱瀛樹笉瓒?);
            }
            OrderInfo orderInfo = orderInfoMap.get(itemDTO.getProductId());
            if (orderInfo == null) {
                orderInfo = new OrderInfo();
                orderInfo.setOrderId(StringTools.createProductOrderId());
                orderInfo.setOrderTime(curDate);
                orderInfo.setUserId(userId);
                orderInfo.setOrderStatus(OrderStatusEnum.WAIT_PAYMENT.getStatus());
                orderInfo.setAmount(new BigDecimal(Constants.ZERO_STR));
                orderInfo.setCommentStatus(OrderCommentStatusEnum.NOT_EVALUATED.getStatus());
                orderInfo.setPayChannel(payChannelEnum.getPayChannel());
                orderInfo.setPayScene(payChannelEnum.getPayScene());
                orderInfo.setPayOrderId(payOrderId);

                orderInfoMap.put(itemDTO.getProductId(), orderInfo);
                orderList.add(orderInfo);

                List<OrderItem> orderItems = new ArrayList<>();
                orderInfo.setOrderItemList(orderItems);

                //璁板綍鍦板潃淇℃伅
                OrderLogisticsInfo orderLogisticsInfo = new OrderLogisticsInfo();
                orderLogisticsInfo.setOrderId(orderInfo.getOrderId());
                orderLogisticsInfo.setUserId(userId);
                orderLogisticsInfo.setLogisticsStatus(LogisticsStatusEnum.PENDING_SHIPMENT.getStatus());
                orderLogisticsInfo.setReceiverName(userAddress.getAddressee());
                orderLogisticsInfo.setReceiverPhone(userAddress.getPhone());
                orderLogisticsInfo.setReceiverAddress(userAddress.getAddress());

                //璁剧疆榛樿鍙戣揣鍦板潃
                if (sendDTO != null) {
                    orderLogisticsInfo.setSenderAddress(sendDTO.getSenderAddress());
                    orderLogisticsInfo.setSenderName(sendDTO.getSenderName());
                    orderLogisticsInfo.setSenderPhone(sendDTO.getSenderPhone());
                }
                orderLogisticsInfoList.add(orderLogisticsInfo);


            }
            cover = cover == null ? productInfo.getCover().split(",")[0] : cover;
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderItemId(orderInfo.getOrderId() + "_" + (orderInfo.getOrderItemList().size() + 1));
            orderItem.setOrderId(orderInfo.getOrderId());
            orderItem.setCover(cover);
            orderItem.setProductId(itemDTO.getProductId());
            orderItem.setProductName(productInfo.getProductName());
            orderItem.setPropertyValueIdHash(StringTools.encodeByMD5(propertyValueIds));
            orderItem.setPropertyInfo(String.join(";", propertyData));
            orderItem.setItemAmount(productSku.getPrice().multiply(new BigDecimal(itemDTO.getBuyCount())));
            orderItem.setBuyCount(itemDTO.getBuyCount());
            orderItem.setOrderItemStatus(OrderItemStatusEnum.NORMAL.getStatus());
            orderItem.setRemark(itemDTO.getRemark());

            orderInfo.setAmount(orderInfo.getAmount().add(orderItem.getItemAmount()));


            orderItemList.add(orderItem);
            //娣诲姞鍒拌鍗曚腑
            orderInfo.getOrderItemList().add(orderItem);

            //鍒犻櫎璐墿杞?
            if (OrderFromTypeEnum.CART == orderFromTypeEnum) {
                ProductCart productCart = new ProductCart();
                productCart.setUserId(userId);
                productCart.setProductId(productInfo.getProductId());
                productCart.setPropertyValueIds(propertyValueIds);
                productCart.setPropertyValueIdHash(StringTools.encodeByMD5(propertyValueIds));
                productCartList.add(productCart);
            }

        }
        //鎵ｅ噺搴撳瓨
        Integer updateCount = productSkuMapper.updateStockBatch(orderItemList);
        if (updateCount != orderItemList.size()) {
            throw new BusinessException("搴撳瓨涓嶈冻");
        }
        this.orderInfoMapper.insertBatch(orderList);
        this.orderItemMapper.insertBatch(orderItemList);

        //璁板綍鐗╂祦淇℃伅
        orderLogisticsInfoMapper.insertBatch(orderLogisticsInfoList);

        //濡傛灉鏄喘鐗╄溅锛屾竻闄ょ浉鍏虫暟鎹?
        if (OrderFromTypeEnum.CART == orderFromTypeEnum) {
            productCartMapper.deleteBatch(productCartList);
        }

        //鑾峰彇鏀粯淇℃伅
        PayChannel payChannel = (PayChannel) SpringContext.getBean(payChannelEnum.getBeanName());
        String subject = OrderFromTypeEnum.CART == orderFromTypeEnum ? String.format(Constants.CART_PAY_NAME, orderList.size()) :
                orderList.get(0).getOrderItemList().get(0).getProductName();
        BigDecimal amount = orderList.stream().map(OrderInfo::getAmount).reduce(BigDecimal::add).get();
        PayInfoDTO payInfoDTO = payChannel.getPayUrl(payChannelEnum, payOrderId, subject, amount);
        //灏嗚鍗曟斁鍏ュ欢鏃堕槦鍒?
        for (OrderInfo orderInfo : orderList) {
            redisComponent.addOrder2DelayQueue(Constants.REDIS_KEY_ORDER_DELAY_QUEUE, appConfig.getOrderExpireMinute(), orderInfo.getOrderId());
        }
        return payInfoDTO;
    }

    @Override
    public void payNotify(PayChannelEnum payChannelEnum, Map<String, String> requestParams, String jsonBody) {
        PayChannel channe = (PayChannel) SpringContext.getBean(payChannelEnum.getBeanName());
        PayOrderNotifyDTO payOrderNotifyDTO = channe.payNotify(requestParams, null);
        if (payOrderNotifyDTO == null) {
            return;
        }
        //鍥炶皟澶勭悊
        payOrderSuccess(payOrderNotifyDTO);
    }

    @Override
    public void payOrderSuccess(PayOrderNotifyDTO payOrderNotifyDTO) {
        //鏇存柊鍟嗗搧璁㈠崟鐘舵€?
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderStatus(OrderStatusEnum.PAID.getStatus());
        orderInfo.setChannelOrderId(payOrderNotifyDTO.getChannelOrderId());

        OrderInfoQuery orderInfoQuery = new OrderInfoQuery();
        orderInfoQuery.setPayOrderId(payOrderNotifyDTO.getPayOrderId());
        orderInfoQuery.setOrderStatus(OrderStatusEnum.WAIT_PAYMENT.getStatus());
        orderInfoMapper.updateByParam(orderInfo, orderInfoQuery);

        //鏀粯鎴愬姛锛岃嚜鍔ㄥ彂璐ч槦鍒楋紝杩欓噷鏄负浜嗘柟渚垮欢鏃讹紝杩欓噷鍙互鍘绘帀锛屽悗鍙版墜鍔ㄦ潵鍙戣揣
        redisComponent.addOrder2DelayQueue(Constants.REDIS_KEY_ORDER_DELAY_QUEUE_DELIVERY, 1, payOrderNotifyDTO.getPayOrderId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelOrder(String userId, String orderId, OrderStatusEnum orderStatusEnum) {
        OrderInfo orderInfo = orderInfoMapper.selectByOrderId(orderId);
        if (orderInfo == null) {
            throw new BusinessException("璁㈠崟涓嶅瓨鍦?);
        }

        if (!OrderStatusEnum.WAIT_PAYMENT.getStatus().equals(orderInfo.getOrderStatus())) {
            throw new BusinessException("璁㈠崟宸茬粡鏀粯鏃犳硶鍙栨秷");
        }

        if (null != userId && !orderInfo.getUserId().equals(userId)) {
            throw new BusinessException("璁㈠崟涓嶅瓨鍦?);
        }

        //鍙栨秷鎵€鏈夌殑璁㈠崟
        OrderInfoQuery orderInfoQuery = new OrderInfoQuery();
        orderInfoQuery.setPayOrderId(orderInfo.getPayOrderId());
        List<OrderInfo> orderList = orderInfoMapper.selectList(orderInfoQuery);
        List<String> orderIdList = orderList.stream().map(OrderInfo::getOrderId).collect(Collectors.toList());
        Integer updateCount = orderInfoMapper.updateOrderStatusBatch(orderStatusEnum.getStatus(), OrderStatusEnum.WAIT_PAYMENT.getStatus(), orderIdList);
        if (updateCount != orderIdList.size()) {
            throw new BusinessException("璁㈠崟宸茬粡鏀粯鏃犳硶鍙栨秷");
        }

        //閫€鎵€鏈夌殑搴撳瓨
        OrderItemQuery orderItemQuery = new OrderItemQuery();
        orderItemQuery.setOrderIdList(orderIdList);
        List<OrderItem> orderItemList = orderItemMapper.selectList(orderItemQuery);
        productSkuMapper.updateStockBatch(orderItemList);


        cancelOrder4Channel(orderInfo);
    }

    private void cancelOrder4Channel(OrderInfo orderInfo) {
        PayChannelEnum payChannelEnum = PayChannelEnum.getByPayScene(orderInfo.getPayScene());
        PayChannel payChannel = (PayChannel) SpringContext.getBean(payChannelEnum.getBeanName());
        payChannel.closeOrder(orderInfo.getPayOrderId());
    }

    //鑾峰彇鏀粯淇℃伅
    public PayInfoDTO getPayInfo(String userId, String orderId) {
        OrderInfo orderInfo = orderInfoMapper.selectByOrderId(orderId);
        if (orderInfo == null || !orderInfo.getUserId().equals(userId)) {
            throw new BusinessException("璁㈠崟涓嶅瓨鍦?);
        }

        if (orderInfo.getOrderStatus() != OrderStatusEnum.WAIT_PAYMENT.getStatus()) {
            throw new BusinessException("璁㈠崟宸叉敮浠?);
        }
        //鐢熸垚鏂扮殑璁㈠崟
        PayChannelEnum payChannelEnum = PayChannelEnum.getByPayScene(orderInfo.getPayScene());
        PayChannel payChannel = (PayChannel) SpringContext.getBean(payChannelEnum.getBeanName());
        String subject = orderInfo.getOrderId();
        BigDecimal amount = orderInfo.getAmount();
        String payOrderId = StringTools.createPayOrderId();

        PayInfoDTO payInfoDTO = payChannel.getPayUrl(payChannelEnum, payOrderId, subject, amount);
        //灏嗕箣鍓嶇殑璁㈠崟鍙栨秷
        cancelOrder4Channel(orderInfo);

        //鏇存柊鏀粯璁㈠崟ID
        OrderInfo updateOrderInfo = new OrderInfo();
        updateOrderInfo.setPayOrderId(payOrderId);
        orderInfoMapper.updateByOrderId(updateOrderInfo, orderInfo.getOrderId());
        return payInfoDTO;
    }

    @Override
    public void deleteOrder(String userId, String orderId) {
        OrderInfo orderInfo = orderInfoMapper.selectByOrderId(orderId);
        if (orderInfo == null || !orderInfo.getUserId().equals(userId)) {
            throw new BusinessException("璁㈠崟涓嶅瓨鍦?);
        }

        if (!ArrayUtils.contains(new Integer[]{OrderStatusEnum.CANCELLED.getStatus(), OrderStatusEnum.CLOSED.getStatus(), OrderStatusEnum.COMPLETED.getStatus()},
                orderInfo.getOrderStatus())) {
            throw new BusinessException("璁㈠崟鏃犳硶鍒犻櫎");
        }

        OrderInfo updateOrderInfo = new OrderInfo();
        updateOrderInfo.setOrderStatus(OrderStatusEnum.DELETE.getStatus());

        OrderInfoQuery orderInfoQuery = new OrderInfoQuery();
        orderInfoQuery.setOrderId(orderId);
        orderInfoQuery.setOrderStatusList(new Integer[]{OrderStatusEnum.CANCELLED.getStatus(), OrderStatusEnum.CLOSED.getStatus(),
                OrderStatusEnum.COMPLETED.getStatus()});

        this.orderInfoMapper.updateByParam(updateOrderInfo, orderInfoQuery);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmOrder(String userId, String orderId) {
        OrderInfo updateOrderInfo = new OrderInfo();
        updateOrderInfo.setOrderStatus(OrderStatusEnum.COMPLETED.getStatus());

        OrderInfoQuery orderInfoQuery = new OrderInfoQuery();
        orderInfoQuery.setOrderId(orderId);
        orderInfoQuery.setUserId(userId);
        orderInfoQuery.setOrderStatusList(new Integer[]{OrderStatusEnum.SHIPPED.getStatus(), OrderStatusEnum.PARTIALLY_REFUNDED.getStatus()});
        Integer updateCount = this.orderInfoMapper.updateByParam(updateOrderInfo, orderInfoQuery);
        if (updateCount == 0) {
            throw new BusinessException("璇ヨ鍗曞綋鍓嶇姸鎬佹棤娉曠‘璁ゆ敹璐?);
        }

        //澧炲姞鍟嗗搧閿€鍞噺
        OrderItemQuery orderItemQuery = new OrderItemQuery();
        orderItemQuery.setOrderId(orderId);
        List<OrderItem> orderItemList = orderItemMapper.selectList(orderItemQuery);
        Integer buyCount = orderItemList.stream().mapToInt(OrderItem::getBuyCount).sum();
        this.productInfoMapper.updateProdctuctTotalSale(orderItemList.get(0).getProductId(), buyCount);

        //鏇存柊es閿€閲?
        esSearchComponent.saveProduct(orderItemList.get(0).getProductId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refundByOrderItemId(String userId, String orderItemId) {
        OrderItem orderItem = orderItemMapper.selectByOrderItemId(orderItemId);
        if (orderItem == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        OrderInfo orderInfo = orderInfoMapper.selectByOrderId(orderItem.getOrderId());
        if (orderInfo == null || !orderInfo.getUserId().equals(userId)) {
            throw new BusinessException("璁㈠崟涓嶅瓨鍦?);
        }
        Integer[] canRefundStatus = new Integer[]{OrderStatusEnum.PAID.getStatus(), OrderStatusEnum.SHIPPED.getStatus(), OrderStatusEnum.PARTIALLY_REFUNDED.getStatus()};

        if (!ArrayUtils.contains(canRefundStatus, orderInfo.getOrderStatus())) {
            throw new BusinessException("璁㈠崟鏃犳硶閫€娆?);
        }

        if (!OrderItemStatusEnum.NORMAL.getStatus().equals(orderItem.getOrderItemStatus())) {
            throw new BusinessException("璁㈠崟宸茬粡閫€娆炬棤娉曞啀娆￠€€娆?);
        }

        String refundOrderId = StringTools.getRandomNumber(Constants.LENGTH_30);
        OrderItem updateOrderItem = new OrderItem();
        updateOrderItem.setOrderItemStatus(OrderItemStatusEnum.REFUND.getStatus());
        updateOrderItem.setRefundOrderId(refundOrderId);

        OrderItemQuery orderItemQuery = new OrderItemQuery();
        orderItemQuery.setOrderItemId(orderItemId);
        orderItemQuery.setOrderItemStatus(OrderItemStatusEnum.NORMAL.getStatus());

        Integer updateCount = this.orderItemMapper.updateByParam(updateOrderItem, orderItemQuery);
        if (updateCount == 0) {
            throw new BusinessException("閫€娆惧け璐ワ紝璇风◢鍚庡啀璇?);
        }
        orderItemQuery = new OrderItemQuery();
        orderItemQuery.setOrderId(orderItem.getOrderId());
        orderItemQuery.setOrderItemStatus(OrderItemStatusEnum.NORMAL.getStatus());
        Integer leftCount = this.orderItemMapper.selectCount(orderItemQuery);

        //鏇存柊涓昏鍗?
        OrderInfo updateInfo = new OrderInfo();
        updateInfo.setOrderStatus(leftCount == 0 ? OrderStatusEnum.REFUNDED.getStatus() : OrderStatusEnum.PARTIALLY_REFUNDED.getStatus());

        OrderInfoQuery orderInfoQuery = new OrderInfoQuery();
        orderInfoQuery.setOrderId(orderItem.getOrderId());
        orderInfoQuery.setOrderStatusList(canRefundStatus);

        Integer updateOrderCount = this.orderInfoMapper.updateByParam(updateInfo, orderInfoQuery);
        if (updateOrderCount == 0) {
            throw new BusinessException("閫€娆惧け璐ワ紝璇风◢鍚庡啀璇?);
        }
        PayChannelEnum payChannelEnum = PayChannelEnum.getByPayScene(orderInfo.getPayScene());
        PayChannel payChannel = (PayChannel) SpringContext.getBean(payChannelEnum.getBeanName());
        //璋冪敤鏀粯瀹濋€€娆?
        payChannel.refund(orderInfo.getPayOrderId(), refundOrderId, orderItem.getItemAmount());
    }

    @Override
    public void refundByOrderId(String userId, String orderId) {
        //鏌ヨ鎵€鏈夊瓙璁㈠崟
        OrderItemQuery orderItemQuery = new OrderItemQuery();
        orderItemQuery.setOrderId(orderId);
        List<OrderItem> orderItemList = this.orderItemMapper.selectList(orderItemQuery);
        if (orderItemList.isEmpty()) {
            throw new BusinessException("娌℃湁鑾峰彇鍒板搴旂殑璁㈠崟淇℃伅锛岃杈撳叆姝ｇ‘鐨勮鍗曞彿杩涜閫€娆?);
        }
        for (OrderItem orderItem : orderItemList) {
            if (OrderItemStatusEnum.REFUND.getStatus().equals(orderItem.getOrderItemStatus())) {
                continue;
            }
            refundByOrderItemId(userId, orderItem.getOrderItemId());
        }
    }

    @Override
    public BigDecimal getOrderTotalAmount(String orderTime, Integer[] orderStatus) {
        return this.orderInfoMapper.selectOrderTotalAmount(orderTime, orderStatus);
    }
}
