package inbound;

import filter.HeaderHttpRequestFilter;
import gateway.Gateway;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;

public class NoobGatewayServerInboundHandler extends ChannelInboundHandlerAdapter {

    private final Gateway gateway = new Gateway();
    private final HeaderHttpRequestFilter headerHttpRequestFilter = new HeaderHttpRequestFilter();

    public NoobGatewayServerInboundHandler() {

    }

    /**
     * Closes the specified channel after all queued write requests are flushed.
     */
    public static void closeOnFlush(Channel ch) {
        if (ch.isActive()) {
            ch.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.read();

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        HttpRequest fullHttpRequest = (HttpRequest) msg;
        gateway.handle(fullHttpRequest, ctx, headerHttpRequestFilter);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("gateway server exception: " + cause.getMessage());
        closeOnFlush(ctx.channel());
    }

}
