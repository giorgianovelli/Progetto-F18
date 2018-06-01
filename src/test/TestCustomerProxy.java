package test;

import customerClient.CustomerProxy;
import server.Review;

public class TestCustomerProxy {
    public static void main(String[] args) {
        CustomerProxy proxy = new CustomerProxy();
        Review review = proxy.getReview(2);
        System.out.println(review.getTitle());
        proxy.updateCustomerName("RICCARDOGIURA@GMAIL.COM", "RICCARDO");
        proxy.updateCustomerSurname("RICCARDOGIURA@GMAIL.COM", "GIURA");
        proxy.updateCustomerPassword("RICCARDOGIURA@GMAIL.COM", "CAMIBAU");
    }
}
