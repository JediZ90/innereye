package cn.tm.ms.rpc;

import org.innereye.ms.rpc.RPCServer;

public class RPCServerTest {

    public static void main(String[] args) {
        RPCServer server = new RPCServer(8000);
        server.start();
    }
}
