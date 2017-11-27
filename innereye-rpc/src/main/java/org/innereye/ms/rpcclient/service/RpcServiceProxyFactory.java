package org.innereye.ms.rpcclient.service;

import java.lang.reflect.Proxy;

import org.innereye.ms.rpcclient.RpcInvoke;
import org.innereye.ms.rpcclient.annotion.RpcServiceProxy;
import org.innereye.ms.rpcclient.client.Client;
import org.innereye.ms.rpcclient.common.Constants;

public class RpcServiceProxyFactory {

    static {
        try {
            initRpcServiceProxy();
            connectRpcServer();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化rpc服务代理
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void initRpcServiceProxy()
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        for (String classPath : Constants.classPath) {
            Class cls = Class.forName(classPath);
            RpcServiceProxy rpcServiceProxy = (RpcServiceProxy) cls.getAnnotation(RpcServiceProxy.class);
            if (rpcServiceProxy != null) {
                Object obj = cls.newInstance();
                Object proxy = Proxy.newProxyInstance(cls.getClassLoader(), cls.getInterfaces(), new RpcInvoke(obj));
                String serviceName = "".equals(rpcServiceProxy.serviceName()) ? classPath.substring(classPath.lastIndexOf(".")
                        + 1) : rpcServiceProxy.serviceName();
                Constants.rpcServicpProxyMap.put(serviceName, proxy);
            }
        }
    }

    /**
     * 连接rpc服务器
     */
    private static void connectRpcServer() throws InterruptedException {
        Client.connectServer();
    }

    /**
     * 获取rpc服务代理
     *
     * @param serviceName
     * @return
     */
    public static Object getRpcServiceProxy(String serviceName) {
        Object obj = Constants.rpcServicpProxyMap.get(serviceName);
        if (obj == null) {
            throw new RuntimeException("获取rpc服务代理...获取对象失败，对象" + serviceName + "不存在");
        }
        return obj;
    }
}
