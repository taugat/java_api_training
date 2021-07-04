package navy_battle.httpSeverConfig;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class CallHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        final String RESPONSE = "OK";
        exchange.sendResponseHeaders(200, RESPONSE.length());
        try (OutputStream body = exchange.getResponseBody())
        {
            body.write(RESPONSE.getBytes());
            body.flush();
        }
    }
}
