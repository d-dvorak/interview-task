package database;

import handlers.GenlabHttpHandler;

import java.sql.*;
import java.util.logging.Logger;

public class EmbeddedDbConfiguration {

    private final static Logger LOGGER = Logger.getLogger(EmbeddedDbConfiguration.class.getName());
    static final String URL = "jdbc:h2:~/genlab-db;";

    public void init(){

        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection(URL, "sa", "");

            Statement stmt = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS `genlab` (\n" +
                    "    `id` INTEGER NOT NULL AUTO_INCREMENT,\n" +
                    "    `data` VARCHAR(2048),\n" +
                    "    PRIMARY KEY ( `id` )\n" +
                    ")";
            stmt.executeUpdate(sql);

            LOGGER.info("Table created...");
            stmt.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}
