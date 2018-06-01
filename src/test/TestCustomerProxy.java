package test;

import customerClient.CustomerProxy;
import server.Review;

import javax.management.DescriptorAccess;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestCustomerProxy {
    public static void main(String[] args) {
        CustomerProxy proxy = new CustomerProxy();
        Review review = proxy.getReview(2);
        System.out.println(review.getTitle());
        proxy.updateCustomerName("RICCARDOGIURA@GMAIL.COM", "PIPINO");
        proxy.updateCustomerSurname("RICCARDOGIURA@GMAIL.COM", "IL BREVE");
        proxy.updateCustomerPassword("RICCARDOGIURA@GMAIL.COM", "CAMIBAU");
        proxy.updateCustomerPhoneNumber("RICCARDOGIURA@GMAIL.COM", "3333333333");
        proxy.updateCustomerDateOfBirth("RICCARDOGIURA@GMAIL.COM", "22/11/1963");
        proxy.updateCustomerAddress("RICCARDOGIURA@GMAIL.COM", "ITALY", "GENOVA", "VIA DEL PORTO", "1", "16121");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date expiration = new Date();
        try {
            expiration = dateFormat.parse("31/01/2010");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        proxy.updateCustomerPaymentMethod("RICCARDOGIURA@GMAIL.COM", "7372989101832834", "BENEDETTO", "SEDICESIMO", expiration, 555);
    }
}
