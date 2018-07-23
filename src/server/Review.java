/**
 * This class have attributes and methods of reviews of assignments.
 */

package server;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class contains the components to create and respond to a review.
 */
public class Review {

    /**
     * The assignment's code.
     */
    private int code;

    /**
     * The review's date.
     */
    private Date date;

    /**
     * The assignment's rating.
     */
    private int rating;

    /**
     * The review's title inserted by the customer.
     */
    private String title;

    /**
     * The review's description inserted by the customer.
     */
    private String comment;

    /**
     * The dog sitter's reply.
     */
    private String reply;


    /**
     * Create a new review with also the dog sitter's reply.
     * @param code the assignment's code.
     * @param date the review's date.
     * @param rating the assignment's rating.
     * @param title the review's title.
     * @param comment the review's description.
     * @param reply the dog sitter's reply.
     */
    public Review(int code, Date date, int rating, String title, String comment, String reply) {
        this.code = code;
        this.date = date;
        this.rating = rating;
        this.title = title;
        this.comment = comment;
        this.reply = reply;
    }


    /**
     * Create a new review.
     * @param code the assignment's code.
     * @param date the review's date.
     * @param rating the assignment's rating.
     * @param title the review's title.
     * @param comment the review's description.
     */
    public Review(int code, Date date, int rating, String title, String comment) {
        this.code = code;
        this.date = date;
        this.rating = rating;
        this.title = title;
        this.comment = comment;
        this.reply = null;
    }


    /**
     * Get a string for a review.
     * @return
     */
    public String toString(){
        try {
            return "Title: " + title + "\nDate: " + dateStringConverter(date) + " at " + timeStringConverter(date) + "\nrating: " + starsRating() + "\n" + comment;
        } catch (ParseException e) {
            return "error!";
        }
    }


    /**
     * Assign a string with stars according to the rating.
     * @return a string with stars.
     */
    public String starsRating(){
        switch (rating){
            case 1:
                return "★";
            case 2:
                return "★★";
            case 3:
                return "★★★";
            case 4:
                return "★★★★";
            case 5:
                return "★★★★★";
            default:
                return "N.D.";
        }
    }


    /**
     * Convert a date into a string.
     * @param dateToConvert the date to be converted.
     * @return the string corresponding to the date.
     * @throws ParseException
     */
    private String dateStringConverter(Date dateToConvert) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(dateToConvert);
    }


    /**
     * Convert a date into a string indicating a time
     * @param dateToConvert the date to be converted.
     * @return the string corresponding to a time.
     * @throws ParseException
     */
    private String timeStringConverter(Date dateToConvert) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(dateToConvert);
    }


    /**
     * Get the assignment's code.
     * @return the assignment's code.
     */
    public int getCode() {
        return code;
    }


    /**
     * Get the review's date.
     * @return the review's date.
     */
    public Date getDate() {
        return date;
    }


    /**
     * Get the assignment's rating.
     * @return the assignment's rating.
     */
    public int getRating() {
        return rating;
    }


    /**
     * Get the review's title
     * @return the review's title.
     */
    public String getTitle() {
        return title;
    }


    /**
     * Get the review's description.
     * @return the review's description.
     */
    public String getComment() {
        return comment;
    }


    /**
     * Get the dog sitter's reply.
     * @return the dog sitter's reply.
     */
    public String getReply() {
        return reply;
    }


    /**
     * Set the dog sitter's reply.
     * @param reply the dog sitter's reply
     */
    public void setReply(String reply) {
        this.reply = reply;
    }
}
