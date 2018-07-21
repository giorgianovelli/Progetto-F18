package server.bank;


import database.DBConnector;
import enumeration.TypeUser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static server.tools.DoubleTools.round2Decimal;

public class Bank {

    /**
     * The list of users.
     */
    private HashMap<String, BankUser> listUser;

    /**
     * The number of transactions done.
     */
    private long nTransaction;


    /**
     * Create a new Bank.
     */
    public Bank() {
        nTransaction = countTransaction();
        this.listUser = new HashMap<>();

        DBConnector dbConnector = new DBConnector();
        try {
            ResultSet rs = dbConnector.askDB("SELECT EMAIL FROM CUSTOMERS");
            while (rs.next()) {
                String email = rs.getString("EMAIL");
                BankUser bu = new BankUser(email, TypeUser.CUSTOMER);
                listUser.put(email, bu);
            }

            dbConnector.closeConnection();

            rs = dbConnector.askDB("SELECT EMAIL FROM DOGSITTERS");
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


    /**
     * Make a bank transaction.
     * @param emailCustomer the customer's email.
     * @param emailDogsitter the dog sitter's email.
     * @param code the assignment's code.
     * @param amount the price for the assignment.
     * @return true if transection is successfully completed.
     */
    public boolean makeBankTransaction(String emailCustomer, String emailDogsitter, int code, double amount) {
        BankUser customer = listUser.get(emailCustomer);
        BankUser dogsitter = listUser.get(emailDogsitter);
        PaymentMethod pmCustomer = customer.getPaymentMethod();
        PaymentMethod pmDogsitter = dogsitter.getPaymentMethod();
        pmCustomer.setAmount(round2Decimal(pmCustomer.getAmount() - amount));
        pmDogsitter.setAmount(round2Decimal(pmDogsitter.getAmount() + amount));

        if (pmCustomer.getExpirationDate().after(new Date())){
            System.out.println("Transaction failed: customer's credit cards is expired");
            return false;
        }

        if (pmDogsitter.getExpirationDate().after(new Date())){
            System.out.println("Transaction failed: dog sitter's credit cards is expired");
            return false;
        }

        if (pmCustomer.getAmount() < 0) {
            System.out.println("Transaction failed: insufficient credit");
            return false;
        }
        else {
            System.out.println(emailCustomer + ": €" + pmCustomer.getAmount());
            System.out.println(emailDogsitter + ": €" + pmDogsitter.getAmount());
            DBConnector dbConnector = new DBConnector();
            try {
                boolean updateCustomer = dbConnector.updateDB("UPDATE CREDIT_CARDS SET AMOUNT = " + pmCustomer.getAmount() + "WHERE NUM = '" + pmCustomer.getNumber() + "';");
                boolean updateDogsitter = dbConnector.updateDB("UPDATE CREDIT_CARDS SET AMOUNT = " + pmDogsitter.getAmount() + "WHERE NUM = '" + pmDogsitter.getNumber() + "';");
                dbConnector.closeUpdate();

                if (updateCustomer && updateDogsitter) {
                    System.out.println("Amount: €" + amount);
                    System.out.println("Amounts transferred successfully: current accounts updated");

                    SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String strDate = date.format(new Date());

                    dbConnector.updateDB("INSERT INTO TRANSACTIONS VALUES ('" + emailCustomer + "', '" + emailDogsitter + "', '" + strDate + "', " + code + ", " + amount + ")");
                    dbConnector.closeUpdate();
                } else {
                    System.out.println("Error in transaction");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }


    /**
     * Get the number of transactions done.
     * @return the number of transactions done.
     */
    private int countTransaction(){
        DBConnector dbConnector = new DBConnector();
        try {
            ResultSet rs = dbConnector.askDB("SELECT * FROM TRANSACTIONS");
            rs.last();
            int nTransaction = rs.getRow();
            dbConnector.closeConnection();
            return nTransaction;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;  //error
        }
    }


    /**
     * Verify if transaction is possible.
     * @param emailCustomer the customer's email.
     * @param amount the price for the assignment.
     * @return true if the transaction is possible.
     */
    public boolean isTransactionPossible(String emailCustomer, String emailDogSitter, double amount){
        BankUser customer = listUser.get(emailCustomer);
        BankUser dogSitter = listUser.get(emailDogSitter);
        PaymentMethod pmCustomer = customer.getPaymentMethod();
        PaymentMethod pmDogsitter = dogSitter.getPaymentMethod();

        if (pmCustomer.getExpirationDate().after(new Date())){
            System.out.println("Transaction failed: customer's credit cards is expired");
            return false;
        }

        if (pmDogsitter.getExpirationDate().after(new Date())){
            System.out.println("Transaction failed: dog sitter's credit cards is expired");
            return false;
        }
        if (pmCustomer.getAmount() - amount < 0){
            System.out.println("The transaction can not be made: insufficient credit");
            return false;
        } else {
            return true;
        }
    }


    /**
     * Refund the customer in case of cancellation of assignment.
     * @param code the assignment's code.
     * @return true if the customer is successfully refunded.
     */
    public boolean refundCustomer(int code){
        DBConnector dbConnector = new DBConnector();
        String emailCustomer = "";
        String emailDogsitter = "";
        double amount = 0;
        try {
            ResultSet rs = dbConnector.askDB("SELECT CUSTOMER, DOGSITTER FROM ASSIGNMENT WHERE CODE = " + code);
            rs.next();
            emailCustomer = rs.getString("CUSTOMER");
            emailDogsitter = rs.getString("DOGSITTER");
            dbConnector.closeConnection();
            rs = dbConnector.askDB("SELECT AMOUNT FROM TRANSACTIONS WHERE CODE_ASSIGNMENT = " + code);
            rs.last();
            if (rs.getRow() == 0){
                System.out.println("None transaction associated to code " + code + "!");
                return false;
            }
            amount = rs.getDouble("AMOUNT");
            dbConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        BankUser customer = listUser.get(emailCustomer);
        BankUser dogsitter = listUser.get(emailDogsitter);
        PaymentMethod pmCustomer = customer.getPaymentMethod();
        PaymentMethod pmDogsitter = dogsitter.getPaymentMethod();
        pmCustomer.setAmount(round2Decimal(pmCustomer.getAmount() + amount));
        pmDogsitter.setAmount(round2Decimal(pmDogsitter.getAmount() - amount));

        if (pmCustomer.getExpirationDate().after(new Date())){
            System.out.println("Transaction failed: customer's credit cards is expired");
            return false;
        }

        if (pmDogsitter.getExpirationDate().after(new Date())){
            System.out.println("Transaction failed: dog sitter's credit cards is expired");
            return false;
        }

        if (pmDogsitter.getAmount() < 0) {
            System.out.println("Refund failed: insufficient credit");
            return false;
        }
        else {
            System.out.println(emailCustomer + ": €" + pmCustomer.getAmount());
            System.out.println(emailDogsitter + ": €" + pmDogsitter.getAmount());
            try {
                boolean updateCustomer = dbConnector.updateDB("UPDATE CREDIT_CARDS SET AMOUNT = " + pmCustomer.getAmount() + "WHERE NUM = '" + pmCustomer.getNumber() + "';");
                boolean updateDogsitter = dbConnector.updateDB("UPDATE CREDIT_CARDS SET AMOUNT = " + pmDogsitter.getAmount() + "WHERE NUM = '" + pmDogsitter.getNumber() + "';");
                dbConnector.closeUpdate();

                if (updateCustomer && updateDogsitter) {
                    System.out.println("Refund completed successfully: current accounts updated");

                    dbConnector.updateDB("DELETE FROM TRANSACTIONS WHERE CODE_ASSIGNMENT = " + code);
                    dbConnector.closeUpdate();
                } else {
                    System.out.println("Error in refunding");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return true;
    }
}

