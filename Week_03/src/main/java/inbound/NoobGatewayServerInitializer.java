package inbound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class NoobGatewayServerInitializer extends ChannelInitializer<SocketChannel> {

    public NoobGatewayServerInitializer() {
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new HttpServerCodec())
                .addLast(new HttpObjectAggregator(1024 * 1024))
                .addLast(new NoobGatewayServerInboundHandler())
                .addLast(new NoobGatewayServerOutboundHandler());
    }


}
