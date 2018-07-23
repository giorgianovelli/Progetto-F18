package interfaces;

import server.Assignment;
import server.Availability;
import server.DogSize;
import server.Review;
import server.places.Address;
import server.places.Area;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

/**
 * This interface provides all the parameters that a dog sitter has.
 */
public interface InterfaceDogSitter {

    /**
     * Get the list of the assignment of the dog sitter.
     * @return the HashMap of dog sitter's assignments. The Integer key is the code of the Assignment.
     */
    HashMap<Integer, Assignment> getAssignmentList();

    /**
     * Get the list of reviews written on the dog sitter.
     * @return the HashSet of reviews. The Integer value is the code of the Assignment.
     * associated to Review.
     */
    HashMap<Integer, Review> getReviewList();

    /**
     * Get dog sitter's availability.
     * @return the dog sitter's availability.
     */
    Availability getDateTimeAvailability();

    /**
     * Get true if dog sitter accepts cash.
     * @return true if dog sitter accepts cash payment.
     */
    boolean isAcceptingCash();

    /**
     * Get the number of dogs that dog sitter accepts simultaneously.
     * @return the number of dogs accepted simoultaneously by the dog sitter.
     */
    int getDogsNumber();

    /**
     * Get the list of dogs' size accepted by the dog sitters.
     * @return HashSet of DogSize accepted by dog sitter.
     */
    HashSet<DogSize> getListDogSize();

    /**
     * Get the dog sitter's area of work.
     * @return the area of work of dog sitter.
     */
    Area getArea();

    /**
     * Get the dog sitter's address.
     * @return the dog sitter's address.
     */
    Address getAddress();

    /**
     * Get the dog sitter's name.
     * @return the dog sitter's name.
     */
    String getName();

    /**
     * Get the dog sitter's surname.
     * @return the dog sitter's surname.
     */
    String getSurname();

    /**
     * Update the dog sitter's name.
     * @param name new name inserted by the dog sitter.
     * @return true if the update is successfully performed.
     */
    boolean updateName(String name);

    /**
     * Update the dog sitter's surname.
     * @param surname new surname inserted by the dog sitter.
     * @return true if the update is successfully performed.
     */
    boolean updateSurname(String surname);

    /**
     * Update the dog sitter's password.
     * @param password new password inserted by the dog sitter.
     * @return true if the update is successfully performed.
     */
    boolean updatePassword(String password);

    /**
     * Update the dog sitter's phone number.
     * @param phoneNumber new phone number inserted by the dog sitter.
     * @return true if the update is successfully performed.
     */
    boolean updatePhoneNumber(String phoneNumber);

    /**
     * Update the dog sitter's date of birth.
     * @param dateOfBirth new date of birth inserted by the dog sitter.
     * @return true if the update is successfully performed.
     */
    boolean updateDateOfBirth(Date dateOfBirth);

    /**
     * Update the dog sitter's address with the following data inserted by the customer.
     * @param country
     * @param city
     * @param street
     * @param number civic number
     * @param cap
     * @return true if the update is successfully performed.
     */
    boolean updateAddress(String country, String city, String street, String number, String cap);

    /**
     * Update the dog sitter's payment method with the following data inserted by the customer.
     * @param number card's number
     * @param name owner's name
     * @param surname owner's surname
     * @param expirationDate the card's expiration date.
     * @param cvv the card's secure code.
     * @return true if the update is successfully performed.
     */
    boolean updatePaymentMethod(String number, String name, String surname, Date expirationDate, String cvv);

    /**
     * Update the cashFlag of the dog sitter.
     * @param acceptCash new value of cashFlag.
     * @return true if the update is successfully performed.
     */
    boolean updateCashFlag(boolean acceptCash);

    /**
     * Update the state of assignment indicated with code.
     * @param code of the Assignment.
     * @param state new state of the Assignment.
     * @return true if the update is successfully performed.
     */
    boolean updateAssignmentState(int code, Boolean state);

    /**
     * Add a new city in which dog sitter works.
     * @param city new city where dog sitter can work.
     * @return true if the update is successfully performed.
     */
    boolean addNewPlaceArea(String city);

    /**
     * Remove a city in which dog sitter will no longer work.
     * @param city in which dog sitter will non longer work.
     * @return true if city is successfully removed.
     */
    boolean removePlaceArea(String city);

    /**
     * Update the number of dogs that a dog sitter can accepts simultaneously.
     * @param nDogs new number of dogs that dog sitter can accept simultaneously.
     * @return true if the update is successfully performed.
     */
    boolean updateDogsNumber(int nDogs);

    /**
     * Update the dogs' sizes accepted by the dog sitter.
     * @param small true if dog sitter accepts small dogs.
     * @param medium true if dog sitter accepts medium dogs.
     * @param big true if dog sitter accepts big dogs.
     * @param giant true if dog sitter accepts giant dogs.
     * @return true if the update is successfully performed.
     */
    boolean updateListDogSize(boolean small, boolean medium, boolean big, boolean giant);

    /**
     * Update dog sitter's availability.
     * @param availability new dog sitter's availability.
     * @return true if the update is successfully performed.
     */
    boolean updateDateTimeAvailability(Availability availability);

    /**
     * Get dog sitter's biography.
     * @return the dog sitter's biography.
     */
    String getBiography();

    /**
     * Reply to the customer's review.
     * @param code the code of the assignment associated to the review.
     * @param dogSitterReply the dog sitter's reply.
     * @return true if the update is successfully performed.
     */
    boolean replyToReview(int code, String dogSitterReply);

    /**
     * Get the customer's email of the assignment specified.
     * @param code the code of the assignment specified.
     * @return the customer's email of the assignment specified.
     */
    String getCustomerEmailOfAssignment(int code);
}
