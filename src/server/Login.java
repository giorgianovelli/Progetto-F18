package server;

import database.DBConnector;
import enumeration.TypeUser;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class allows the login to the project.
 */
public class Login{
    /**
     * The user.
     */
    private String user;
    /**
     * The user's password.
     */
    private String password;
    /**
     * The type user.
     */
    private TypeUser typeUser;


    /**
     * Perform the login of a customer through the database.
     * @param inputUser the email address inserted by the user.
     * @param inputPasword the password inserted by the user.
     * @return true if the access is allowed ,else
     * @return false
     */
    public boolean customerAccessDataVerifier(String inputUser, String inputPasword) throws SQLException {
        String user = "";
        DBConnector dbConnector = new DBConnector();
        ResultSet rs = dbConnector.askDB("SELECT * FROM CUSTOMERS WHERE EMAIL = '" + inputUser + "' AND PASSWORD = '" + inputPasword + "'");

        System.out.println(inputUser);
        rs.last();
        rs.getRow();
        if (rs.getRow() == 1){
            System.out.println("Access allowed as customer!");
            dbConnector.closeConnection();
            return true;
        } else {
            System.out.println("Access denied as customer!");
            dbConnector.closeConnection();
            return false;
        }
    }

    /**
     * Perform the login of a dogsitter through the database.
     * @param inputUser the email address inserted by the user.
     * @param inputPasword the password inserted by the user.
     * @return true if the access is allowed ,else
     * @return false
     */
    public boolean dogSitterAccessDataVerifier(String inputUser, String inputPasword) throws SQLException {
        String user = "";
        DBConnector dbConnector = new DBConnector();
        ResultSet rs = dbConnector.askDB("SELECT * FROM DOGSITTERS WHERE EMAIL = '" + inputUser + "' AND PASSWORD = '" + inputPasword + "'");

        System.out.println(inputUser);
        rs.last();
        rs.getRow();
        if (rs.getRow() == 1){
            System.out.println("Access allowed as dogsitter!");
            dbConnector.closeConnection();
            return true;
        } else {
            System.out.println("Access denied as dogsitter!");
            dbConnector.closeConnection();
            return false;
        }
    }

    public TypeUser getTypeUser() {
        return typeUser;
    }
}
