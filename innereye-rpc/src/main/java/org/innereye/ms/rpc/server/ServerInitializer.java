package org.innereye.ms.rpc.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class ServerInitializer extends ChannelInitializer<Channel> {

    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();
        channelPipeline.addLast("object-decoder", new ObjectDecoder(1024
                * 1024, ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
        channelPipeline.addLast("object-encoder", new ObjectEncoder());
        // 自己的逻辑Handler
        channelPipeline.addLast("handler", new ServerHandler());
    }
}
