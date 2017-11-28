package org.innereye.gw;

import org.innereye.gw.core.HttpProxyChannelInitializer;
import org.innereye.ms.core.type.SSLType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 基于Netty实现的代理网关
 */
public class GwServer {

    private static final Logger logger = LoggerFactory.getLogger(GwServer.class);

    public final int PORT;

    public final boolean IS_SECURE;

    public final String HOST = "0.0.0.0";

    private volatile Channel serverChannel;

    private final ServerBootstrap serverBootstrap;

    private final EventLoopGroup bossGroup;

    private final EventLoopGroup workerGroup;

    /**
     * @param isSecure
     *            是否暴露HTTPS
     * @param port
     *            暴露端口号
     */
    public GwServer(boolean isSecure, int port){
        this(isSecure, SSLType.SIMPLE, port);
    }

    public GwServer(boolean isSecure, SSLType sslType, int port){
        this.PORT = port;
        this.IS_SECURE = isSecure;
        // Configure the server.
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024).option(ChannelOption.TCP_NODELAY, true);
        serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).handler(new LoggingHandler(LogLevel.INFO)).childHandler(new HttpProxyChannelInitializer(isSecure, sslType));
    }

    /**
     * 启动
     */
    public void start() {
        try {
            serverChannel = serverBootstrap.bind(HOST, PORT).sync().channel();
            logger.info("Server started. Open your web browser and navigate to " + (IS_SECURE ? "https" : "http")
                    + "://" + HOST + ":" + PORT + "/");
        }
        catch (InterruptedException e) {
            close();
        }
    }

    /**
     * 停止
     */
    public void stop() {
        try {
            if (serverChannel != null) {
                serverChannel.disconnect();
                serverChannel.closeFuture().sync();
            }
            logger.info("Server stopped");
        }
        catch (InterruptedException e) {
            logger.error("Error while stopping server, error is:" + e.getMessage());
        }
        finally {
            close();
        }
    }

    /**
     * 关闭
     */
    private void close() {
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        GwServer server = null;
        try {
            server = new GwServer(false, 8000);
            server.start();
        }
        catch (Exception e) {
            if (server != null) {
                server.stop();
            }
            e.printStackTrace();
        }
    }
}
