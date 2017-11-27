package cn.tm.ms.restful;

import org.innereye.ms.restful.RestFulServer;

public class RestFulServerTest {

    public static void main(String[] args) {
        RestFulServer server = new RestFulServer(false, "restful", 8080, "cn.tm.ms.restful");
        server.start();
    }
}
