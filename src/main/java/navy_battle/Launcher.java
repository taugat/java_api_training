package navy_battle;

import com.sun.net.httpserver.HttpServer;
import navy_battle.httpSeverConfig.CallHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Launcher {

    public static void main(String[] args) {
        new Launcher().launchHttpServer();
    }

    public void launchHttpServer() {
        try {
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(9876), 0);
            httpServer.setExecutor(Executors.newFixedThreadPool(1));
            httpServer.createContext("/ping", new CallHandler());
            httpServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
