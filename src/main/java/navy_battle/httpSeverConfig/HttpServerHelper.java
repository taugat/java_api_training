package navy_battle.httpSeverConfig;

import com.sun.net.httpserver.HttpServer;
import navy_battle.controllers.MainController;
import navy_battle.controllers.iMainController;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;

public class HttpServerHelper {

    private final HttpServer httpServer;

    public HttpServerHelper(int port) throws IOException {
        this.httpServer = HttpServer.create(new InetSocketAddress(port), 0);
    }

    public void init(){
        iMainController mainController = new MainController(this);
        CreateContextHelper contextHelper = new CreateContextHelper();

        httpServer.setExecutor(Executors.newFixedThreadPool(1));
        contextHelper.createPingContext(httpServer, mainController);
        contextHelper.createApiGameStart(httpServer, mainController);
    }

    public void start() {
        httpServer.start();
    }

}
