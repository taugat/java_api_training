package fr.lernejo.navy_battle.httpSeverConfig;

import com.sun.net.httpserver.HttpExchange;
import fr.lernejo.navy_battle.Launcher;
import fr.lernejo.navy_battle.controllers.iMainController;
import fr.lernejo.navy_battle.utils.ControllerResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class HttpServerHelperTest {

    @Test
    void contactSecondSever() throws IOException, ExecutionException, InterruptedException {
        final boolean[] createGame = {false};
        HttpServerHelper httpServerHelper = new HttpServerHelper(8795,  new iMainController() {
            @Override
            public ControllerResponse postCreateApiGameStartResponse(HttpExchange gameStarter) {
                createGame[0] = true;
                return null;
            }

            @Override
            public ControllerResponse getFire(HttpExchange exchange) {
                return null;
            }

            @Override
            public void sendPostStartGame(String url) {

            }
        });
        httpServerHelper.init();
        httpServerHelper.start(null);
        new Launcher().init(9876, "http://localhost:8795");
        assertTrue(createGame[0]);
    }

}