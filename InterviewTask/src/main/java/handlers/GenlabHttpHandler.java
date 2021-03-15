package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database.EmbeddedDbUpdater;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class GenlabHttpHandler implements HttpHandler {

    private final static Logger LOGGER = Logger.getLogger(GenlabHttpHandler.class.getName());

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Map<String, Object> parameters = new HashMap<>();
        InputStreamReader inputStreamReader = new InputStreamReader(exchange.getRequestBody(), "utf-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        parseRecords(bufferedReader);

        // send response
        String response = "";
        for (String key : parameters.keySet())
            response += key + " = " + parameters.get(key) + "\n";
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void parseRecords(BufferedReader reader) throws IOException {
        String record;
        while ((record = reader.readLine()) != null) {
            LOGGER.info("record = " + record);
            EmbeddedDbUpdater.insertNewRecord(record);
        }
    }
}
