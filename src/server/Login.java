package server;

import database.DBConnector;
import enumeration.TypeUser;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Login{
    private String user = new String();
    private String password = new String();
    private TypeUser typeUser;

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
