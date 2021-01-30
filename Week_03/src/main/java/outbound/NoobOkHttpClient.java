package outbound;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;


public class NoobOkHttpClient implements NoobHttpClient {
    private final String ip;
    private final int port;
    private final OkHttpClient client = new OkHttpClient();

    public NoobOkHttpClient(String ip, int port) {

        this.ip = ip;
        this.port = port;
    }

    public void request(HttpRequest msg, Channel gatewayChannel) {
        Request request = new Request.Builder()
                .url(ip + ":" + port)
                //TODO HttpRequest相关参数转换到okhttp的Request对象中
                .addHeader("noob", "head")
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (gatewayChannel != null && gatewayChannel.isOpen()) {
                gatewayChannel.writeAndFlush(handleResponse(response));
            } else {
                gatewayChannel = null;
                System.out.println("NoobNettyHttpHandler gatewatChannel is down ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private HttpResponse handleResponse(Response response){
        //TODO
        try {
            return new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.valueOf(response.code()), Unpooled.wrappedBuffer(response.body().bytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST);
    }

}
