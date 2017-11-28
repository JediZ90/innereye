package org.innereye.gw.core;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.AsciiString;

public class HttpProxyServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final AsciiString CONTENT_LENGTH = new AsciiString("Content-Length");

    private static final AsciiString CONNECTION = new AsciiString("Connection");

    private static final AsciiString KEEP_ALIVE = new AsciiString("keep-alive");

    @Override
    public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) {
        // this.sendRedirect(ctx, "http://mail.163.com/");
        System.out.println("uri : " + request.uri());
        System.out.println("method : " + request.method().toString());
        System.out.println("User-Agent : " + request.headers().get("User-Agent"));
        if (HttpUtil.is100ContinueExpected(request)) {
            ctx.write(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE));
        }
        boolean keepAlive = HttpUtil.isKeepAlive(request);
//        FullHttpResponse response = null;
//        response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());
//        if (!keepAlive) {
//            ctx.write(response).addListener(ChannelFutureListener.CLOSE);
//        }
//        else {
//            response.headers().set(CONNECTION, KEEP_ALIVE);
//            ctx.write(response);
//        }
    }

    private static void sendRedirect(ChannelHandlerContext ctx, String newUri) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FOUND);
        response.headers().set(HttpHeaderNames.LOCATION, newUri);
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}
