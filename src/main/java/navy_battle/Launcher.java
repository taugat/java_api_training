package navy_battle;

import com.sun.net.httpserver.HttpServer;
import navy_battle.controllers.MainController;
import navy_battle.controllers.iMainController;
import navy_battle.httpSeverConfig.CallHandler;
import navy_battle.httpSeverConfig.CreateContextHelper;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Launcher {

    public static void main(String[] args) {
        new Launcher().launchHttpServer();
    }

    public void launchHttpServer() {

        iMainController mainController = new MainController();
        CreateContextHelper contextHelper = new CreateContextHelper();
        try {
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(9876), 0);
            httpServer.setExecutor(Executors.newFixedThreadPool(1));
            contextHelper.createPingContext(httpServer,mainController);
            contextHelper.createApiGameStart(httpServer,mainController);
            httpServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
