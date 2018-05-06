package test;

import engine.Bank;

public class TestBank {

    public static void main(String[] args) {
        Bank bank = new Bank();
        //bank.printBankUsers();
        System.out.println(bank.getnTransaction());
        bank.makeBankTransaction("GIANNINORVEGESE@GMAIL.COM", "MARIOBIANCHI@LIBERO.COM", 1);
    }
}