package org.innereye.router;


import org.innereye.gw.core.BadClientSilencer;
import org.innereye.gw.router.Router;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpRouterServerInitializer extends ChannelInitializer<SocketChannel> {
    private final HttpRouterServerHandler handler;
    private final BadClientSilencer       badClientSilencer = new BadClientSilencer();

    HttpRouterServerInitializer(Router<String> router) {
        handler = new HttpRouterServerHandler(router);
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ch.pipeline()
          .addLast(new HttpServerCodec())
          .addLast(handler)
          .addLast(badClientSilencer);
    }
}
