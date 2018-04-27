package engine;


import database.DBConnector;
import enumeration.TypeUser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

public class Bank {


    private HashMap<String, BankUser> listUser;
    private long nTransaction;

    public Bank(long nTransaction) {

        nTransaction = 0;
        this.listUser = new HashMap<String,BankUser>();

        DBConnector dbConnector = new DBConnector();
        try {
            ResultSet rs = dbConnector.askDB("SELECT EMAIL FROM CUSTOMERS");
            while (rs.next()) {
                String email = rs.getString("EMAIL");
                BankUser bu = new BankUser(email, TypeUser.CUSTOMER);
                listUser.put(email, bu);
            }

            dbConnector.closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ResultSet rs = dbConnector.askDB("SELECT * FROM DOGSITTERS");
            while (rs.next()){
                String email = rs.getString("EMAIL");
                BankUser bu = new BankUser(email, TypeUser.DOGSITTER);
                listUser.put(email, bu);
            }
            dbConnector.closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public long getnTransaction() {
        return nTransaction;
    }

    public boolean makeBankTransaction() {
        return true;
    }


    public void printBankUsers() {
        for (String key: listUser.keySet()) {
            System.out.println(listUser.get(key));
        }
    }



}

