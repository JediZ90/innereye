package org.innereye.gw.core;

import java.util.concurrent.TimeUnit;

import org.innereye.ms.core.type.SSLType;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;

public class HttpProxyChannelInitializer extends ChannelInitializer<Channel> {

    private final boolean isSecure;

    private SslContext sslContext;

    public HttpProxyChannelInitializer(boolean isSecure, SSLType sslType){
        this.isSecure = isSecure;
        if (isSecure) {// SSL
            if (SSLType.SIMPLE == sslType) {
                try {
                    SelfSignedCertificate ssc = new SelfSignedCertificate();
                    SslContextBuilder builder = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey());
                    sslContext = builder.build();
                    // sslContext =
                    // SslContext.newServerContext(ssc.certificate(),
                    // ssc.privateKey());// 具体场景要通过文件
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (SSLType.COMPLEX == sslType) {
                throw new RuntimeException("暂不支持");
            }
            else {
                throw new RuntimeException("未知类型");
            }
        }
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if (isSecure) {// SSL
            pipeline.addLast(sslContext.newHandler(ch.alloc()));
        }
        pipeline.addLast(new ReadTimeoutHandler(60, TimeUnit.SECONDS));
        pipeline.addLast(new WriteTimeoutHandler(60, TimeUnit.SECONDS));
        pipeline.addLast("codec", new HttpServerCodec());
        pipeline.addLast("httpObjectAggregator", new HttpObjectAggregator(1048576));
        pipeline.addLast(new HttpProxyServerHandler());
    }
}
