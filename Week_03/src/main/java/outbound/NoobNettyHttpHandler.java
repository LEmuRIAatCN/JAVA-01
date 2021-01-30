package outbound;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NoobNettyHttpHandler extends ChannelInboundHandlerAdapter {
    Channel gatewayChannel;

    public NoobNettyHttpHandler() {
    }

    public Channel getGatewayChannel() {
        return gatewayChannel;
    }

    public void setGatewayChannel(Channel gatewayChannel) {
        this.gatewayChannel = gatewayChannel;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.read();
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) {
        System.out.println("incoming msg: "+msg);
        if (gatewayChannel !=null && gatewayChannel.isOpen()) {
            gatewayChannel.writeAndFlush(msg);
        }else{
            gatewayChannel =null;
            System.out.println("NoobNettyHttpHandler gatewatChannel is down ");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("NoobNettyHttpHandler ex: "+cause.getMessage());
        ctx.writeAndFlush(cause);
    }
}
