package fr.lernejo.navy_battle.httpSeverConfig;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.controllers.iMainController;
import fr.lernejo.navy_battle.utils.ControllerResponse;

public class CreateContextHelper
{
    public void createPingContext(HttpServer httpServer, iMainController mainController)
    {
        httpServer.createContext("/ping",
            new CallHandler() {
                @Override
                protected ControllerResponse onPostResponse(HttpExchange exchange){
                    return mainController.defaultPingResponse();
                }
                @Override
                protected ControllerResponse onGetResponse(HttpExchange exchange) {
                    return mainController.defaultPingResponse();
                }
            });
    }
    public void createApiGameStart(HttpServer httpServer, iMainController mainController)
    {
        httpServer.createContext("/api/game/start",
            new CallHandler() {
                @Override
                protected ControllerResponse onPostResponse(HttpExchange exchange){
                    return mainController.postCreateApiGameStartResponse(exchange);
                }
                @Override
                protected ControllerResponse onGetResponse(HttpExchange exchange) {
                    return null;
                }
            });
    }
}
