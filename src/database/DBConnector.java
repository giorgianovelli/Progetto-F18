package database;
import java.sql.*;

public class DBConnector {

    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";
    // Ho appeso alla CONNSTRING la stringa "?autoReconnect=true&useSSL=false" disabilitando l'SSL e evitando un errore
    private static final String CONNSTRING = "jdbc:mysql://localhost/canibau?autoReconnect=true&useSSL=false";

    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;

    public ResultSet askDB(String query) throws SQLException {

        // Commenta la successiva riga di codice se lavori con Java 6 o sup.
        //Class.forName("com.mysql.jdbc.Driver");

        /*Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;*/

        try{
            conn = DriverManager.getConnection(CONNSTRING, USERNAME, PASSWORD);
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(query);
            return rs;
        }
        catch(SQLException e){
            System.err.println(e);
            rs.close();
            stmt.close();
            conn.close();
            return null;
        /*} finally {
            if(rs != null){
                rs.close();
            }
            if(stmt != null){
                stmt.close();
            }
            if(conn != null){
                conn.close();
            }*/
        }
    }

    public void closeConnection() throws SQLException {
        conn.close();
        rs.close();
        stmt.close();
    }

    /*public int getRow() throws SQLException {
        return rs.getRow();
    }*/
}