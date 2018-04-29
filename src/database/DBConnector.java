package database;
import java.sql.*;

public class DBConnector {

    private static final String USERNAME = "dbuser";
    private static final String PASSWORD = "dbpassword";
    // Ho appeso alla CONNSTRING la stringa "?autoReconnect=true&useSSL=false" disabilitando l'SSL e evitando un errore
    private static final String CONNSTRING = "jdbc:mysql://localhost/CANIBAU?autoReconnect=true&useSSL=false";

    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;

    public ResultSet askDB(String query) throws SQLException {
        try{
            conn = DriverManager.getConnection(CONNSTRING, USERNAME, PASSWORD);
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(query);
            return rs;
        }
        catch(SQLException e){
            System.out.println("ERROR!!");
            System.err.println(e);
            rs.close();
            stmt.close();
            conn.close();
            return null;
        }
    }

    public void closeConnection() throws SQLException {
        conn.close();
        rs.close();
        stmt.close();
    }
}