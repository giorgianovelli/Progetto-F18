package test;

import server.SignUp;
import server.bank.PaymentMethod;
import server.places.Address;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestSignUp {
    public static void main(String[] args) {
        SignUp signUp = new SignUp();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date birth = new Date();
        Date expiration = new Date();
        Address address = new Address("ITALY", "PIEVE PORTO MORONE", "VIA DEL CHI SA DOVE", "5", "27017");
        try {
            birth = dateFormat.parse("23/06/1996");
            expiration = dateFormat.parse("31/07/2023");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        PaymentMethod paymentMethod = new PaymentMethod("1234567890987654", "NICOLAS", "CAROLO", expiration, 000, 1000);
        signUp.customerSignUp("NICOLAS.CAROLO@EMAIL.COM", "NICOLAS", "CAROLO", "CIAOBELLO", "3337755000", birth, address, paymentMethod);
    }
}
