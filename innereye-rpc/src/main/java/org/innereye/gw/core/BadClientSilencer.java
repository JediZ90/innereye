package org.innereye.gw.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.LastHttpContent;

@ChannelHandler.Sharable
public class BadClientSilencer extends SimpleChannelInboundHandler<Object> {

    private static final Logger log = LoggerFactory.getLogger(BadClientSilencer.class);

    /**
     * Logs to Netty internal logger. Override this method to log to other
     * places if you want.
     */
    protected void onUnknownMessage(Object msg) {
        log.warn("Unknown msg: " + msg);
    }

    /**
     * Logs to Netty internal logger. Override this method to log to other
     * places if you want.
     */
    protected void onBadClient(Throwable e) {
        log.warn("Caught exception (maybe client is bad)", e);
    }

    /**
     * Logs to Netty internal logger. Override this method to log to other
     * places if you want.
     */
    protected void onBadServer(Throwable e) {
        log.warn("Caught exception (maybe server is bad)", e);
    }

    // ----------------------------------------------------------------------------
    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) {
        // This handler is the last inbound handler.
        // This means msg has not been handled by any previous handler.
        ctx.close();
        if (msg != LastHttpContent.EMPTY_LAST_CONTENT) {
            onUnknownMessage(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) {
        ctx.close();
        // To clarify where exceptions are from, imports are not used
        if (e instanceof java.io.IOException || e instanceof java.nio.channels.ClosedChannelException
                || e instanceof io.netty.handler.codec.DecoderException
                || e instanceof io.netty.handler.codec.CorruptedFrameException
                || e instanceof java.lang.IllegalArgumentException || e instanceof javax.net.ssl.SSLException
                || e instanceof io.netty.handler.ssl.NotSslRecordException) {
            onBadClient(e); // Maybe client is bad
        }
        else {
            onBadServer(e); // Maybe server is bad
        }
    }
}
