package interfaces;

import server.*;
import server.bank.PaymentMethod;
import server.places.Address;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public interface InterfaceCustomer {

    /**
     * Confirm the assignment for the following parameters.
     * @param emailDogSitter dog sitter's email address.
     * @param dateStartAssignment start of the assignment.
     * @param dateEndAssignment end of the assignment.
     * @param selectedDogs the HashSet of dogs to assign to the dog sitter.
     * @param meetingPoint place (Address type) in which customer and dog sitter will meet.
     * @param paymentInCash true if customer will pay dog sitter in cash.
     * @return true if assignment is successfully performed.
     */
    boolean addAssignment(String emailDogSitter, Date dateStartAssignment, Date dateEndAssignment, HashSet<Dog> selectedDogs, Address meetingPoint, boolean paymentInCash);

    /**
     * Remove the assignment associated to code.
     * @param code of the assignment to remove.
     * @return true if the assignment is successfully removed.
     */
    boolean removeAssignment(int code);

    /**
     * Add a review.
     * @param codeAssignment the code of the assignment that customer want to review.
     * @param emailDogSitter dog sitter's email address.
     * @param rating int value from 1 to 5.
     * @param title the title of the review.
     * @param comment the description inserted by the customer.
     * @return true if review is successfully created.
     */
    boolean addReview(int codeAssignment, String  emailDogSitter, int rating, String title, String comment);

    /**
     * Remove the review related to code.
     * @param code the code of the review that the customer want to remove.
     * @return true if the review is successfully removed.
     */
    boolean removeReview(Integer code);

    /**
     * Add a new dog with the following data to the dogs' list
     * @param customerEmail email address of the owner.
     * @param name dog's name.
     * @param breed dog's breed.
     * @param dateOfBirth dog's date of birth.
     * @param weight dog's weight.
     * @return true if the new dog is successfully addedd.
     */
    boolean addDog(String customerEmail, String name, String breed, Date dateOfBirth, double weight);

    /**
     * Disable a dog.
     * @param ID the ID of the dog that customer want to disable.
     * @return true if the dog is successfully disabled.
     */
    boolean disableDog(int ID);

    /**
     *
     * @return the HashMap of customer's assignments. The Integer key is the code of the Assignment.
     */
    HashMap<Integer, Assignment> getAssignmentList();

    /**
     * Get the list of the reviews written by the customer.
     * @return the HashMap of the Reviews written by the customer. The Integer key is the
     * assignment's code related to review.
     */
    HashMap<Integer, Review> getReviewList();

    /**
     * Get the customer's address.
     * @return the customer's Address.
     */
    Address getAddress();

    /**
     * Get the list of dogs of the customer.
     * @return the HashSet containing the dogs of the customer.
     */
    HashSet<Dog> getDogList();

    /**
     * Perform a search of dog sitters available based on the following parameters.
     * @param dateStart start of the assignment.
     * @param dateEnd end of assignment.
     * @param meetingPoint place (Address type) in which customer and dog sitter will meet.
     * @param dogList list of dogs to assign to dog sitter.
     * @param cash true if customer will pay dog sitter in cash.
     * @return the HashSet containing email address of available dog sitters.
     */
    HashSet<String> search(Date dateStart, Date dateEnd, Address meetingPoint, HashSet<Dog> dogList, boolean cash);

    /**
     * Estimate the assignment's price based on following parameters.
     * @param dogList list of dogs to assign to dog sitter.
     * @param dateStart start of assignment.
     * @param dateEnd end of assignment.
     * @return the assignment's price.
     */
    double estimatePriceAssignment(HashSet<Dog> dogList, Date dateStart, Date dateEnd);

    /**
     * Get the customer's payment method.
     * @return the customer's PaymentMethod.
     */
    PaymentMethod getPaymentMethod();

    /**
     * Update the customer's name.
     * @param name new name inserted by the customer.
     * @return true if the update is successfully performed.
     */
    boolean updateName(String name);

    /**
     * Update the customer's surname.
     * @param surname new surname inserted by the customer.
     * @return true if the update is successfully performed.
     */
    boolean updateSurname(String surname);

    /**
     * Update the customer's password.
     * @param password new password inserted by the customer.
     * @return true if the update is successfully performed.
     */
    boolean updatePassword(String password);

    /**
     * Update the customer's phone number.
     * @param phoneNumber new phone number inserted by the customer.
     * @return true if the update is successfully performed.
     */
    boolean updatePhoneNumber(String phoneNumber);

    /**
     * Update the customer's date of birth.
     * @param dateOfBirth new date of birth inserted by the customer.
     * @return true if the update is successfully performed.
     */
    boolean updateDateOfBirth(Date dateOfBirth);

    /**
     * Update the customer's address with the following data inserted by the customer.
     * @param country the country in which the customer lives.
     * @param city the city where the customer lives.
     * @param street the street in which customer lives.
     * @param number civic number.
     * @param cap the cap of the city where the customer lives.
     * @return true if the update is successfully performed.
     */
    boolean updateAddress(String country, String city, String street, String number, String cap);

    /**
     * Update the customer's payment method with the following data inserted by the customer.
     * @param number card's number.
     * @param name owner's name.
     * @param surname owner's surname.
     * @param expirationDate the card's expiration date.
     * @param cvv the secure code of the card.
     * @return true if the update is successfully performed.
     */
    boolean updatePaymentMethod(String number, String name, String surname, Date expirationDate, String cvv);

    /**
     * Get the customer's name.
     * @return the customer's name.
     */
    String getName();

    /**
     * Get the customer's surname.
     * @return the customer's surname.
     */
    String getSurname();

    /**
     * Get the customer's password.
     * @return the customer's password.
     */
    String getPassword();

    /**
     * Get the customer's phone number.
     * @return the customer's phone number.
     */
    String getPhoneNumber();

    /**
     * Get the customer's date of birth.
     * @return the customer's date of birth.
     */
    Date getDateOfBirth();

    /**
     * Check if the payment method of the assignment is in cash.
     * @param code the code of the assignment.
     * @return true if the customer pays the dog sitter in cash.
     */
    Boolean isInCashPaymentMethodOfAssignment(int code);

    /**
     * Update the dog's name.
     * @param ID the dog's ID.
     * @param name the new dog's name.
     * @return true if the update is successfully performed.
     */
    boolean updateDogName(int ID, String name);

    /**
     * Update the dog's breed.
     * @param ID the dog's ID.
     * @param breed the new dog's breed.
     * @return true if the update is successfully performed.
     */
    boolean updateDogBreed(int ID, String breed);

    /**
     * Update the dog's date of birth.
     * @param ID the dog's ID.
     * @param dateOfBirth the new dog's date of birth.
     * @return true if the update is successfully performed.
     */
    boolean updateDogAge(int ID, Date dateOfBirth);

    /**
     * Update the dog's weight.
     * @param ID the dog's ID.
     * @param weight the new dog's weight.
     * @return true if the update is successfully performed.
     */
    boolean updateDogWeight(int ID, double weight);

    /**
     * Get dog's date of birth.
     * @param ID the dog's ID.
     * @return the dog's date of birth.
     */
    Date getDogDateOfBirth(int ID);

    /**
     * Get the list of breeds of dogs.
     * @return the list of breeds.
     */
    HashSet<String> getDogsBreedsList();
}
