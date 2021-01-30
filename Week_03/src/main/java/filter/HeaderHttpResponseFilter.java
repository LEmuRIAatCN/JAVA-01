package filter;

import io.netty.handler.codec.http.HttpResponse;

public class HeaderHttpResponseFilter implements HttpResponseFilter {
    @Override
    public void filter(HttpResponse response) {
        response.headers().set("added-from-response-filter", "fall back");
    }
}
