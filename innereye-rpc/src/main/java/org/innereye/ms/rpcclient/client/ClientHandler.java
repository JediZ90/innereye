package org.innereye.ms.rpcclient.client;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import org.innereye.ms.rpc.entity.RpcResponse;
import org.innereye.ms.rpc.util.LogUtil;
import org.innereye.ms.rpcclient.common.Constants;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<Object> {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    /**
     * 读取到信息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel channel = ctx.channel();
        RpcResponse rpcResponse = (RpcResponse) msg;
        String requestId = rpcResponse.getRequestId();
        Constants.rpcResponseMap.put(requestId, rpcResponse);
        CountDownLatch countDownLatch = Constants.rpcCountDownLatch.get(requestId);
        Constants.rpcCountDownLatch.remove(requestId);
        countDownLatch.countDown();
        System.out.println("我是客户端，收到服务器【" + channel + "】信息【" + rpcResponse + "】");
    }

    /**
     * 通道建立连接
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        Constants.channel = channel;
        System.out.println("我是客户端，已成功和服务器建立连接【" + channel + "】");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("客户端发生异常：【" + LogUtil.getStackTrace(cause) + "】");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("=========================");
    }

    /**
     * 每次超时时间触发，就向服务器发送心跳，服务器接收到心跳后就回复心跳，然后客户端根据收到的信息确实是否连接正常
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println(sdf.format(new Date()) + "===================触发了心跳");
    }
}
