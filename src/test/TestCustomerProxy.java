package test;

import customerClient.CustomerProxy;
import server.Review;

public class TestCustomerProxy {
    public static void main(String[] args) {
        CustomerProxy proxy = new CustomerProxy();
        Review review = proxy.getReview(2);
        System.out.println(review.getTitle());
    }
}
