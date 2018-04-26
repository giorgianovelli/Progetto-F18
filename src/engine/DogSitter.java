package engine;

import engine.Address;

import java.util.Date;

public class DogSitter extends User {
    public DogSitter(String email, String name, String surname, String password, String phoneNumber, Date dateOfBirth, Address address, PaymentMethod paymentMethod) {
        super(email, name, surname, password, phoneNumber, dateOfBirth, address, paymentMethod);
    }
}
