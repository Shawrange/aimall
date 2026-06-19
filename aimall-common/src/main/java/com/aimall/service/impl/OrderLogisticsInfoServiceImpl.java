package com.aimall.service.impl;

import com.aimall.component.RedisComponent;
import com.aimall.constants.Constants;
import com.aimall.entity.config.AppConfig;
import com.aimall.entity.enums.LogisticsStatusEnum;
import com.aimall.entity.enums.OrderStatusEnum;
import com.aimall.entity.enums.PageSize;
import com.aimall.entity.po.OrderInfo;
import com.aimall.entity.po.OrderLogisticsInfo;
import com.aimall.entity.po.OrderLogisticsInfoRecord;
import com.aimall.entity.query.OrderInfoQuery;
import com.aimall.entity.query.OrderLogisticsInfoQuery;
import com.aimall.entity.query.OrderLogisticsInfoRecordQuery;
import com.aimall.entity.query.SimplePage;
import com.aimall.entity.vo.PaginationResultVO;
import com.aimall.exception.BusinessException;
import com.aimall.mappers.OrderInfoMapper;
import com.aimall.mappers.OrderLogisticsInfoMapper;
import com.aimall.mappers.OrderLogisticsInfoRecordMapper;
import com.aimall.service.OrderLogisticsInfoService;
import com.aimall.utils.StringTools;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * 鐗╂祦淇℃伅琛?涓氬姟鎺ュ彛瀹炵幇
 */
@Service("orderLogisticsInfoService")
public class OrderLogisticsInfoServiceImpl implements OrderLogisticsInfoService {

    @Resource
    private OrderLogisticsInfoMapper<OrderLogisticsInfo, OrderLogisticsInfoQuery> orderLogisticsInfoMapper;

    @Resource
    private OrderInfoMapper<OrderInfo, OrderInfoQuery> orderInfoMapper;

    @Resource
    private OrderLogisticsInfoRecordMapper<OrderLogisticsInfoRecord, OrderLogisticsInfoRecordQuery> orderLogisticsInfoRecordMapper;

    @Resource
    private RedisComponent redisComponent;

    @Resource
    private AppConfig appConfig;

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    @Override
    public List<OrderLogisticsInfo> findListByParam(OrderLogisticsInfoQuery param) {
        return this.orderLogisticsInfoMapper.selectList(param);
    }

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    @Override
    public Integer findCountByParam(OrderLogisticsInfoQuery param) {
        return this.orderLogisticsInfoMapper.selectCount(param);
    }

    /**
     * 鍒嗛〉鏌ヨ鏂规硶
     */
    @Override
    public PaginationResultVO<OrderLogisticsInfo> findListByPage(OrderLogisticsInfoQuery param) {
        int count = this.findCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

        SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
        param.setSimplePage(page);
        List<OrderLogisticsInfo> list = this.findListByParam(param);
        PaginationResultVO<OrderLogisticsInfo> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 鏂板
     */
    @Override
    public Integer add(OrderLogisticsInfo bean) {
        return this.orderLogisticsInfoMapper.insert(bean);
    }

    /**
     * 鎵归噺鏂板
     */
    @Override
    public Integer addBatch(List<OrderLogisticsInfo> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.orderLogisticsInfoMapper.insertBatch(listBean);
    }

    /**
     * 鎵归噺鏂板鎴栬€呬慨鏀?
     */
    @Override
    public Integer addOrUpdateBatch(List<OrderLogisticsInfo> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.orderLogisticsInfoMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 澶氭潯浠舵洿鏂?
     */
    @Override
    public Integer updateByParam(OrderLogisticsInfo bean, OrderLogisticsInfoQuery param) {
        StringTools.checkParam(param);
        return this.orderLogisticsInfoMapper.updateByParam(bean, param);
    }

    /**
     * 澶氭潯浠跺垹闄?
     */
    @Override
    public Integer deleteByParam(OrderLogisticsInfoQuery param) {
        StringTools.checkParam(param);
        return this.orderLogisticsInfoMapper.deleteByParam(param);
    }

    /**
     * 鏍规嵁OrderId鑾峰彇瀵硅薄
     */
    @Override
    public OrderLogisticsInfo getOrderLogisticsInfoByOrderId(String orderId) {
        return this.orderLogisticsInfoMapper.selectByOrderId(orderId);
    }

    /**
     * 鏍规嵁OrderId淇敼
     */
    @Override
    public Integer updateOrderLogisticsInfoByOrderId(OrderLogisticsInfo bean, String orderId) {
        return this.orderLogisticsInfoMapper.updateByOrderId(bean, orderId);
    }

    /**
     * 鏍规嵁OrderId鍒犻櫎
     */
    @Override
    public Integer deleteOrderLogisticsInfoByOrderId(String orderId) {
        return this.orderLogisticsInfoMapper.deleteByOrderId(orderId);
    }

    @Override
    public void delivery(OrderLogisticsInfo logisticsInfo) {
        OrderInfo orderInfo = orderInfoMapper.selectByOrderId(logisticsInfo.getOrderId());
        if (orderInfo == null) {
            throw new BusinessException("璁㈠崟涓嶅瓨鍦?);
        }

        if (!OrderStatusEnum.PAID.getStatus().equals(orderInfo.getOrderStatus())) {
            throw new BusinessException("璁㈠崟涓嶆槸寰呭彂璐х姸鎬?);
        }

        OrderInfo updateOrderInfo = new OrderInfo();
        updateOrderInfo.setOrderStatus(OrderStatusEnum.SHIPPED.getStatus());

        OrderInfoQuery orderInfoQuery = new OrderInfoQuery();
        orderInfoQuery.setOrderId(logisticsInfo.getOrderId());
        orderInfoQuery.setOrderStatus(OrderStatusEnum.PAID.getStatus());
        orderInfoMapper.updateByParam(updateOrderInfo, orderInfoQuery);


        logisticsInfo.setLogisticsStatus(LogisticsStatusEnum.IN_TRANSIT.getStatus());
        this.orderLogisticsInfoMapper.updateByOrderId(logisticsInfo, logisticsInfo.getOrderId());

        OrderLogisticsInfo dbInfo = this.getOrderLogisticsInfoByOrderId(logisticsInfo.getOrderId());
        OrderLogisticsInfoRecord record = new OrderLogisticsInfoRecord();
        record.setRecordAddress(dbInfo.getSenderAddress());
        record.setRecordTime(new Date());
        record.setOrderId(logisticsInfo.getOrderId());
        this.orderLogisticsInfoRecordMapper.insert(record);


        //鍔犲叆闃熷垪锛屾ā鎷熺墿娴?
        redisComponent.addOrder2LogisticsQueue(10, logisticsInfo.getOrderId());

        //鍙戣揣鍚庤嚜鍔ㄧ‘璁ゆ敹璐?
        redisComponent.addOrder2DelayQueue(Constants.REDIS_KEY_ORDER_DELAY_QUEUE_CONFIRM, appConfig.getOrderConfirmMinute(), logisticsInfo.getOrderId());
    }

    @Transactional(rollbackFor = Exception.class)
    public void mockOrderlogistics(String orderId) {
        String[] logisticsCenters = {
                "鍖椾含甯傚ぇ鍏村尯绀艰搐闀囩ぜ璐ょ墿娴佸洯3鍙蜂腑杞珯",
                "涓婃捣甯傛郸涓滄柊鍖虹妗ラ晣绌烘腐鍏矾256鍙蜂腑閫氳埅绌轰腑杞腑蹇?,
                "骞垮窞甯傝姳閮藉尯鑺变笢闀囪仈閭﹀揩閫掍簹澶浆杩愪腑蹇?,
                "娣卞湷甯傚疂瀹夊尯绂忔案琛楅亾鍏村洿椤轰赴鍗庡崡鑸┖鏋㈢航",
                "鎴愰兘甯傚弻娴佸尯瑗胯埅娓閬撶墿娴佸ぇ閬撲腑閫氬窛娓濆垎鎷ㄤ腑蹇?,
                "閲嶅簡甯傛笣鍖楀尯鏈ㄨ€抽晣绌烘腐澶ч亾鍦嗛€氳タ鍗楄埅绌烘灑绾?,
                "姝︽眽甯傞粍闄傚尯妯簵琛楅亾涓寸┖浜т笟鍥煹杈惧崕涓浆杩愪腑蹇?,
                "閮戝窞甯傛柊閮戝競钖涘簵闀囧崕鍗楀煄浜岃矾涓夊織鐗╂祦鍗庝腑鏋㈢航",
                "鍗椾含甯傛睙瀹佸尯绂勫彛琛楅亾鏈哄満璺?鍙烽偖鏀胯埅绌洪€熼€掔墿娴佷腑蹇?,
                "鏉窞甯傝惂灞卞尯鐡滄播闀囨澀宸炰繚绋庣墿娴佷腑蹇僁鍖?,
                "瑗垮畨甯傝タ鍜告柊鍖虹┖娓柊鍩庤嚜璐稿ぇ閬撲含涓滀簹娲蹭竴鍙疯タ瀹夋櫤鑳界墿娴佸洯",
                "闀挎矙甯傞暱娌欏幙榛勮姳闀囨満鍦哄彛绀惧尯閭斂閫熼€掗暱娌欏鐞嗕腑蹇?,
                "澶╂触甯傛娓呭尯宕旈粍鍙ｉ晣鐢靛晢鐗╂祦鍥櫨涓栧揩閫掑崕鍖楄浆杩愪腑蹇?,
                "闈掑矝甯傝兌宸炲競鑳朵笢琛楅亾闈掑矝淇濈◣鐗╂祦鍥?,
                "鍘﹂棬甯傛箹閲屽尯楂樺磶鍖椾笁璺埅绌烘腐鐗╂祦鍥?,
                "鍝堝皵婊ㄥ競閬撻噷鍖哄お骞抽晣绌烘腐鐗╂祦鍥瀬鍏旈€熼€掗粦榫欐睙杞繍涓績",
                "鏄嗘槑甯傚畼娓″尯澶ф澘妗ヨ閬撻暱姘存満鍦虹墿娴佸洯鍖?,
                "涔岄瞾鏈ㄩ綈甯傛柊甯傚尯鍦扮獫鍫′埂鍥介檯鏈哄満鐗╂祦鍥?,
                "鍗楀畞甯傛睙鍗楀尯鍚村湬闀囨満鍦洪珮閫熻矾瀹夎兘鐗╂祦骞胯タ鍒嗘嫧涓績",
                "鍏板窞甯傚畨瀹佸尯娌欎簳椹胯閬撹タ鍖楃墿璧勭墿娴佸洯",
                "鍗楁槍甯傚崡鏄屽幙鍚戝闀囬搧璺墿娴佸熀鍦?,
                "鍚堣偉甯傝偉瑗垮幙妗冭姳宸ヤ笟鍥『涓板悎鑲ラ檰杩愪腑杞満",
                "澶師甯傚皬搴楀尯鍖楁牸闀囧北瑗跨患鏀圭ず鑼冨尯鐗╂祦鍥?,
                "鐭冲搴勫競姝ｅ畾鍘挎柊鍩庨摵闀囩煶瀹跺簞缁煎悎淇濈◣鍖虹墿娴佷腑蹇?,
                "闀挎槬甯傚鍩庡尯鍏撮殕灞遍晣闀挎槬閾佽矾鐗╂祦鍩哄湴",
                "娌堥槼甯傝嫃瀹跺悲鍖轰綗娌熻閬撴矆闃虫浠欏浗闄呮満鍦虹墿娴佸洯鍖?,
                "娴峰彛甯傜編鍏板尯鐏靛北闀囨捣鍙ｇ編鍏板浗闄呮満鍦虹墿娴佷腑蹇?,
                "鍛煎拰娴╃壒甯傝禌缃曞尯宸村溅闀囩櫧濉旀満鍦虹墿娴佸洯",
                "璐甸槼甯傞緳娲炲牎鏈哄満鐗╂祦鍥尯",
                "瀹佹尝甯傛睙鍖楀尯娲琛楅亾瀹佹尝鐢靛晢鐗╂祦涓績",
                "浣涘北甯傚崡娴峰尯鐙北闀囦經灞辨満鍦虹墿娴佸洯鍖?,
                "鏃犻敗甯傛柊鍚村尯纭曟斁琛楅亾鑻忓崡鍥介檯鏈哄満鐗╂祦涓績",
                "澶ц繛甯傜敇浜曞瓙鍖哄ぇ杩炴咕琛楅亾澶ц繛娓泦瑁呯鐗╂祦鍥?,
                "娴庡崡甯傚巻鍩庡尯鑽疯姳璺閬撴祹鍗楀浗闄呮満鍦虹墿娴佸洯鍖?,
                "娓╁窞甯傞緳婀惧尯鏈哄満澶ч亾娓╁窞鑸┖鐗╂祦鍥?,
                "绂忓窞甯傞暱涔愬尯婕虫腐琛楅亾绂忓窞淇濈◣鍖虹墿娴佸洯",
                "娉夊窞甯傛檵姹熷競纾佺伓闀囨硥宸為檰鍦版腐",
                "闀挎矙甯傛祻闃冲競姘稿畨闀囬暱娌欓粍鑺辩患鍚堜繚绋庡尯",
                "闈掑矝甯傚嵆澧ㄥ尯钃濇潙闀囬潚宀涘嵆澧ㄥ浗闄呴檰娓?,
                "鎴愰兘甯傞潚鐧芥睙鍖哄煄鍘㈤晣鎴愰兘閾佽矾闆嗚绠变腑蹇冪珯",
                "姝︽眽甯備笢瑗挎箹鍖鸿蛋椹箔琛楅亾姝︽眽閾佽矾闆嗚绠变腑蹇冪珯",
                "閮戝窞甯傜鍩庡洖鏃忓尯鑸捣涓滆矾閮戝窞閾佽矾闆嗚绠变腑蹇冪珯",
                "鑻忓窞甯傚鑻忓尯閲戦槉鏂板煄鑻忓窞閾佽矾瑗跨珯鐗╂祦鍥?,
                "鍗椾含甯傛郸鍙ｅ尯妗ユ灄琛楅亾鍗椾含閾佽矾闆嗚绠变腑蹇冪珯",
                "涓滆帪甯傞夯娑岄晣鏂版矙娓悗鏂圭墿娴佸洯",
                "涓北甯傜伀鐐紑鍙戝尯涓北娓揣杩愯仈钀ユ湁闄愬叕鍙?,
                "鐝犳捣甯傞娲插尯娲咕娓彔娴蜂繚绋庡尯鐗╂祦鍥?,
                "姹熼棬甯傝摤姹熷尯鑽峰闀囨睙闂ㄧ墿娴佷腑蹇冪珯",
                "鎯犲窞甯傛儬闃冲尯娣℃按琛楅亾鎯犲窞鐗╂祦闆嗘暎涓績",
                "婀涙睙甯傞湠灞卞尯涓存腐宸ヤ笟鍥箾姹熸腐鐗╂祦涓績",
                "婕冲窞甯傞緳娴峰競瑙掔編闀囨汲宸炲彴鍟嗘姇璧勫尯鐗╂祦鍥?
        };

        OrderInfo orderInfo = orderInfoMapper.selectByOrderId(orderId);
        if (orderInfo == null) {
            return;
        }
        if (OrderStatusEnum.REFUNDED.getStatus().equals(orderInfo.getOrderStatus())) {
            OrderLogisticsInfo logisticsInfo = new OrderLogisticsInfo();
            logisticsInfo.setLogisticsStatus(LogisticsStatusEnum.CANCELLED.getStatus());
            this.orderLogisticsInfoMapper.updateByOrderId(logisticsInfo, orderId);
            return;
        }

        OrderLogisticsInfoRecordQuery query = new OrderLogisticsInfoRecordQuery();
        query.setOrderId(orderId);
        Integer recordCount = this.orderLogisticsInfoRecordMapper.selectCount(query);
        String address = null;
        //妯℃嫙5涓腑杞湴鍧€
        if (recordCount >= 5) {
            OrderLogisticsInfo orderLogisticsInfo = this.orderLogisticsInfoMapper.selectByOrderId(orderId);
            address = orderLogisticsInfo.getReceiverAddress();
        } else {
            Integer randomIndex = StringTools.getRandomNumberRange(0, logisticsCenters.length);
            address = logisticsCenters[randomIndex];
            //缁х画鍔犲叆闃熷垪锛屾ā鎷熺墿娴?
            redisComponent.addOrder2LogisticsQueue(StringTools.getRandomNumberRange(1, 5), orderId);
        }
        OrderLogisticsInfoRecord record = new OrderLogisticsInfoRecord();
        record.setRecordAddress(address);
        record.setRecordTime(new Date());
        record.setOrderId(orderId);
        this.orderLogisticsInfoRecordMapper.insert(record);

        if (recordCount >= 5) {
            OrderLogisticsInfo logisticsInfo = new OrderLogisticsInfo();
            logisticsInfo.setLogisticsStatus(LogisticsStatusEnum.DELIVERED.getStatus());
            this.orderLogisticsInfoMapper.updateByOrderId(logisticsInfo, orderId);
        }
    }

    @Override
    public OrderLogisticsInfo getOrderLogisticsRecords(String userId, String orderId) {
        OrderLogisticsInfo orderLogisticsInfo = this.orderLogisticsInfoMapper.selectByOrderId(orderId);
        if (orderLogisticsInfo == null || !orderLogisticsInfo.getUserId().equals(userId)) {
            throw new BusinessException("璁㈠崟涓嶅瓨鍦?);
        }
        OrderLogisticsInfoRecordQuery query = new OrderLogisticsInfoRecordQuery();
        query.setOrderId(orderId);
        query.setOrderBy("record_time desc");
        List<OrderLogisticsInfoRecord> recordList = this.orderLogisticsInfoRecordMapper.selectList(query);
        orderLogisticsInfo.setRecordList(recordList);
        return orderLogisticsInfo;
    }
}
