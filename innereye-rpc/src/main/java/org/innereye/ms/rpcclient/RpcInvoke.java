package org.innereye.ms.rpcclient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import org.innereye.ms.rpc.entity.RpcRequest;
import org.innereye.ms.rpc.entity.RpcResponse;
import org.innereye.ms.rpcclient.common.Constants;

/**
 * 用于 组装rpc调用需要的参数
 * countDownLatch为了保证获取到rpc返回的数据
 */
public class RpcInvoke implements InvocationHandler {

    private static int callCount = 1;

    private Object targetObj = null;

    public RpcInvoke(Object obj){
        this.targetObj = obj;
    }

    // 这里要返回 rpc 调用的结果
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest request = new RpcRequest();
        String requestId = UUID.randomUUID().toString();
        request.setRequestId(requestId);
        String objGet = targetObj.getClass().getName();
        request.setClassName(objGet);
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParameters(args);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Constants.rpcCountDownLatch.put(requestId, countDownLatch);
        // 调用对象方法，这里不需要调用
        // method.invoke(targetObj,args);
        Constants.channel.writeAndFlush(request);
        System.out.println("等待获取返回的数据..." + callCount);
        countDownLatch.await();
        System.out.println("成功返回数据..." + callCount);
        callCount++;
        RpcResponse rpcRequest = Constants.rpcResponseMap.get(requestId);
        Constants.rpcResponseMap.remove(requestId);
        return rpcRequest;
    }
}
