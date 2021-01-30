package filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

public class HeaderHttpRequestFilter implements HttpRequestFilter {

    @Override
    public void filter(HttpRequest fullRequest, ChannelHandlerContext ctx) {
        fullRequest.headers().set("added-from-request-filter", "gogogo");
    }
}
