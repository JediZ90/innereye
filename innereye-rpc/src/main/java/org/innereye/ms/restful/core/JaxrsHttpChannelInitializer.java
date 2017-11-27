package org.innereye.ms.restful.core;

import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.Application;

import org.innereye.ms.core.type.SSLType;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;

public class JaxrsHttpChannelInitializer extends ChannelInitializer<SocketChannel> {

    public static final String JERSEY_HANDLER = JerseyHttpHandler.class.getName();

    private final boolean isSecure;

    private final String rootPath;

    private final Application application;

    private SslContext sslContext;

    public JaxrsHttpChannelInitializer(Application resourceConfig, boolean isSecure, SSLType sslType, String rootPath){
        this.application = resourceConfig;
        this.isSecure = isSecure;
        this.rootPath = rootPath;
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
    public void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        if (isSecure) {// SSL
            pipeline.addLast(sslContext.newHandler(ch.alloc()));
        }
        pipeline.addLast(new ReadTimeoutHandler(60, TimeUnit.SECONDS));
        pipeline.addLast(new WriteTimeoutHandler(60, TimeUnit.SECONDS));
        pipeline.addLast("codec", new HttpServerCodec());
        pipeline.addLast("httpObjectAggregator", new HttpObjectAggregator(1048576));
        pipeline.addLast(JERSEY_HANDLER, new JerseyHttpHandler(application, isSecure, rootPath));
    }
}
