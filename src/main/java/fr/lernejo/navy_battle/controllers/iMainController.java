package fr.lernejo.navy_battle.controllers;

import com.sun.net.httpserver.HttpExchange;
import fr.lernejo.navy_battle.utils.ControllerResponse;

public interface iMainController {

    ControllerResponse postCreateApiGameStartResponse(HttpExchange gameStarter) throws Exception;

    ControllerResponse getFire(HttpExchange exchange) throws Exception;

    void sendPostStartGame(String url);

    void sendGetFire(String url);
}
