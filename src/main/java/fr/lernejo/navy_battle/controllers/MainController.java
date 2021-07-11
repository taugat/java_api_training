package fr.lernejo.navy_battle.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import fr.lernejo.navy_battle.httpSeverConfig.HttpServerHelper;
import fr.lernejo.navy_battle.model.CellLocation;
import fr.lernejo.navy_battle.model.RoundStatus;
import fr.lernejo.navy_battle.model.GameStarter;
import fr.lernejo.navy_battle.utils.ControllerResponse;
import fr.lernejo.navy_battle.utils.Utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class MainController implements iMainController{

    private final HttpServerHelper httpServerHelper;
    private final List<GameController> gameControllers = new ArrayList<>();
    private final UUID id = UUID.randomUUID();
    private final Utils utils;

    public MainController(HttpServerHelper httpServerHelper) {
        this.httpServerHelper = httpServerHelper;
        this.utils = new Utils();
    }

    @Override
    public void sendPostStartGame(String url) {
        String message = "hello";
        try {
            String gameStarterJSON =  new ObjectMapper().writeValueAsString(new GameStarter(id.toString(), httpServerHelper.getURL(), message));
            HttpResponse<String> response = httpServerHelper.sendPostRequest(url, "/api/game/start", gameStarterJSON);
            if (response.statusCode() == HttpURLConnection.HTTP_ACCEPTED)
            {
                GameStarter gameStarter = new ObjectMapper().readValue(response.body(), GameStarter.class);
                gameControllers.add(new GameController(gameStarter.getId()));
                sendGetFire(url);
            }
        } catch (ExecutionException | InterruptedException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendGetFire(String url) {
        try {
            CellLocation cellLocation = gameControllers.get(0).doFire();
            HttpResponse<String> response = httpServerHelper.sendGetRequest(url, "/api/game/fire?cell=" + cellLocation.toString());
            RoundStatus roundStatus = new ObjectMapper().readValue(response.body(), RoundStatus.class);
            gameControllers.get(0).resultFire(roundStatus.getConsequence(), cellLocation);
            if (!roundStatus.isShipLeft()){
                gameControllers.get(0).endGame(true);
                //httpServerHelper.stop();
            }
        } catch (ExecutionException | InterruptedException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ControllerResponse postCreateApiGameStartResponse(HttpExchange httpExchange){
        ControllerResponse response;
        try {
            String requestBody = new String(httpExchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            GameStarter gameStarter = new ObjectMapper().readValue(requestBody, GameStarter.class);
            gameControllers.add(new GameController(gameStarter.getId()));
            String gameStarterResponse = new ObjectMapper().writeValueAsString(new GameStarter(id.toString(),httpServerHelper.getURL(),"May the best code win"));
            response = new ControllerResponse(HttpURLConnection.HTTP_ACCEPTED, gameStarterResponse);
        } catch (IOException e) {
            if (!(e instanceof  JsonProcessingException)) {
                response = new ControllerResponse(HttpURLConnection.HTTP_INTERNAL_ERROR,"Internal Error");
            }
            else
                response = new ControllerResponse(HttpURLConnection.HTTP_BAD_REQUEST,"Bad Request");
        }
        return response;
    }

    @Override
    public ControllerResponse getFire(HttpExchange exchange) {
        ControllerResponse response;

        try {
            Map<String,String> requestURI = utils.decodeParams(
                exchange.getRequestURI().getRawQuery()
            );
            CellLocation cellLocation = new CellLocation(requestURI.get("cell"));
            if (gameControllers.size() > 0)
            {
                RoundStatus roundStatus = gameControllers.get(0).getRoundStatus(cellLocation);
                if (!roundStatus.isShipLeft())
                    gameControllers.get(0).endGame(false);
                response = new ControllerResponse(HttpURLConnection.HTTP_ACCEPTED, new ObjectMapper().writeValueAsString(roundStatus));
            }
            else response = new ControllerResponse(HttpURLConnection.HTTP_ACCEPTED, "");//TODO
        } catch (Exception e) {
            if (e instanceof JsonProcessingException)
                response = new ControllerResponse(HttpURLConnection.HTTP_INTERNAL_ERROR,"Internal Error");
            else
                response = new ControllerResponse(HttpURLConnection.HTTP_BAD_REQUEST, "Bad Request");
        }
        return response;
    }
}
