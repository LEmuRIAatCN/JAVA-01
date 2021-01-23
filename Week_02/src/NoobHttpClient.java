import okhttp3.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class NoobHttpClient {
    private static final String ENDPOINT = "http://localhost:8801";

    public static void main(String... args) throws Exception {
        OkHttpClient client = new OkHttpClient();

        // Create request for remote resource.
        Request request = new Request.Builder()
                .url(ENDPOINT)
                .addHeader("noob", "head")
                .get()
                .build();
        // try-with-resources帮我关闭
        // 只进行一次访问
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                Headers headers = response.headers();
                System.out.println("headers as follows");
                for (java.lang.String head : headers.names()) {
                    //show headers
                    System.out.println(head + ": " + headers.get(head));
                }
                //show body if it's String
                ResponseBody body = response.body();
                if (body != null) {
                    InputStream stream = body.byteStream();
                    InputStreamReader reader = new InputStreamReader(stream);
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    System.out.println("body string as follows");
                    while (true) {
                        String line = bufferedReader.readLine();
                        if (line != null) {
                            System.out.println(line);
                        } else {
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
