package fr.lernejo.navy_battle;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.lernejo.navy_battle.httpSeverConfig.HttpServerHelper;
import fr.lernejo.navy_battle.model.GameStarter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

class LauncherTest {

    private static HttpServerHelper httpServerHelper1;

    @BeforeAll
    static void launch() throws IOException, ExecutionException, InterruptedException {
        httpServerHelper1 = new HttpServerHelper(8795);
        httpServerHelper1.init();
        httpServerHelper1.start(null);
    }

    @AfterAll
    static void stop(){

        httpServerHelper1.stop();
    }
    @Test
    void launchHttpServerPing() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(
            HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8795/ping"))
            .build(),
            HttpResponse.BodyHandlers.ofString()
        );

        assertEquals(response.statusCode(),200);
        assertEquals(response.body(),"OK");
    }

    @Test
    void validPostGameStarterRequest() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        String gameStarter= new ObjectMapper().writeValueAsString(new GameStarter(UUID.randomUUID().toString(),"http://localhost:9876","message"));

        HttpResponse<String> response = httpClient.send(
            HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8795/api/game/start"))
                .POST(HttpRequest.BodyPublishers.ofString(gameStarter))
                .build(),
            HttpResponse.BodyHandlers.ofString()
        );

        assertEquals(response.statusCode(),202);
    }

    @Test
    void invalidGetGameStarterRequest() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> response = httpClient.send(
            HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8795/api/game/start"))
                .GET()
                .build(),
            HttpResponse.BodyHandlers.ofString()
        );

        assertEquals(response.statusCode(),404);
    }

    @Test
    void invalidPostGameStarterRequest() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> response = httpClient.send(
            HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8795/api/game/start"))
                .POST(HttpRequest.BodyPublishers.ofString("{'id'='hjk'}"))
                .build(),
            HttpResponse.BodyHandlers.ofString()
        );

        assertEquals(response.statusCode(),400);
    }
    @Test
    void correctFireRequest() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> response = httpClient.send(
            HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8795/api/game/fire?cell=B7"))
                .GET()
                .build(),
            HttpResponse.BodyHandlers.ofString()
        );

        assertEquals(response.statusCode(),202);
    }
    @Test
    void invalidFireRequest() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> response = httpClient.send(
            HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8795/api/game/fire"))
                .GET()
                .build(),
            HttpResponse.BodyHandlers.ofString()
        );

        assertEquals(response.statusCode(),400);
    }
}
