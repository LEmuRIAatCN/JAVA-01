import inbound.NoobGatewayServer;

public class NoobGatewayStarter {
    public static void main(String[] args){
        try {
            NoobGatewayServer.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
