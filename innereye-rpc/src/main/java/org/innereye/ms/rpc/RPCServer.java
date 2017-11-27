package org.innereye.ms.rpc;

import org.innereye.ms.rpc.server.ServerInitializer;
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
 * 基于Netty实现的RPC服务器
 */
public class RPCServer {

    private static final Logger logger = LoggerFactory.getLogger(RPCServer.class);

    public final int PORT;

    public final String HOST = "0.0.0.0";

    private volatile Channel serverChannel;

    private final ServerBootstrap serverBootstrap;

    private final EventLoopGroup bossGroup;

    private final EventLoopGroup workerGroup;

    public RPCServer(int port){
        this.PORT = port;
        // Configure the server.
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).handler(new LoggingHandler(LogLevel.INFO)).childHandler(new ServerInitializer());
    }

    /**
     * 启动
     */
    public void start() {
        try {
            serverChannel = serverBootstrap.bind(HOST, PORT).sync().channel();
            logger.info("RPC Server started. PORT=" + PORT);
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
        RPCServer server = null;
        try {
            if (args.length == 1) {
                int port = Integer.valueOf(args[0]);
                server = new RPCServer(port);
                server.start();
            }
            else {
                throw new RuntimeException("参数个数必须为1个!!!");
            }
        }
        catch (Exception e) {
            if (server != null) {
                server.stop();
            }
            e.printStackTrace();
        }
    }
}
