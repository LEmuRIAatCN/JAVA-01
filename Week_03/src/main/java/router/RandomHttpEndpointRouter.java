package router;

import java.util.List;
import java.util.Random;

public class RandomHttpEndpointRouter implements HttpEndpointRouter<Integer> {
    @Override
    public Integer route(List<String> urls) {
        int size = urls.size();
        Random random = new Random(System.currentTimeMillis());
        return random.nextInt(size);
    }
}
