package fr.lernejo.navy_battle.httpSeverConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.controllers.iMainController;
import fr.lernejo.navy_battle.utils.ControllerResponse;

import java.io.IOException;
import java.net.HttpURLConnection;

public class CreateContextHelper
{
    public void createPingContext(HttpServer httpServer, iMainController mainController)
    {
        httpServer.createContext("/ping",
            new CallHandler() {
                @Override
                public void handle(HttpExchange exchange) throws IOException {
                    super.responseOK(exchange, HttpURLConnection.HTTP_OK,"OK");
                }
            });
    }
    public void createApiGameStart(HttpServer httpServer, iMainController mainController)
    {
        httpServer.createContext("/api/game/start", new CallHandler() {@Override protected ControllerResponse onPostResponse(HttpExchange exchange){
                    try{
                        return mainController.postCreateApiGameStartResponse(exchange);
                    } catch (Exception e) {
                        if (!(e instanceof  JsonProcessingException)) return new ControllerResponse(HttpURLConnection.HTTP_INTERNAL_ERROR,"Internal Error");
                        else return new ControllerResponse(HttpURLConnection.HTTP_BAD_REQUEST,"Bad Request");
                    }
                }});
    }
    public void createApiFire(HttpServer httpServer, iMainController mainController)
    {
        httpServer.createContext("/api/game/fire", new CallHandler() {@Override protected ControllerResponse onGetResponse(HttpExchange exchange) {
                    try {
                        return mainController.getFire(exchange);
                    } catch (Exception e) {
                        if (e instanceof JsonProcessingException) return new ControllerResponse(HttpURLConnection.HTTP_INTERNAL_ERROR,"Internal Error");
                        else return new ControllerResponse(HttpURLConnection.HTTP_BAD_REQUEST, "Bad Request");
                    }
                }});
    }
}
