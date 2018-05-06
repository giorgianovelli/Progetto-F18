package test;

import engine.Bank;

public class TestBank {

    public static void main(String[] args) {
        Bank bank = new Bank(0);
        //bank.printBankUsers();
        bank.makeBankTransaction("GIANNINORVEGESE@GMAIL.COM", "MARIOBIANCHI@LIBERO.COM", 18000);

    }
}