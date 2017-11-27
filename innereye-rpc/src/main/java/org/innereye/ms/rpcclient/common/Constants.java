package org.innereye.ms.rpcclient.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import org.innereye.ms.rpc.entity.RpcResponse;
import org.innereye.ms.rpc.util.Utils;
import org.innereye.ms.rpcclient.service.RpcServiceProxyFactory;

import io.netty.channel.Channel;

public class Constants {

    // 保存客户端rpc服务代理
    public static Map<String, Object> rpcServicpProxyMap = new HashMap<String, Object>();

    public static Map<String, RpcResponse> rpcResponseMap = new ConcurrentHashMap<String, RpcResponse>();

    public static Map<String, CountDownLatch> rpcCountDownLatch = new ConcurrentHashMap<String, CountDownLatch>();

    // 保存所有class类路径
    public static List<String> classPath = new ArrayList<String>();

    // rpc远程地址
    public static String rpc_remote_ip = null;

    // rpc远程端口
    public static int rpc_remote_port = -1;

    // 扫描基包
    public static String rpc_base_package = null;

    // 用于rpc通信
    public static Channel channel = null;
    static {
        try {
            initRpc();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化rpc
     *
     * @throws IOException
     */
    private static void initRpc() throws IOException {
        InputStream inputStream = RpcServiceProxyFactory.class.getClassLoader().getResourceAsStream("rpc_config.properties");
        if (inputStream == null) {
            throw new RuntimeException("rpc初始化异常...配置文件rpc_config.properties不存在");
        }
        Properties properties = new Properties();
        properties.load(inputStream);
        Constants.rpc_remote_ip = properties.getProperty("rpc_remote_ip");
        Constants.rpc_remote_port = properties.getProperty("rpc_remote_port") == null ? -1 : Integer.parseInt(properties.getProperty("rpc_remote_port"));
        Constants.rpc_base_package = properties.getProperty("rpc_base_package");
        if (Constants.rpc_remote_ip == null || Constants.rpc_remote_port == -1 || Constants.rpc_base_package == null) {
            throw new RuntimeException("rpc初始化异常...配置文件rpc_config.properties属性rpc_remote_ip或rpc_remote_port或rpc_base_package不存在");
        }
        Utils.scanRpcBasePackage(Constants.rpc_base_package, Constants.classPath);
    }
}
