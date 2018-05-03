package test;

import engine.Customer;
import engine.SearchEngine;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static staticClasses.ObjectCreator.createCustomerFromDB;

public class TestSearchEngine {
    public static void main(String[] args) {
        SearchEngine se = new SearchEngine();
        Customer c = createCustomerFromDB("RICCARDOGIURA@GMAIL.COM");
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date startDate = new Date();
        Date endDate = new Date();
        try {
            startDate = date.parse("05/05/2018 13:00");
            endDate = date.parse("05/05/2018 15:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        se.search(startDate,endDate, c.)
    }
}
