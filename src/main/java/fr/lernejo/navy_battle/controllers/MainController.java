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
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class MainController implements iMainController{

    private final HttpServerHelper httpServerHelper;
    private final Map<String, GameController> gameControllers = new HashMap<>();
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
                gameControllers.put(url, new GameController(gameStarter.getId()));
                sendGetFire(url);
            }
        } catch (ExecutionException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendGetFire(String url) {
        try {
            CellLocation cellLocation = gameControllers.get(url).doFire();
            HttpResponse<String> response = httpServerHelper.sendGetRequest(url, "/api/game/fire?cell=" + cellLocation.toString());
            RoundStatus roundStatus = new ObjectMapper().readValue(response.body(), RoundStatus.class);
            gameControllers.get(url).resultFire(roundStatus.getConsequence(), cellLocation);
            if (!roundStatus.isShipLeft()){
                gameControllers.get(url).endGame(true);
            }
        } catch (ExecutionException | InterruptedException | IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ControllerResponse postCreateApiGameStartResponse(HttpExchange httpExchange) throws Exception{
            String requestBody = new String(httpExchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            GameStarter gameStarter = new ObjectMapper().readValue(requestBody, GameStarter.class);
            gameControllers.put(gameStarter.getUrl(), new GameController(gameStarter.getId()));
            String gameStarterResponse = new ObjectMapper().writeValueAsString(new GameStarter(id.toString(),httpServerHelper.getURL(),"May the best code win"));
            return new ControllerResponse(HttpURLConnection.HTTP_ACCEPTED, gameStarterResponse);
    }

    @Override
    public ControllerResponse getFire(HttpExchange exchange) throws Exception{
        Map<String,String> requestURI = utils.decodeParams(exchange.getRequestURI().getRawQuery());
        CellLocation cellLocation = new CellLocation(requestURI.get("cell"));
        String url = gameControllers.entrySet().iterator().next().getKey();
        if (gameControllers.containsKey(url)) {
            GameController cgameController = gameControllers.entrySet().iterator().next().getValue();
            RoundStatus roundStatus = cgameController.getRoundStatus(cellLocation);
            ControllerResponse.iOnResponseSentListener onResponseSentListener = null;
            if (!roundStatus.isShipLeft()) cgameController.endGame(false);
            else onResponseSentListener = () -> sendGetFire(url);
            return new ControllerResponse(HttpURLConnection.HTTP_ACCEPTED, new ObjectMapper().writeValueAsString(roundStatus), onResponseSentListener);
        }
        else return new ControllerResponse(HttpURLConnection.HTTP_ACCEPTED, "Game Not Started");
    }
}
