package inbound;

import filter.HeaderHttpRequestFilter;
import filter.HeaderHttpResponseFilter;
import gateway.Gateway;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponse;

public class NoobGatewayServerOutboundHandler extends ChannelOutboundHandlerAdapter {

    private final HeaderHttpResponseFilter responseFilter = new HeaderHttpResponseFilter();

    public NoobGatewayServerOutboundHandler() {

    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if(msg instanceof HttpResponse){
            responseFilter.filter((HttpResponse)msg);
        }
        super.write(ctx, msg, promise);
    }


}
