package navy_battle;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class LauncherTest {

    @Test
    void launchHttpServer() throws IOException, InterruptedException {
        new Launcher().launchHttpServer();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(
            HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9876/ping"))
            .build(),
            HttpResponse.BodyHandlers.ofString()
        );

        assertEquals(response.statusCode(),200);
        assertEquals(response.body(),"OK");
    }
}
