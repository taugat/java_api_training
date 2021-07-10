package fr.lernejo.navy_battle.controllers;

import com.sun.net.httpserver.HttpExchange;
import fr.lernejo.navy_battle.utils.ControllerResponse;

public interface iMainController {

    ControllerResponse postCreateApiGameStartResponse(HttpExchange gameStarter);

    ControllerResponse getFire(HttpExchange exchange);

    void sendPostStartGame(String url);
}
