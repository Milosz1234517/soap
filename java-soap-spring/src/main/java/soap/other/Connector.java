package soap.other;

import soap.other.daoObjects.EventDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    public static Connection connectToDB() {
        Connection connection = null;
        try {
            String portNumber = "5432";
            String databaseName = "javadatabase";
            String loginName = "postgres";
            String password = "bazadanych";

            connection = DriverManager.getConnection("jdbc:postgresql://localhost:" + portNumber + "/" + databaseName, loginName, password);
            connection.setAutoCommit(false);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
