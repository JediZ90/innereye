package org.innereye.ms.rpc.service;

import org.innereye.ms.rpc.annotion.RpcService;
import org.innereye.ms.rpc.common.Constants;

public class RpcServiceFactory {

    static {
        try {
            initRpcServiceProxy();
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
    }

    /**
     * 初始化rpc服务代理
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void initRpcServiceProxy()
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        for (String classPath : Constants.classPath) {
            Class cls = Class.forName(classPath);
            RpcService rpcService = (RpcService) cls.getAnnotation(RpcService.class);
            if (rpcService != null) {
                Object obj = cls.newInstance();
                Constants.rpcServiceMap.put(classPath, obj);
            }
        }
    }

    /**
     * 获取rpc服务代理
     *
     * @param classPath
     * @return
     */
    public static Object getRpcService(String classPath) {
        Object obj = Constants.rpcServiceMap.get(classPath);
        if (obj == null) {
            throw new RuntimeException("获取rpc服务...获取对象失败，对象" + classPath + "不存在");
        }
        return obj;
    }
}
