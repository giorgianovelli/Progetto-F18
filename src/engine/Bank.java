package engine;


import database.DBConnector;
import enumeration.TypeUser;
import staticClasses.ObjectCreator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
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
            ResultSet rs = dbConnector.askDB("SELECT EMAIL FROM DOGSITTERS");
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

    public boolean makeBankTransaction(String emailCustomer, String emailDogsitter, double amount) {
        BankUser customer = listUser.get(emailCustomer);
        BankUser dogsitter = listUser.get(emailDogsitter);
        PaymentMethod pmCustomer = customer.getPaymentMethod();
        PaymentMethod pmDogsitter = dogsitter.getPaymentMethod();
        pmCustomer.setAmount(pmCustomer.getAmount() - amount);
        pmDogsitter.setAmount(pmDogsitter.getAmount() + amount);

        if (pmCustomer.getAmount() < 0) {
            System.out.println("Transazione non riuscita: credito insufficiente");
            return false;
        }
        else {
            System.out.println(pmCustomer.getAmount());
            System.out.println(pmDogsitter.getAmount());

            DBConnector dbConnector = new DBConnector();

            try {
                int updateCustomer = dbConnector.updateDB("UPDATE CREDIT_CARDS SET AMOUNT = " + pmCustomer.getAmount() + "WHERE NUM = '" + pmCustomer.getNumber() + "';");
                int updateDogsitter = dbConnector.updateDB("UPDATE CREDIT_CARDS SET AMOUNT = " + pmDogsitter.getAmount() + "WHERE NUM = '" + pmDogsitter.getNumber() + "';");
                dbConnector.closeUpdate();

                if (updateCustomer > 0 && updateDogsitter > 0) {
                    System.out.println("Importi trasferiti con successo: conti correnti aggiornati");
                } else {
                    System.out.println("Errore nel trasferimento");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return true;
    }


    public void printBankUsers() {
        for (String key: listUser.keySet()) {
            System.out.println(listUser.get(key));
        }
    }




}

