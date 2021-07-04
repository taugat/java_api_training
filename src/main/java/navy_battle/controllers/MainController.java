package navy_battle.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import navy_battle.model.GameStarter;
import navy_battle.utils.ControllerResponse;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class MainController implements iMainController{

    @Override
    public ControllerResponse defaultPingResponse() {
        return new ControllerResponse(HttpURLConnection.HTTP_OK,"OK");
    }

    @Override
    public ControllerResponse postCreateApiGameStartResponse(HttpExchange httpExchange){
        ControllerResponse reponse;
        try {
            String requestBody = new String(httpExchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            GameStarter gameStarter = new ObjectMapper().readValue(requestBody, GameStarter.class);
            String gameStarterReponse = new ObjectMapper().writeValueAsString(new GameStarter(UUID.randomUUID().toString(),"http://localhost:9876","message"));
            reponse = new ControllerResponse(HttpURLConnection.HTTP_ACCEPTED, gameStarterReponse);
        } catch (IOException e) {
            if (!(e instanceof  JsonProcessingException)) {
                e.printStackTrace();
                reponse = new ControllerResponse(HttpURLConnection.HTTP_INTERNAL_ERROR,"Internal Error");
            }
            else
                reponse = new ControllerResponse(HttpURLConnection.HTTP_BAD_REQUEST,"Bad Request");
        }
        return reponse;
    }

}
