package server;

import database.DBConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import interfaces.InterfaceCustomer;
import server.bank.Bank;
import server.bank.PaymentMethod;
import server.dateTime.WorkingTime;
import server.places.Address;
import server.places.Area;
import server.tools.dateTime.DateTimeDHMS;

import static server.tools.DoubleTools.round2Decimal;
import static server.tools.dateTime.DateTimeTools.dateTimeDiff;

//import static staticClasses.ObjectCreator.createDogFromDB;
//import static staticClasses.ObjectCreator.getCustomerListAssignmentFromDB;

public class Customer extends User{
    private HashSet<Dog> dogList;
    private HashSet<DogSitter> dogSitterSearchList;

    public Customer(String email, String name, String surname, String password, String phoneNumber, Date dateOfBirth, Address address, PaymentMethod paymentMethod) {
        super(email, name, surname, password, phoneNumber, dateOfBirth, address, paymentMethod);
        dogList = new HashSet<Dog>(3);
        assignmentList = new HashMap<Integer, Assignment>();
        reviewList = new HashMap<Integer, Review>();
        Singleton singleton = new Singleton();
        assignmentList = singleton.getCustomerListAssignmentFromDB(email);
        reviewList = singleton.getCustomerReviewList(this);
        dogList = getDogListFromDB(email);
        dogSitterSearchList = new HashSet<DogSitter>();
    }

    public Assignment addAssignment(DogSitter ds, Date dateStartAssignment, Date dateEndAssignment, HashSet<Dog> selectedDogs, Address meetingPoint) {
        String emailDogSitter = ds.email;

        //chiamata alla classe banca per effettuare la transazione
        boolean testTransaction = true;
        DBConnector dbConnector = new DBConnector();
        int code = -1;
        try {
            ResultSet rs = dbConnector.askDB("SELECT * FROM ASSIGNMENT");
            rs.last();
            code = rs.getRow() + 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Bank bank = new Bank();

        //implementare funzione per il calcolo del prezzo della prestazione
        //double amount = 10;
        double price = estimatePriceAssignment(selectedDogs, dateStartAssignment, dateEndAssignment);

        if (bank.isTransactionPossible(email, price)) {

            //crea un oggetto di tipo Assignment e lo aggiunge all'HashMap assignmentList
            //Assignment assignment = new Assignment(code, selectedDogs, dateStartAssignment, dateEndAssignment, meetingPoint);
            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date.setLenient(false);
            Date startAssignment = new Date();
            Date endAssignment = new Date();

            String dateStringStartAssigment = date.format(dateStartAssignment);
            String dateStringEndAssigment = date.format(dateEndAssignment);
            try {
                startAssignment = date.parse(dateStringStartAssigment);
                endAssignment = date.parse(dateStringEndAssigment);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Assignment assignment = new Assignment(code, selectedDogs, dateStartAssignment, dateEndAssignment, meetingPoint);
            assignmentList.put(code, assignment);

            //salva la prenotazione nel database

            Timestamp sqlStart = new Timestamp(startAssignment.getTime());
            Timestamp sqlEnd = new Timestamp(endAssignment.getTime());

            try {
                dbConnector.updateDB("INSERT INTO ASSIGNMENT VALUES (" + code + ", '" + email + "', '" + ds.getEmail() + "', TRUE, '" + dateStringStartAssigment + "', '" + dateStringEndAssigment + "')");
                dbConnector.updateDB("INSERT INTO MEETING_POINT VALUES (" + code + ", '" + meetingPoint.getCountry() + "', '" + meetingPoint.getCity() + "', '" + meetingPoint.getStreet() + "', '" + meetingPoint.getCap() + "', '" + meetingPoint.getCap() + "')");
                for (Dog d : dogList) {
                    dbConnector.updateDB("INSERT INTO DOG_ASSIGNMENT VALUES (" + code + ", " + d.getID() + ")");
                }
                //bank.makeBankTransaction(email, emailDogSitter, code, amount);
                dbConnector.closeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            bank.makeBankTransaction(email, emailDogSitter, code, price);

            System.out.println("Assignment completed successfully!");
            System.out.println(assignment.toString());
            return assignment;
        } else {
            System.out.println("Error during assignment with " + ds.email);
            return null;
        }
    }

    public boolean removeAssignment(Integer key) {
        Assignment a = null;
        a = assignmentList.get(key);
        if (a != null) {
            assignmentList.remove(key);
            System.out.println("Selected assignment removed!");

            //aggiungere codice per rimuovere la prenotazione dal database
            DBConnector dbConnector = new DBConnector();
            Bank bank = new Bank();
            try {
                //dbConnector.updateDB("DELETE FROM TRANSACTIONS WHERE CODE_ASSIGNMENT = " + a.getCode());
                bank.refundCustomer(a.getCode());
                dbConnector.updateDB("DELETE FROM MEETING_POINT WHERE CODE = " + a.getCode());
                dbConnector.updateDB("DELETE FROM DOG_ASSIGNMENT WHERE CODE = " + a.getCode());
                dbConnector.updateDB("DELETE FROM ASSIGNMENT WHERE CODE = " + a.getCode());
                dbConnector.closeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return true;
        } else {
            System.out.println("Error in removing the selected assignment!");
            return false;
        }
    }

    public Review addReview(int codeAssignment, DogSitter ds, Date dateReview, int rating, String title, String comment) {
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        date.setLenient(false);
        String dateStringReview = date.format(dateReview);
        Review review = new Review(codeAssignment, dateReview, rating, title.toUpperCase(), comment);

        //salva la recensione nel database
        //sottometodo da implementare

        reviewList.put(codeAssignment, review);
        System.out.println(review.toString());
        return review;
    }

    public boolean removeReview(Integer key) {
        Review r = null;
        r = reviewList.get(key);
        if (r != null) {
            reviewList.remove(key);
            System.out.println("Selected review removed!");

            //aggiungere codice per rimuovere la recensione dal database

            return true;
        } else {
            System.out.println("Error in removing the selected review!");
            return false;
        }
    }

    public HashMap<Integer, Assignment> listAssignment() {
        Assignment a = null;
        for (Integer key : assignmentList.keySet()) {
            a = assignmentList.get(key);
            System.out.println(a.toString());
        }
        if (a == null) {
            System.out.println("There are no assignment available!");
        }
        return assignmentList;
    }

    public HashMap<Integer, Review> listReview() {
        Review r = null;
        for (Integer key : reviewList.keySet()) {
            r = reviewList.get(key);
            System.out.println(r.toString());
        }
        if (r == null) {
            System.out.println("There are no reviews available!");
        }
        return reviewList;
    }

    public HashSet<Dog> addDog(String name, String breed, DogSize size, int age, double weight, int ID) {
        Dog dog = new Dog(name, breed, size, age, weight, ID);
        dogList.add(dog);
        return dogList;
    }

    public HashSet<Dog> removeDog(Dog dog) {
        if (dogList.contains(dog)) {
            dogList.remove(dog);
            return dogList;
        } else {
            return null;
        }
    }

    public HashMap<Integer, Assignment> getAssignmentList() {
        return assignmentList;
    }

    public HashMap<Integer, Review> getReviewList() {
        return reviewList;
    }

    public Address getAddress() {
        return address;
    }

    public HashSet<Dog> getDogList() {
        return dogList;
    }

    private HashSet<Dog> getDogListFromDB(String email) {
        DBConnector dbConnector = new DBConnector();
        try {
            ResultSet rs = dbConnector.askDB("SELECT ID FROM DOGS WHERE OWNER_EMAIL = '" + email + "'");
            while (rs.next()) {
                int dogID = rs.getInt("ID");
                Singleton singleton = new Singleton();
                Dog dog = singleton.createDogFromDB(dogID);
                dogList.add(dog);
            }
            dbConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dogList;
    }

    public HashSet<String> search(Date dateStart, Date dateEnd, Address meetingPoint, HashSet<Dog> dogList, boolean cash){
        //funzione da completare
        //al termine, rimuovere le System.out utili per il debug

        loadDogSitterList();

        SimpleDateFormat dateNumDayOfWeek = new SimpleDateFormat("u");
        int nStartDay = Integer.parseInt(dateNumDayOfWeek.format(dateStart));
        int nEndDay = Integer.parseInt(dateNumDayOfWeek.format(dateEnd));

        searchStep0(meetingPoint);
        searchStep1(dateStart, dateEnd, nStartDay, nEndDay);
        searchStep2(dateStart, dateEnd, nStartDay, nEndDay);
        searchStep3(dogList);
        searchStep4(dogList);
        searchStep5(dateStart, dateEnd, dogList);
        searchStep6(cash);

        System.out.println("Dog sitters available:");
        for (DogSitter ds : dogSitterSearchList) {
            System.out.println(ds.getEmail());
        }

        HashSet<String> dogSitterMailList = new HashSet<String>();
        for (DogSitter ds : dogSitterSearchList) {
            dogSitterMailList.add(ds.getEmail());
        }

        return dogSitterMailList;
    }

    private void searchStep0(Address meetingPoint){
        //funzione che filtra i dog sitter in base al meeting point
        HashSet<DogSitter> toRemove = new HashSet<DogSitter>();
        for (DogSitter ds : dogSitterSearchList) {
            Area dogSitterArea = ds.getArea();
            if (!(dogSitterArea.contains(meetingPoint.getCity()))){
                toRemove.add(ds);
            }
        }
        for (DogSitter ds : toRemove) {
            dogSitterSearchList.remove(ds);
        }
    }

    private void searchStep1(Date dateStart, Date dateEnd, int nStartDay, int nEndDay){
        //rimuove i dogsitter che non lavorano nei giorni richiesti dal cliente
        int i;
        HashSet<DogSitter> toRemove = new HashSet<DogSitter>();
        for (DogSitter ds : dogSitterSearchList) {
            WorkingTime availability[] = ds.getDateTimeAvailability().getArrayDays();
            for (i = nStartDay - 1; i < nEndDay; i++){
                if ((availability[i].getStart() == null) && (availability[i].getEnd() == null)){
                    toRemove.add(ds);
                }
            }
        }
        for (DogSitter ds : toRemove) {
            dogSitterSearchList.remove(ds);
        }
    }

    private void searchStep2(Date dateStart, Date dateEnd, int nStartDay, int nEndDay){
        //esclude i dog sitter che non lavorano negli orari di lavoro impostati dal cliente
        int i;
        HashSet<DogSitter> toRemove = new HashSet<DogSitter>();
        Time timeStart = new Time(dateStart.getTime());
        Time timeEnd = new Time(dateEnd.getTime());
        for (DogSitter ds : dogSitterSearchList) {
            WorkingTime availability[] = ds.getDateTimeAvailability().getArrayDays();
            for (i = nStartDay - 1; i < nEndDay; i++){
                Time ts = availability[i].getStart();
                Time te = availability[i].getEnd();
                if ((timeStart.after(ts) || timeStart.equals(ts)) && (timeEnd.before(te) || timeEnd.equals(te))){
                    toRemove.add(ds);
                }
            }
        }

        for (DogSitter ds : toRemove) {
            dogSitterSearchList.remove(ds);
        }
    }

    private void searchStep3(HashSet<Dog> dogList){
        //funzione che escude i dogsitter che non danno disponibilità
        //per il numero di cani richiesto dall'utente
        HashSet<DogSitter> toRemove = new HashSet<DogSitter>();
        for (DogSitter ds : dogSitterSearchList) {
            if (ds.getDogNumber() < dogList.size()){
                toRemove.add(ds);
            }
        }
        for (DogSitter ds : toRemove) {
            dogSitterSearchList.remove(ds);
        }
    }

    private void searchStep4(HashSet<Dog> dogList){
        //funzione che esclude i dogsitter che non danno dispobilità
        //per le taglie indicate dal cliente
        HashSet<DogSitter> toRemove = new HashSet<DogSitter>();
        for (DogSitter ds : dogSitterSearchList) {
            HashSet<DogSize> listDogSize = ds.getListDogSize();
            for (Dog dog : dogList) {
                if (!(listDogSize.contains(dog.getSize()))){
                    toRemove.add(ds);
                }
            }
        }
        for (DogSitter ds : toRemove) {
            dogSitterSearchList.remove(ds);
        }
    }

    private void searchStep5(Date dateStart, Date dateEnd, HashSet<Dog> dogList){
        //funzione che esclude i dog sitter che non lavorano negli orari di lavoro impostati dal cliente
        HashSet<DogSitter> toRemove = new HashSet<DogSitter>();
        for (DogSitter ds : dogSitterSearchList) {
            HashMap<Integer, Assignment> listAssignment = ds.getListAssignment();
            for (Integer key : listAssignment.keySet()) {
                Assignment a = listAssignment.get(key);
                if (((dateStart.after(a.getDateStart()) || dateStart.equals(a.getDateStart())) && (dateStart.before(a.getDateStart()) || dateStart.equals(a.getDateStart()))) || ((dateEnd.after(a.getDateEnd()) || dateEnd.equals(a.getDateEnd())) && (dateEnd.before(a.getDateEnd()) || dateEnd.equals(a.getDateEnd())))){
                    toRemove.add(ds);
                }
            }
        }

        for (DogSitter ds : toRemove) {
            dogSitterSearchList.remove(ds);
        }
    }

    private void searchStep6(boolean cash){
        //nel caso in cui il cliente vuole pagare in contanti,
        //esclude i dog sitter che accettano il pagamento solo con carta di credito
        HashSet<DogSitter> toRemove = new HashSet<DogSitter>();
        if (cash){
            for (DogSitter ds : dogSitterSearchList) {
                if(ds.isAcceptingCash() == false){
                    toRemove.add(ds);
                }
            }
            for (DogSitter ds : toRemove) {
                dogSitterSearchList.remove(ds);
            }
        }
    }

    public double estimatePriceAssignment(HashSet<Dog> dogList, Date dateStart, Date dateEnd){
        DBConnector dbConnector = new DBConnector();
        HashMap<DogSize, Double> priceMap = new HashMap<DogSize, Double>(4);
        HashMap<Integer, Double> hRange = new HashMap<Integer, Double>(4);

        try {
            ResultSet rs = dbConnector.askDB("SELECT H_RANGE, PERC FROM PERC_PRICE");
            while (rs.next()){
                int n = rs.getInt("H_RANGE");
                hRange.put(n, rs.getDouble("PERC"));
            }
            dbConnector.closeConnection();
            rs = dbConnector.askDB("SELECT SIZE, PRICE FROM PRICE_LIST");
            while (rs.next()){
                DogSize size = DogSize.valueOf(rs.getString("SIZE"));
                priceMap.put(size, rs.getDouble("PRICE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        double price = 0;
        DateTimeDHMS workTime = dateTimeDiff(dateEnd, dateStart);

        System.out.println("Time: " + workTime.getDays() + ":" + workTime.getHours() + ":" + workTime.getMinutes());

        for (Dog d : dogList) {

            price = price + (priceMap.get(d.getSize()) * hRange.get(24) * workTime.getDays());
            //System.out.println("pd: " + price);

            int partialHours = workTime.getHours();

            if (partialHours > 12){
                price = price + (priceMap.get(d.getSize()) * hRange.get(24));
                //System.out.println("phd: " + price);
                partialHours = 0;
            }

            if (partialHours > 6 && partialHours <= 12) {
                price = price + (priceMap.get(d.getSize()) * hRange.get(12) * (partialHours - 6));
                //System.out.println("ph6: " + price);
                partialHours = 6;
            }

            if (partialHours > 3 && partialHours <= 6) {
                price = price + (priceMap.get(d.getSize()) * hRange.get(6) * (partialHours - 3));
                //System.out.println("ph3: " + price);
                partialHours = 3;
            }

            price = price + (priceMap.get(d.getSize()) * hRange.get(3) * partialHours);
            //System.out.println("ph1: " + price);

            double minutes = (int)workTime.getMinutes();
            double minutesHourRatio = minutes / 60;
            //System.out.println("ratio: " + minutesHourRatio);
            price = price + (priceMap.get(d.getSize()) * hRange.get(3) * minutesHourRatio);
        }
        return round2Decimal(price);
    }

    public boolean updateName(String name){
        this.name = name;
        DBConnector dbConnector = new DBConnector();
        try {
            boolean isUpdated = dbConnector.updateDB("UPDATE CUSTOMERS SET NAME = '" + name + "' WHERE EMAIL = '" + this.email + "';");
            dbConnector.closeUpdate();

            if (isUpdated) {
                System.out.println("Name for " + this.email + " now is up to date!");
                return true;
            } else {
                System.out.println("Error in updating name for " + this.email + "!");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateSurname(String surname){
        this.surname = surname;
        DBConnector dbConnector = new DBConnector();
        try {
            boolean isUpdated = dbConnector.updateDB("UPDATE CUSTOMERS SET SURNAME = '" + surname + "' WHERE EMAIL = '" + this.email + "';");
            dbConnector.closeUpdate();

            if (isUpdated) {
                System.out.println("Surname for " + this.email + " now is up to date!");
                return true;
            } else {
                System.out.println("Error in updating surname for " + this.email + "!");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePassword(String password){
        this.password = password;
        DBConnector dbConnector = new DBConnector();
        try {
            boolean isUpdated = dbConnector.updateDB("UPDATE CUSTOMERS SET PASSWORD = '" + password.toUpperCase() + "' WHERE EMAIL = '" + this.email + "';");
            dbConnector.closeUpdate();

            if (isUpdated) {
                System.out.println("Password for " + this.email + " now is up to date!");
                return true;
            } else {
                System.out.println("Error in updating password for " + this.email + "!");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
        DBConnector dbConnector = new DBConnector();
        try {
            boolean isUpdated = dbConnector.updateDB("UPDATE CUSTOMERS SET PHONE_NUMB = '" + phoneNumber + "' WHERE EMAIL = '" + this.email + "';");
            dbConnector.closeUpdate();

            if (isUpdated) {
                System.out.println("Phone number for " + this.email + " now is up to date!");
                return true;
            } else {
                System.out.println("Error in updating phone number for " + this.email + "!");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateDateOfBirth(Date dateOfBirth){
        this.dateOfBirth = dateOfBirth;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(dateOfBirth);
        DBConnector dbConnector = new DBConnector();
        try {
            boolean isUpdated = dbConnector.updateDB("UPDATE CUSTOMERS SET BIRTHDATE = '" + strDate + "' WHERE EMAIL = '" + this.email + "';");
            dbConnector.closeUpdate();

            if (isUpdated) {
                System.out.println("Date of birth for " + this.email + " now is up to date!");
                return true;
            } else {
                System.out.println("Error in updating date of birth for " + this.email + "!");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAddress(String country, String city, String street, String number, String cap){
        this.address = new Address(country, city, street, number, cap);
        DBConnector dbConnector = new DBConnector();
        try {
            boolean isUpdated = dbConnector.updateDB("UPDATE ADDRESS SET COUNTRY = '" + country + "', CITY = '" + city + "', STREET = '" + street + "', CNUMBER = '" + number + "', CAP = '" + cap + "' WHERE EMAIL = '" + this.email + "';");
            dbConnector.closeUpdate();

            if (isUpdated) {
                System.out.println("Address for " + this.email + " now is up to date!");
                return true;
            } else {
                System.out.println("Error in updating address of birth for " + this.email + "!");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePaymentMethod(String number, String name, String surname, Date expirationDate, int cvv){
        double amount = this.paymentMethod.getAmount();
        String oldNum = this.paymentMethod.getNumber();
        this.paymentMethod = new PaymentMethod(number, name, surname, expirationDate, cvv, amount);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strExpiration = dateFormat.format(expirationDate);
        DBConnector dbConnector = new DBConnector();
        try {
            boolean isUpdated = dbConnector.updateDB("INSERT INTO CREDIT_CARDS VALUES ('" + number + "', '" + name + "', '" + surname + "', '" + strExpiration + "', " + cvv + ", " + amount + ")");
            dbConnector.closeUpdate();

            if (!(isUpdated)) {
                System.out.println("Error in updating address of birth for " + this.email + "!");
                return false;
            }

            isUpdated = dbConnector.updateDB("UPDATE CUSTOMERS SET PAYMENT = '" + number + "' WHERE EMAIL = '" + this.email + "';");
            dbConnector.closeUpdate();

            if (!(isUpdated)) {
                System.out.println("Error in updating address of birth for " + this.email + "!");
                return false;
            }

            isUpdated = dbConnector.updateDB("DELETE FROM CREDIT_CARDS WHERE NUM = '" + oldNum + "';");
            dbConnector.closeUpdate();

            if (isUpdated) {
                System.out.println("Payment method for " + this.email + " now is up to date!");
                return true;
            } else {
                System.out.println("Error in updating payment method of birth for " + this.email + "!");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void loadDogSitterList(){
        //TODO risolvere questa eccezione: "Illegal operation on empty result set"
        DBConnector dbConnector = new DBConnector();
        Singleton singleton = new Singleton();
        try {
            ResultSet rs = dbConnector.askDB("SELECT EMAIL FROM DOGSITTERS");
            while (rs.next()){
                DogSitter ds = singleton.createDogSitterFromDB(rs.getString("EMAIL"));
                dogSitterSearchList.add(ds);
            }
            dbConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
