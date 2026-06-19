package com.aimall.websocket.netty;

import com.aimall.entity.config.AppConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Description: ws鍒濆鍖栫被
 * @auther: 绋嬪簭鍛樿€佺綏 https://space.bilibili.com/499388891
 * @date: 9:36 2025/6/22
 * @param:
 * @return:
 */
@Component
@Slf4j
public class NettyWebSocketStarter implements Runnable {

    @Resource
    private AppConfig appConfig;

    @Resource
    private HandlerWebSocket handlerWebSocket;

    @Resource
    private HandlerTokenValidation handlerTokenValidation;
    /**
     * boss绾跨▼缁勶紝鐢ㄤ簬澶勭悊杩炴帴
     */
    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    /**
     * work绾跨▼缁勶紝鐢ㄤ簬澶勭悊娑堟伅
     */
    private EventLoopGroup workerGroup = new NioEventLoopGroup();

    /**
     * 璧勬簮鍏抽棴鈥斺€斿湪瀹瑰櫒閿€姣佹椂鍏抽棴
     */
    @PreDestroy
    public void close() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    @Override
    public void run() {
        try {
            //鍒涘缓鏈嶅姟绔惎鍔ㄥ姪鎵?
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup);
            serverBootstrap.channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel channel) {
                            ChannelPipeline pipeline = channel.pipeline();
                            /**
                             * 瀵筯ttp鍗忚鐨勬敮鎸侊紝浣跨敤http鐨勭紪鐮佸櫒锛岃В鐮佸櫒
                             * 閫氬父浣滀负绗竴涓鐞嗗櫒娣诲姞
                             * 蹇呴』鍦?HttpObjectAggregator 涔嬪墠
                             */
                            pipeline.addLast(new HttpServerCodec());
                            /**
                             * 杩欐槸涓€涓?HTTP 娑堟伅鑱氬悎鍣紝涓昏鍔熻兘鏄皢鍒嗙墖鐨?HTTP 娑堟伅
                             * 锛堝 chunked 浼犺緭缂栫爜鐨勬秷鎭級鑱氬悎鎴愬畬鏁寸殑 FullHttpRequest 鎴?FullHttpResponse銆?
                             */
                            pipeline.addLast(new HttpObjectAggregator(64 * 1024));

                            /**
                             * 妫€娴嬭繛鎺ョ┖闂茬姸鎬佺殑澶勭悊鍣?浼氫紶閫掔粰涓嬩竴涓鐞嗗櫒
                             * readerIdleTime  涓€娈垫椂闂村唴鏈敹鍒板鎴风鏁版嵁銆?
                             * writerIdleTime  涓€娈垫椂闂村唴鏈悜瀹㈡埛绔彂閫佹暟鎹€?
                             * allIdleTime  -璇诲拰鍐欏潎鏃犳椿鍔?
                             */
                            pipeline.addLast(new IdleStateHandler(60, 0, 0, TimeUnit.SECONDS));
                            /**
                             * 澶勭悊绌洪棽浜嬩欢
                             */
                            pipeline.addLast(new HandlerHeartBeat());
                            /**
                             * 鎷︽埅 channelRead 浜嬩欢
                             * TOKEN鏍￠獙
                             */
                            pipeline.addLast(handlerTokenValidation);
                            /**
                             * WebSocket鍗忚澶勭悊鍣紙鍏抽敭閰嶇疆锛?
                             * websocketPath 鎸囧畾 WebSocket 鐨勭鐐硅矾寰?
                             * subprotocols 鎸囧畾鏀寔鐨勫瓙鍗忚
                             * allowExtensions  鏄惁鍏佽 WebSocket 鎵╁睍
                             * maxFrameSize  璁剧疆鏈€澶у抚澶у皬 65536
                             * allowMaskMismatch 鏄惁鍏佽鎺╃爜涓嶅尮閰?
                             * checkStartsWith 鏄惁涓ユ牸妫€鏌ヨ矾寰勫紑澶?
                             * handshakeTimeoutMillis  鎻℃墜瓒呮椂鏃堕棿锛堟绉掞級
                             */
                            pipeline.addLast(new WebSocketServerProtocolHandler("/ws", null, true, 65536, true, true, 10000L));
                            pipeline.addLast(handlerWebSocket);
                        }
                    });
            //鍚姩
            Channel channel = serverBootstrap.bind(appConfig.getWsPort()).sync().channel();
            log.info("Netty鏈嶅姟绔惎鍔ㄦ垚鍔?绔彛:{}", appConfig.getWsPort());
            channel.closeFuture().sync();
        } catch (Exception e) {
            log.error("netty鍚姩澶辫触", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
