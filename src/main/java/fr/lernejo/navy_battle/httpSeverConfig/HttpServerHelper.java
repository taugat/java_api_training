package fr.lernejo.navy_battle.httpSeverConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.controllers.MainController;
import fr.lernejo.navy_battle.controllers.iMainController;
import fr.lernejo.navy_battle.model.GameStarter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class HttpServerHelper {

    private final HttpServer httpServer;
    private final iMainController mainController;

    public HttpServerHelper(int port) throws IOException {
        this.httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        this.mainController = new MainController(this);
    }

    public HttpServerHelper(int port, iMainController mainController) throws IOException {
        this.httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        this.mainController = mainController;
    }

    public void init(){

        CreateContextHelper contextHelper = new CreateContextHelper();

        httpServer.setExecutor(Executors.newFixedThreadPool(1));
        contextHelper.createPingContext(httpServer, mainController);
        contextHelper.createApiGameStart(httpServer, mainController);
        contextHelper.createApiFire(httpServer, mainController);
    }

    public void start(String url) throws ExecutionException, InterruptedException {
        httpServer.start();
        if (url != null){
            mainController.sendPostStartGame(url);
        }
    }

    public HttpResponse<String> sendPostRequest(String adversaryUrl, String path, String body) throws ExecutionException, InterruptedException {
        HttpRequest requestPost = HttpRequest.newBuilder()
            .uri(URI.create(adversaryUrl + path))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build();

        return HttpClient.newHttpClient().sendAsync(requestPost, HttpResponse.BodyHandlers.ofString())
            .get();
    }

    public HttpResponse<String> sendGetRequest(String adversaryUrl, String path) throws ExecutionException, InterruptedException {
        HttpRequest requestGet = HttpRequest.newBuilder()
            .uri(URI.create(adversaryUrl + path))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .GET()
            .build();

        return HttpClient.newHttpClient().sendAsync(requestGet, HttpResponse.BodyHandlers.ofString())
            .get();
    }

    public String getURL() {
        return "http://localhost:" + httpServer.getAddress().getPort();
    }

    public void stop() {
        httpServer.stop(0);
    }
}
