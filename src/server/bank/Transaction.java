package server.bank;

import server.bank.BankUser;

import java.util.Date;

public class Transaction {

    private BankUser payer;
    private BankUser receiver;
    private double transactionAmount;
    private Date transactionDate;
    private long transactionCode;

    public Transaction(BankUser payer, BankUser receiver, double transactionAmount) {
        this.payer = payer;
        this.receiver = receiver;
        this.transactionAmount = transactionAmount;
        this.transactionDate = new Date();
        // this.transactionCode = get
    }
}
