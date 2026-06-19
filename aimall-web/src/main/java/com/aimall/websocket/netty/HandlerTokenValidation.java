package com.aimall.websocket.netty;

import com.aimall.component.RedisComponent;
import com.aimall.entity.dto.TokenUserInfoDTO;
import com.aimall.websocket.ChannelContextUtils;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@ChannelHandler.Sharable
@Component
@Slf4j
public class HandlerTokenValidation extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Resource
    private ChannelContextUtils channelContextUtils;

    @Resource
    private RedisComponent redisComponent;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) {
        String uri = request.uri();
        QueryStringDecoder queryDecoder = new QueryStringDecoder(uri);
        List<String> tokens = queryDecoder.parameters().get("token");
        if (tokens == null) {
            sendErrorResponse(ctx);
            return;
        }
        String token = tokens.get(0);
        TokenUserInfoDTO tokenUserInfoDto = redisComponent.getTokenInfo(token);
        if (tokenUserInfoDto == null) {
            log.error("鏍￠獙token澶辫触:{}", token);
            sendErrorResponse(ctx);
            return;
        }
        log.info("鏍￠獙鎴愬姛");
        // 濡傛灉闇€瑕佽浆鍙戞秷鎭? 澧炲姞寮曠敤璁℃暟
        ctx.fireChannelRead(request.retain());
        //鍔犲叆閫氶亾
        channelContextUtils.addContext(tokenUserInfoDto.getUserId(), ctx.channel());
    }

    private void sendErrorResponse(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FORBIDDEN, Unpooled.copiedBuffer("token鏃犳晥", CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}
