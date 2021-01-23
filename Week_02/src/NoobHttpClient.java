import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.Closeable;
import java.io.IOException;


public class NoobHttpClient {
    private static final String ENDPOINT = "http://localhost:8801";

    public static void main(String... args) throws Exception {
        OkHttpClient client = new OkHttpClient();

        // Create request for remote resource.
        Request request = new Request.Builder()
                .url(ENDPOINT)
                .build();

        // try-with-resources帮我关闭
        try (Response response = client.newCall(request).execute()) {
            // Deserialize HTTP response to concrete type.
            ResponseBody body = response.body();
            System.out.println(body);
        }

    }

}
