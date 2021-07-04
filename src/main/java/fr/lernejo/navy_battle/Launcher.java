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
        new Launcher().init(port, args[1]);
    }

    public void init(int port, String url) {
        UUID myUUID = UUID.randomUUID();
        try {
            HttpServerHelper httpServer = new HttpServerHelper(port);
            httpServer.init();
            httpServer.start();
            if (url != null){
                String message = "hello";
                String gameStarterJSON =  new ObjectMapper().writeValueAsString(new GameStarter(myUUID.toString(), httpServer.getURL(), message));
                httpServer.sendPostRequest(url, "/api/game/start", gameStarterJSON);
            }
        } catch (IOException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
