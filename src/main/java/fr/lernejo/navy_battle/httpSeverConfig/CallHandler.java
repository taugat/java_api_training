package fr.lernejo.navy_battle.httpSeverConfig;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.lernejo.navy_battle.utils.ControllerResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public abstract class CallHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        ControllerResponse controllerResponse = switch (exchange.getRequestMethod())
        {
            case "GET" -> onGetResponse(exchange);
            case "POST" -> onPostResponse(exchange);
            default -> null;
        };
        if (null != controllerResponse) {
            responseOK(exchange, controllerResponse.rCode, controllerResponse.response);
            if (controllerResponse.onResponseSendListener != null)
                controllerResponse.onResponseSendListener.onResponseSend();
        } else {
            responseNotfound(exchange);
        }
    }

    protected ControllerResponse onPostResponse(HttpExchange exchange){
        return null;
    }

    protected ControllerResponse onGetResponse(HttpExchange exchange){
        return null;
    }

    public void responseOK(HttpExchange exchange, int rCode, String response) throws IOException {
        exchange.sendResponseHeaders(rCode, response.length());
        try (OutputStream body = exchange.getResponseBody())
        {
            body.write(response.getBytes());
            body.flush();
        }
        exchange.close();
    }

    public void responseNotfound(HttpExchange exchange) throws IOException {
        String response = "Not Found";
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, response.length());
        try (OutputStream body = exchange.getResponseBody())
        {
            body.write(response.getBytes());
            body.flush();
        }
        exchange.close();
    }
}
