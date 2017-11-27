package org.innereye.ms.rpc.server;

import java.lang.reflect.Method;

import org.innereye.ms.rpc.entity.RpcRequest;
import org.innereye.ms.rpc.entity.RpcResponse;
import org.innereye.ms.rpc.service.RpcServiceFactory;
import org.innereye.ms.rpc.util.LogUtil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 通道事件驱动器
 */
public class ServerHandler extends SimpleChannelInboundHandler<Object> {

    /**
     * 读取到信息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel channel = ctx.channel();
        RpcRequest rpcRequest = (RpcRequest) msg;
        String classPath = rpcRequest.getClassName();
        Object obj = RpcServiceFactory.getRpcService(classPath);
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setRequestId(rpcRequest.getRequestId());
        if (obj != null) {
            Class cls = obj.getClass();
            Method method = cls.getDeclaredMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
            Object result = method.invoke(obj, rpcRequest.getParameters());
            rpcResponse.setResult(result);
        }
        else {
            rpcResponse.setError("请求接口不存在...");
        }
        ctx.writeAndFlush(rpcResponse);
        System.out.println("我是服务器，收到客户端【" + channel + "】信息【" + rpcRequest + "】");
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
        System.out.println("我是服务器，收到客户端【" + channel + "】的连接");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("服务器发生异常：【" + LogUtil.getStackTrace(cause) + "】");
        ctx.writeAndFlush(LogUtil.getStackTrace(cause));
    }
}
