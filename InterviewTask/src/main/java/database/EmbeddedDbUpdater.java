package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class EmbeddedDbUpdater {

    private final static Logger LOGGER = Logger.getLogger(EmbeddedDbUpdater.class.getName());
    static final String URL = "jdbc:h2:~/genlab-db;";

    static public void insertNewRecord(String data){

        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection(URL, "sa", "");

            Statement stmt = connection.createStatement();
            String sql = "INSERT INTO `genlab` (`data`) VALUES ('" + data + "')";
            stmt.executeUpdate(sql);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
