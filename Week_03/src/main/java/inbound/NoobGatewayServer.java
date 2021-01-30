package inbound;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class NoobGatewayServer {
    private static final int SERVER_PORT = 8080;

    public static void run() throws InterruptedException {
        //netty http server
        EventLoopGroup boss = new NioEventLoopGroup(2);
        EventLoopGroup workers = new NioEventLoopGroup(40);
        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(boss, workers)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new NoobGatewayServerInitializer());
            bootstrap.bind(SERVER_PORT).sync().channel().closeFuture().sync();

        } finally {
            boss.shutdownGracefully();
            workers.shutdownGracefully();
        }

    }
}
