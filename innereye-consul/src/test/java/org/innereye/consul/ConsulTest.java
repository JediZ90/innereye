package org.innereye.consul;

import com.google.common.net.HostAndPort;

public class ConsulTest {

    protected static Consul client = Consul.builder().withHostAndPort(HostAndPort.fromParts("172.31.0.156", 8500)).build();

    public static void main(String[] args) {
        System.out.println(client.catalogClient().getDatacenters());
        System.out.println(client.catalogClient().getNodes().get(0));
    }
}
