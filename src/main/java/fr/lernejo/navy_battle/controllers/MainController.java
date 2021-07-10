package fr.lernejo.navy_battle.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import fr.lernejo.navy_battle.httpSeverConfig.HttpServerHelper;
import fr.lernejo.navy_battle.model.Cell;
import fr.lernejo.navy_battle.model.CellStatus;
import fr.lernejo.navy_battle.model.GameController;
import fr.lernejo.navy_battle.model.GameStarter;
import fr.lernejo.navy_battle.utils.ControllerResponse;
import fr.lernejo.navy_battle.utils.Utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class MainController implements iMainController{

    private final HttpServerHelper httpServerHelper;
    private final GameController gameController;
    private final UUID id = UUID.randomUUID();
    private final Utils utils;

    public MainController(HttpServerHelper httpServerHelper) {
        this.httpServerHelper = httpServerHelper;
        this.gameController = new GameController();
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
                sendGetFire(url);
            }
        } catch (ExecutionException | InterruptedException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void sendGetFire(String url) {
        try {
            Cell cell = gameController.doFire();
            HttpResponse<String> response = httpServerHelper.sendGetRequest(url, "/api/game/fire?cell=" + cell.toString());
            CellStatus cellStatus = new ObjectMapper().readValue(response.body(), CellStatus.class);
            gameController.resultFire(cellStatus.getConsequence(), cell);
            if (!cellStatus.isShipLeft()){
                gameController.endGame(true);
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
            String gameStarterResponse = new ObjectMapper().writeValueAsString(new GameStarter(id.toString(),httpServerHelper.getURL(),"May the best code win"));
            response = new ControllerResponse(HttpURLConnection.HTTP_ACCEPTED, gameStarterResponse);
        } catch (IOException e) {
            if (!(e instanceof  JsonProcessingException)) {
                e.printStackTrace();
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
            Cell cell = new Cell(requestURI.get("cell"));
            try {
                CellStatus cellStatus = gameController.getCellStatus(cell);
                if (!cellStatus.isShipLeft())
                    gameController.endGame(false);
                response = new ControllerResponse(HttpURLConnection.HTTP_ACCEPTED, new ObjectMapper().writeValueAsString(cellStatus));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                response = new ControllerResponse(HttpURLConnection.HTTP_INTERNAL_ERROR,"Internal Error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = new ControllerResponse(HttpURLConnection.HTTP_BAD_REQUEST, "Bad Request");
        }
        return response;
    }
}
