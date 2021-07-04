package navy_battle.httpSeverConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import navy_battle.controllers.iMainController;
import navy_battle.model.GameStarter;
import navy_battle.utils.ControllerResponse;

import java.io.IOException;
import java.io.UTFDataFormatException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

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
