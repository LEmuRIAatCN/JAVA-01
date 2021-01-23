import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;

import java.util.Objects;

import static io.netty.handler.codec.http.HttpHeaderNames.*;
import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderValues.*;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;

public class NoobNettyHttpServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        p.addLast(new HttpServerCodec());
        p.addLast(new HttpServerExpectContinueHandler());
        p.addLast(new NoobHttpHandler());
    }

    class NoobHttpHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            super.channelRegistered(ctx);
        }

        @Override
        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
            super.channelUnregistered(ctx);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            super.channelInactive(ctx);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

            if (msg instanceof HttpRequest) {
                HttpRequest httpRequest = (HttpRequest) msg;
                boolean keepAlive = HttpUtil.isKeepAlive(httpRequest);
                FullHttpResponse response = null;
                try {
//                    System.out.println(httpRequest);

                    response = new DefaultFullHttpResponse(httpRequest.protocolVersion(), OK,
                            Unpooled.wrappedBuffer("hi!".getBytes("UTF-8")));
                    if (keepAlive) {
                        if (!httpRequest.protocolVersion().isKeepAliveDefault()) {
                            response.headers().set(CONNECTION, KEEP_ALIVE);
                        }
                    } else {
                        response.headers().set(CONNECTION, CLOSE);
                    }
                } catch (Exception e) {
                    System.out.println("something wrong when processing Httprequest: " + e.getMessage());
                    System.out.println("response is returned with error msg");
                    response = new DefaultFullHttpResponse(httpRequest.protocolVersion(), NOT_FOUND,
                            Unpooled.wrappedBuffer(e.getMessage().getBytes("UTF-8")));
                } finally {
                    if (Objects.nonNull(response)) {
                        response.headers()
                                .set(CONTENT_TYPE, TEXT_PLAIN)
                                .setInt(CONTENT_LENGTH, response.content().readableBytes());
                    }
                    ChannelFuture channelFuture = ctx.write(response);
                    if (!keepAlive) {
                        channelFuture.addListener(ChannelFutureListener.CLOSE);
                    }
                }
            }

        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            System.out.println("error caught: " + cause.getMessage());
            super.exceptionCaught(ctx, cause);
        }
    }
}
