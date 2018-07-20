package database;

import java.sql.*;


/**
 * Provide the connection with the database.
 */
public class DBConnector {

    /**
     * The database username.
     */
    private static final String USERNAME = "dbuser";

    /**
     * The database user's password.
     */
    private static final String PASSWORD = "dbpassword";

    /**
     * The internet address where the database is allocated.
     */
    private static final String CONNSTRING = "jdbc:mysql://localhost/CANIBAU?autoReconnect=true&useSSL=false";

    /**
     * The Connection object.
     */
    private Connection conn = null;

    /**
     * The object used for executing a static SQL statement and returning the results it produces.
     */
    private Statement stmt = null;

    /**
     * The result returned by the query.
     */
    private ResultSet rs = null;

    /**
     * The number of query affected by the update.
     */
    private int RowsAffected = 0;


    /**
     * Return the result of a query
     * @param query the query to be executed.
     * @return a ResultSet object that contains the result of the query executed.
     * @throws SQLException
     */
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


    /**
     * Execute a db operation of update.
     * @param query the query to be executed.
     * @return true if the update is correctly performed.
     * @throws SQLException
     */
    public boolean updateDB(String query) throws SQLException {
        try {

            conn = DriverManager.getConnection(CONNSTRING, USERNAME, PASSWORD);
            stmt = conn.createStatement();
            RowsAffected = stmt.executeUpdate(query);
            return true;

        }
        catch (SQLException e) {
            System.out.println("ERROR!!");
            System.err.println(e);
            stmt.close();
            conn.close();
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Close the connection with database.
     * This method must be used after a ResultSet object is be returned.
     * @throws SQLException
     */
    public void closeConnection() throws SQLException {
        conn.close();
        rs.close();
        stmt.close();
    }


    /**
     * Close the connection with database.
     * This method must be used after a db operation of update.
     * @throws SQLException
     */
    public void closeUpdate() throws SQLException {
        conn.close();
        stmt.close();
    }
}
