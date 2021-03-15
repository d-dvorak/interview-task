import com.sun.net.httpserver.HttpServer;
import database.EmbeddedDbConfiguration;
import handlers.GenlabHttpHandler;
import handlers.TestHttpHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class InterviewTaskApp {

    public static void main(String[] args) {

        // global loggin level
        Logger rootLogger = LogManager.getLogManager().getLogger("");
        rootLogger.setLevel(Level.OFF);
        for (Handler h : rootLogger.getHandlers()) {
            h.setLevel(Level.OFF);
        }

        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        // init db configuration
        EmbeddedDbConfiguration dbConfiguration = new EmbeddedDbConfiguration();
        dbConfiguration.init();

        // run local http server
        HttpServer server;
        try {
            server = HttpServer.create(new InetSocketAddress("localhost", 8001), 0);
            server.createContext("/test", new TestHttpHandler());
            server.createContext("/receive", new GenlabHttpHandler());
            server.setExecutor(threadPoolExecutor);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
