package navy_battle;

import navy_battle.httpSeverConfig.HttpServerHelper;

import java.io.IOException;

public class Launcher {

    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);
        new Launcher().launchHttpServer(port);
    }

    public void launchHttpServer(int port) {
        try {
            HttpServerHelper httpServer = new HttpServerHelper(port);
            httpServer.init();
            httpServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
