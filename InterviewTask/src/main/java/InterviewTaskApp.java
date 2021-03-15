import com.sun.net.httpserver.HttpServer;
import handlers.GenlabHttpHandler;
import handlers.TestHttpHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class InterviewTaskApp {

    public static void main(String[] args) {

        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

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
