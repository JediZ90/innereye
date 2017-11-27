package org.innereye.ms.rpcclient.client;

import org.innereye.ms.rpcclient.common.Constants;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 描述 对象传输，使用netty自身的 ObjectEncoder对象编码器 ObjectDecoder对象解码器
 * ObjectEncoder对象编码器 ObjectDecoder对象解码器 是netty自身对对象的解码和编码
 */
public class Client {

    public static void connectServer() throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ClientInitializer());
        bootstrap.connect(Constants.rpc_remote_ip, Constants.rpc_remote_port).sync();
    }

    public static void main(String[] args) throws InterruptedException {
        connectServer();
    }
}
