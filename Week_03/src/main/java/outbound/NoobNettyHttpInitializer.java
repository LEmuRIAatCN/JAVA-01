package outbound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;

public class NoobNettyHttpInitializer extends ChannelInitializer<SocketChannel> {

    public NoobNettyHttpInitializer() {
        System.out.println("NoobNettyHttpInitializer");
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new HttpClientCodec())
                .addLast(new HttpContentDecompressor())
//                .addLast(new HttpObjectAggregator(1024 * 1024))
                .addLast(new NoobNettyHttpHandler())
        ;
    }


}
