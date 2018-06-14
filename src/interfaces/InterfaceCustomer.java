package interfaces;

import server.*;
import server.bank.PaymentMethod;
import server.places.Address;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public interface InterfaceCustomer {

    boolean addAssignment(String emailDogSitter, Date dateStartAssignment, Date dateEndAssignment, HashSet<Dog> selectedDogs, Address meetingPoint);

    boolean removeAssignment(int code);

    boolean addReview(int codeAssignment, String  emailDogSitter, int rating, String title, String comment);

    boolean removeReview(Integer code);

    boolean addDog(String customerEmail, String name, String breed, int age, double weight);

    boolean disableDog(int ID);

    HashMap<Integer, Assignment> getAssignmentList();

    HashMap<Integer, Review> getReviewList();

    Address getAddress();

    HashSet<Dog> getDogList();

    HashSet<String> search(Date dateStart, Date dateEnd, Address meetingPoint, HashSet<Dog> dogList, boolean cash);

    double estimatePriceAssignment(HashSet<Dog> dogList, Date dateStart, Date dateEnd);

    PaymentMethod getPaymentMethod();

    boolean updateName(String name);

    boolean updateSurname(String surname);

    boolean updatePassword(String password);

    boolean updatePhoneNumber(String phoneNumber);

    boolean updateDateOfBirth(Date dateOfBirth);

    boolean updateAddress(String country, String city, String street, String number, String cap);

    boolean updatePaymentMethod(String number, String name, String surname, Date expirationDate, int cvv);

    String getName();

    String getSurname();

    String getPassword();

    String getPhoneNumber();

    Date getDateOfBirth();
}
