package engine;

import engine.Customer;
import engine.DogSitter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Review {
    private Customer customer;
    private DogSitter dogSitter;
    private Date date;
    private int rating;
    private String title;
    private String comment;

    public Review(Customer customer, DogSitter dogSitter, Date date, int rating, String title, String comment) {
        this.customer = customer;
        this.dogSitter = dogSitter;
        this.date = date;
        this.rating = rating;
        this.title = title;
        this.comment = comment;
    }

    public String toString(){
        try {
            return "Title: " + title + "\nFrom: " + customer.email + "\nTo: " + dogSitter.email + "\nDate: " + dateStringConverter(date) + " at " + timeStringConverter(date) + "\nrating: " + starsRating() + "\n" + comment;
        } catch (ParseException e) {
            return "error!";
        }
    }

    private String starsRating(){
        switch (rating){
            case 1:
                return "*";
            case 2:
                return "**";
            case 3:
                return "***";
            case 4:
                return "****";
            case 5:
                return "*****";
            default:
                return "N.D.";
        }
    }

    private String dateStringConverter(Date dateToConvert) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String reportDate = sdf.format(dateToConvert);
        return reportDate;
    }

    private String timeStringConverter(Date dateToConvert) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String reportDate = sdf.format(dateToConvert);
        return reportDate;
    }


}
