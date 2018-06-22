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

public interface InterfaceDogSitter {
    HashMap<Integer, Assignment> getAssignmentList();

    HashMap<Integer, Review> getReviewList();

    Availability getDateTimeAvailability();

    boolean isAcceptingCash();

    int getDogsNumber();

    HashSet<DogSize> getListDogSize();

    Area getArea();

    Address getAddress();

    String getName();

    String getSurname();

    boolean updateName(String name);

    boolean updateSurname(String surname);

    boolean updatePassword(String password);

    boolean updatePhoneNumber(String phoneNumber);

    boolean updateDateOfBirth(Date dateOfBirth);

    boolean updateAddress(String country, String city, String street, String number, String cap);

    boolean updatePaymentMethod(String number, String name, String surname, Date expirationDate, int cvv);

    boolean updateCashFlag(boolean acceptCash);

    boolean updateAssignmentState(int code, Boolean state);

    boolean addNewPlaceArea(String city);

    boolean removePlaceArea(String city);

    boolean updateDogsNumber(int nDogs);

    boolean updateListDogSize(boolean small, boolean medium, boolean big, boolean giant);

    //boolean updateDateTimeAvailability()
}
