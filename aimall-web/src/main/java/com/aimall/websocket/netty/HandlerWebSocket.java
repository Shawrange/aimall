package com.aimall.websocket.netty;

import com.aimall.component.RedisComponent;
import com.aimall.constants.Constants;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * @Description ws 涓氬姟澶勭悊
 * @Date 2023/12/17 10:10
 */

/**
 * 璁剧疆閫氶亾鍏变韩
 */
@ChannelHandler.Sharable
@Component("handlerWebSocket")
public class HandlerWebSocket extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static final Logger logger = LoggerFactory.getLogger(HandlerWebSocket.class);

    @Resource
    private RedisComponent redisComponent;

    /**
     * 褰撻€氶亾灏辩华鍚庝細璋冪敤姝ゆ柟娉曪紝閫氬父鎴戜滑浼氬湪杩欓噷鍋氫竴浜涘垵濮嬪寲鎿嶄綔
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        logger.info("鏈夋柊鐨勮繛鎺ュ姞鍏ャ€傘€傘€?);
    }

    /**
     * 褰撻€氶亾涓嶅啀娲昏穬鏃讹紙杩炴帴鍏抽棴锛変細璋冪敤姝ゆ柟娉曪紝鎴戜滑鍙互鍦ㄨ繖閲屽仛涓€浜涙竻鐞嗗伐浣?
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        logger.info("鏈夎繛鎺ュ凡缁忔柇寮€銆傘€傘€?);
    }

    /**
     * 璇诲氨缁簨浠?褰撴湁娑堟伅鍙鏃朵細璋冪敤姝ゆ柟娉曪紝鎴戜滑鍙互鍦ㄨ繖閲岃鍙栨秷鎭苟澶勭悊銆?
     *
     * @param ctx
     * @param textWebSocketFrame
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame) {
        //鎺ユ敹蹇冭烦
        String text = textWebSocketFrame.text();
        if (Constants.PING.equals(text)) {
            Channel channel = ctx.channel();
            Attribute<String> attribute = channel.attr(AttributeKey.valueOf(channel.id().toString()));
            String userId = attribute.get();
            redisComponent.saveUserHeartBeat(userId);
        }
    }
}
