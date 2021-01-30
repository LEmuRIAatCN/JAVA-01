package outbound;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.HttpRequest;

public interface NoobHttpClient {
    void request(HttpRequest msg, Channel gatewayChannel);
}
