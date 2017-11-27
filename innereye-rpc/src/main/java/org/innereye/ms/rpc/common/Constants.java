package org.innereye.ms.rpc.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.innereye.ms.rpc.service.RpcServiceFactory;
import org.innereye.ms.rpc.util.Utils;

public class Constants {

    public static Map<String, Object> rpcServiceMap = new HashMap<String, Object>();

    public static String rpc_base_package = null;

    public static List<String> classPath = new ArrayList<String>();

    public static int port = -1;
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
        InputStream inputStream = RpcServiceFactory.class.getClassLoader().getResourceAsStream("rpc_config.properties");
        if (inputStream == null) {
            throw new RuntimeException("rpc初始化异常...配置文件rpc_config.properties不存在");
        }
        Properties properties = new Properties();
        properties.load(inputStream);
        Constants.rpc_base_package = properties.getProperty("rpc_base_package");
        Constants.port = properties.getProperty("port") == null ? -1 : Integer.parseInt(properties.getProperty("port"));
        if (Constants.rpc_base_package == null || Constants.port == -1) {
            throw new RuntimeException("rpc初始化异常...配置文件rpc_config.properties属性rpc_base_package或port不存在");
        }
        Utils.scanRpcBasePackage(Constants.rpc_base_package, Constants.classPath);
    }
}
