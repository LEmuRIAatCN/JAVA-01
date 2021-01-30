package outbound;

import discoverer.Discoverer;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NoobClients {
    private static final String CLIENT_TYPE_NETTY = "netty";
    private static final String CLIENT_TYPE_OKHTTP = "okhttp";
    private static Map<String, NoobHttpClient> clients = new ConcurrentHashMap<>();
    private static List<String> remotes = Discoverer.get();

    static {
        String type = System.getProperty("noob.gateway.client.type", "netty");
        System.out.println("start to init NoobClients. Client type is: " + type);
        if (type.contentEquals("okhttp")) {
            for (String s : remotes) {
                String[] pair = s.split(":");
                clients.put(s, new NoobOkHttpClient(pair[0], Integer.parseInt(pair[1])));
            }
        }
        if(type.contentEquals("netty")){
            for (String s : remotes) {
                String[] pair = s.split(":");
                clients.put(s, new NoobNettyHttpClient(pair[0], Integer.parseInt(pair[1])));
            }
        }
        System.out.println(remotes);
    }
    public static Map<String, NoobHttpClient> getClients(){
        return clients;
    }

    public static List<String> getEndpoints(){
        return remotes;
    }

}
