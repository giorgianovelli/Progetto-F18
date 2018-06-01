package test;

import customerClient.CustomerProxy;
import server.Review;

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
    }
}
