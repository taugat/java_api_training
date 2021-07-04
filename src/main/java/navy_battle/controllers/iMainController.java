package navy_battle.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.net.httpserver.HttpExchange;
import navy_battle.utils.ControllerResponse;

import java.io.IOException;

public interface iMainController {

    ControllerResponse defaultPingResponse();

    ControllerResponse postCreateApiGameStartResponse(HttpExchange gameStarter);
}
