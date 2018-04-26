package engine;

import database.DBConnector;
import enumeration.TypeUser;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
    private String user = new String();
    private String password = new String();
    private TypeUser typeUser;

    public Login(){
        getAccessData();
    }

    private void getAccessData(){
        //get access data from database

        //temporary method
        //user = "user";
        //password = "password";

        //typeUser = enumeration.TypeUser.DOGSITTER;
        typeUser = TypeUser.CUSTOMER;
    }

    public boolean accessDataVerifier(String inputUser, String inputPasword) throws SQLException {

        //login automatico per velocizzare i test sulla GUI
        //inputUser = "user";
        //inputPasword = "password";

        DBConnector dbConnector = new DBConnector();
        ResultSet rs = dbConnector.askDB("SELECT * FROM CUSTOMERS WHERE EMAIL = '" + inputUser + "' AND PASSWORD = '" + inputPasword + "'");

        System.out.println(inputUser);
        rs.last();
        rs.getRow();
        if (rs.getRow() == 1){
            System.out.println("Access allowed!");
            dbConnector.closeConnection();
            return true;
        } else {
            System.out.println("Access denied!");
            dbConnector.closeConnection();
            return false;
        }
    }

    public TypeUser getTypeUser() {
        return typeUser;
    }
}
