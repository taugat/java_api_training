package fr.lernejo.navy_battle;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.lernejo.navy_battle.model.GameStarter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

class LauncherTest {

    @BeforeAll
    static void launch(){
        new Launcher().init(8795, null);
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
    void validGameStarterRequest() throws IOException, InterruptedException {
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
    void getGameStarterRequest() throws IOException, InterruptedException {
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
    void incorrectGameStarterRequest() throws IOException, InterruptedException {
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
    void contactSecondSever()
    {
        new Launcher().init(9876, "http://localhost:8795/");
    }
}
