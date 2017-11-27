package cn.tm.ms.rpcclient;

import org.innereye.ms.rpcclient.service.RpcServiceProxyFactory;

import cn.tm.ms.rpcclient.service.UserRpcService;

public class RPCClientTest {

    public static void main(String[] args) {
        UserRpcService UserRpcService = (UserRpcService) RpcServiceProxyFactory.getRpcServiceProxy("");
        UserRpcService.select();
    }
}
