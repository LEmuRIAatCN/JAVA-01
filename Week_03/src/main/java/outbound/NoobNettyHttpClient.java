package outbound;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpRequest;

public class NoobNettyHttpClient implements NoobHttpClient {
    private final String ip;
    private final int port;
    private Channel clientChannel;

    public NoobNettyHttpClient(String ip, int port) {

        this.ip = ip;
        this.port = port;
    }

    public void request(HttpRequest msg, Channel gatewayChannel) {
        if (clientChannel == null) {
            try {
                EventLoopGroup group = new NioEventLoopGroup(1);
                Bootstrap b = new Bootstrap();
                b.group(group)
                        .channel(NioSocketChannel.class)
                        .handler(new NoobNettyHttpInitializer());
                ChannelFuture channelFuture = b.connect(ip, port);
                clientChannel = channelFuture.channel();
                channelFuture.sync();
//                System.out.println("address: " + ip + ":" + port + ",,, " + clientChannel.pipeline().names());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (clientChannel.isOpen()) {
            try {
                NoobNettyHttpHandler handler = clientChannel.pipeline().get(NoobNettyHttpHandler.class);
                handler.setGatewayChannel(gatewayChannel);
            } catch (Exception e) {
                System.err.println("eee: " + e.getMessage());
            }
//            System.out.println("clientChannel.writeAndFlush");
            clientChannel.writeAndFlush(msg);
        } else {
            clientChannel = null;
        }
    }
}
