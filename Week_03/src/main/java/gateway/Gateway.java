package gateway;

import filter.HttpRequestFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import outbound.NoobClients;
import router.HttpEndpointRouter;
import router.RandomHttpEndpointRouter;

public class Gateway {
    HttpEndpointRouter<Integer> router = new RandomHttpEndpointRouter();
    public void handle(final HttpRequest fullRequest, final ChannelHandlerContext ctx, HttpRequestFilter filter) {
        //doRoute
        int routeResult = router.route(NoobClients.getEndpoints());
        //doRequestFilter
        filter.filter(fullRequest, ctx);
        NoobClients.getClients().get(NoobClients.getEndpoints().get(routeResult)).request(fullRequest, ctx.channel());
    }

}
