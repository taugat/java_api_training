package fr.lernejo.navy_battle;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.lernejo.navy_battle.httpSeverConfig.HttpServerHelper;
import fr.lernejo.navy_battle.model.GameStarter;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class Launcher {

    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);
        String url = args.length>1 ? args[1] : null;
        new Launcher().init(port, url);
    }

    public void init(int port, String url) {
        try {
            HttpServerHelper httpServer = new HttpServerHelper(port);
            httpServer.init();
            httpServer.start(url);
        } catch (IOException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
