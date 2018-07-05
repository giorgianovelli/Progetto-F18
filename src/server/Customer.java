/**
 * This class contains some methods of the actions that a customer can do.
 */

package server;

import database.DBConnector;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
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
import static server.tools.dateTime.DateTimeTools.getAge;


public class Customer extends User implements InterfaceCustomer{

    /**
     * List of dogs.
     */
    private HashSet<Dog> dogList;

    /**
     * HashSet of registered DogSitter.
     */
    private HashSet<DogSitter> dogSitterSearchList;



    /**
     * Create a new Customer.
     * @param email customer's email address.
     * @param name customer's name.
     * @param surname customer's surname.
     * @param password customer's password.
     * @param phoneNumber customer's phone number.
     * @param dateOfBirth customer's date of birth.
     * @param address customer's address.
     * @param paymentMethod customer's payment method.
     */
    public Customer(String email, String name, String surname, String password, String phoneNumber, Date dateOfBirth, Address address, PaymentMethod paymentMethod) {
        super(email, name, surname, password, phoneNumber, dateOfBirth, address, paymentMethod);
        dogList = new HashSet<Dog>(3);
        assignmentList = new HashMap<Integer, Assignment>();
        reviewList = new HashMap<Integer, Review>();
        Singleton singleton = new Singleton();
        assignmentList = getAssignmentList();
        reviewList = singleton.getCustomerReviewList(this);
        dogList = getDogListFromDB(email);
        dogSitterSearchList = new HashSet<DogSitter>();
    }


    /**
     *
     * @return the HashMap of the assignments of the customer. The Integer key is the code of the Assignment.
     */
    public HashMap<Integer, Assignment> getAssignmentList(){
        Singleton singleton = new Singleton();
        return singleton.getCustomerListAssignmentFromDB(email);
    }


    /**
     * Confirm an assignment with the following parameters.
     * @param emailDogSitter dog sitter's email.
     * @param dateStartAssignment start of the assignment.
     * @param dateEndAssignment end of the assignment.
     * @param selectedDogs the list of dogs to assign to dog sitter.
     * @param meetingPoint the place where customer and dog sitter will meet.
     * @param paymentInCash true if customer will pay dog sitter in cash
     * @return true if assignment is successfully confirmed.
     */
    //TODO refactor
    public boolean addAssignment(String emailDogSitter, Date dateStartAssignment, Date dateEndAssignment, HashSet<Dog> selectedDogs, Address meetingPoint, boolean paymentInCash) {
        //chiamata alla classe banca per effettuare la transazione
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
        double price = estimatePriceAssignment(selectedDogs, dateStartAssignment, dateEndAssignment);

        if ((bank.isTransactionPossible(email, price)) || (paymentInCash)) {

            //crea un oggetto di tipo Assignment e lo aggiunge all'HashMap assignmentList
            //Assignment assignment = new Assignment(code, selectedDogs, dateStartAssignment, dateEndAssignment, meetingPoint);
            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date.setLenient(false);

            String dateStringStartAssigment = date.format(dateStartAssignment);
            String dateStringEndAssigment = date.format(dateEndAssignment);

            Assignment assignment = new Assignment(code, selectedDogs, dateStartAssignment, dateEndAssignment, meetingPoint);
            assignmentList.put(code, assignment);

            //salva la prenotazione nel database

            try {
                dbConnector.updateDB("INSERT INTO ASSIGNMENT VALUES (" + code + ", '" + email + "', '" + emailDogSitter + "', 'NULL', '" + dateStringStartAssigment + "', '" + dateStringEndAssigment + "')");
                dbConnector.updateDB("INSERT INTO MEETING_POINT VALUES (" + code + ", '" + meetingPoint.getCountry() + "', '" + meetingPoint.getCity() + "', '" + meetingPoint.getStreet() + "', '" + meetingPoint.getCap() + "', '" + meetingPoint.getCap() + "')");
                for (Dog d : selectedDogs) {
                    dbConnector.updateDB("INSERT INTO DOG_ASSIGNMENT VALUES (" + code + ", " + d.getID() + ")");
                }
                dbConnector.closeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (!(paymentInCash)){
                bank.makeBankTransaction(email, emailDogSitter, code, price);
            }

            System.out.println("Assignment completed successfully!");
            System.out.println(assignment.toString());
            return true;
        } else {
            System.out.println("Error during assignment with " + emailDogSitter);
            return false;
        }
    }


    /**
     * Remove the assignment indicated with code.
     * @param code the code of assignment to remove.
     * @return true if the assignment is successfully removed.
     */
    public boolean removeAssignment(int code) {
        Assignment a = null;
        a = assignmentList.get(code);
        if (a != null) {
            assignmentList.remove(code);
            System.out.println("Selected assignment removed!");

            DBConnector dbConnector = new DBConnector();
            Bank bank = new Bank();
            try {
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

    /**
     * Add a review to an Assignment (if it doesn't already exist).
     * @param codeAssignment the code of the assignment to be reviewed.
     * @param emailDogSitter dog sitter's email address.
     * @param rating an int value from 1 to 5.
     * @param title the title of review.
     * @param comment the text of review.
     * @return true if the review is successfully added.
     */
    public boolean addReview(int codeAssignment, String  emailDogSitter, int rating, String title, String comment) {
        SimpleDateFormat dateFormatSql = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateReview = new Date();
        String dateStringReview = dateFormatSql.format(dateReview);
        Review review = new Review(codeAssignment, dateReview, rating, title.toUpperCase(), comment);

        if (reviewList.containsKey(codeAssignment)){
            System.out.println("Error: there is already a review for this assignment!");
            return false;
        }

        if (!(assignmentList.containsKey(codeAssignment))){
            System.out.println("Error: there is not an assignment with code " + codeAssignment + "!");
            return false;
        }

        //salva la recensione nel database
        DBConnector dbConnector = new DBConnector();
        try {
            dbConnector.updateDB("INSERT INTO REVIEW VALUES (" + codeAssignment + ", '" + dateStringReview + "', " + rating + ", '" + title + "', '" + comment + "', null)");
            dbConnector.closeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        reviewList.put(codeAssignment, review);
        System.out.println(review.toString());
        return true;
    }


    /**
     * Remove the review indicated with code.
     * @param code the code of review to be remove.
     * @return true if the review is removed successfully.
     */
    public boolean removeReview(Integer code) {
        Review r = null;
        r = reviewList.get(code);
        if (r != null) {
            reviewList.remove(code);
            System.out.println("Selected review removed!");

            DBConnector dbConnector = new DBConnector();
            try {
                dbConnector.updateDB("DELETE FROM REVIEW WHERE ASSIGNMENT_CODE = " + code);
                dbConnector.closeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return true;
        } else {
            System.out.println("Error: there is not a review for assignment with code " + code +"!");
            return false;
        }
    }


    /**
     * Add a new dog.
     * @param customerEmail owner's email address.
     * @param name dog's name.
     * @param breed dog's breed.
     * @param dateOfBirth dog's date of birth.
     * @param weight dog's weight.
     * @return true if the new dog is successfully added.
     */
    public boolean addDog(String customerEmail, String name, String breed, Date dateOfBirth, double weight) {
        DBConnector dbConnector = new DBConnector();
        DogSize size;
        int ID;
        try {
            ResultSet rs = dbConnector.askDB("SELECT SIZE FROM BREEDS WHERE NAME = '" + breed + "'");
            rs.next();
            String strSize = rs.getString("SIZE");
            size = DogSize.valueOf(strSize);
            dbConnector.closeConnection();
            rs = dbConnector.askDB("SELECT ID FROM DOGS");
            rs.last();
            ID = rs.getRow() + 1;
            dbConnector.closeConnection();
            Dog dog = new Dog(name, breed, size, getAge(dateOfBirth), weight, ID, true);
            dogList.add(dog);
            SimpleDateFormat dateSqlFormat = new SimpleDateFormat("yyyy-MM-dd");
            String strDateOfBirth = dateSqlFormat.format(dateOfBirth);
            dbConnector.updateDB("INSERT INTO DOGS VALUES (" + ID + ", '" + name + "', '" + breed + "', " + weight + ", '" + strDateOfBirth + "', '" + customerEmail + "', true)");
            dbConnector.closeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Disable the dog indicated by ID.
     * @param ID the ID of the dog to be removed.
     * @return true if selected dog is successfully removed.
     */
    public boolean disableDog(int ID){
        Singleton singleton = new Singleton();
        Dog dog = singleton.createDogFromDB(ID);
        boolean isDisabled = false;

        for (Dog d : dogList) {
            if (d.getID() == dog.getID()){
                isDisabled = true;
                DBConnector dbConnector = new DBConnector();
                try {
                    dbConnector.updateDB("UPDATE DOGS SET IS_ENABLED = FALSE WHERE ID = " + ID + ";");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return isDisabled;
    }


    /**
     * Get the list of reviews written by the customer.
     * @return the HashMap of reviews. The Integer key is the code of the assignment
     * related to the review.
     */
    public HashMap<Integer, Review> getReviewList() {
        return reviewList;
    }


    /**
     * Get the list of dogs.
     * @return the HashSet of dogs.
     */
    public HashSet<Dog> getDogList() {
        return dogList;
    }


    /**
     * Get the list of dogs from database.
     * @param email the email address of customer.
     * @return the HashSet of dogs.
     */
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


    /**
     * Perform a search for a dog sitter.
     * @param dateStart start of the assignment.
     * @param dateEnd end of the assignment.
     * @param meetingPoint the place where customer and dog sitter will meet.
     * @param dogList the list of dogs to assign to dog sitter.
     * @param cash true if customer will pay dog sitter in cash.
     * @return an HashSet of dog sitters available for the assignment.
     */
    public HashSet<String> search(Date dateStart, Date dateEnd, Address meetingPoint, HashSet<Dog> dogList, boolean cash){
        //TODO Al termine, rimuovere le System.out utili per il debug

        loadDogSitterList();

        SimpleDateFormat dateNumDayOfWeek = new SimpleDateFormat("u");
        int nStartDay = Integer.parseInt(dateNumDayOfWeek.format(dateStart));
        int nEndDay = Integer.parseInt(dateNumDayOfWeek.format(dateEnd));

        searchMeetingPoint(meetingPoint);
        searchDayOfWork(dateStart, dateEnd, nStartDay, nEndDay);
        searchTimeOfWork(dateStart, dateEnd, nStartDay, nEndDay);
        searchDogsNumber(dogList);
        searchDogSize(dogList);
        searchAvailable(dateStart, dateEnd);
        searchPaymentMethod(cash);

        System.out.println("Dog sitters available:");
        if (dogSitterSearchList.size() != 0){
            for (DogSitter ds : dogSitterSearchList) {
                System.out.println(ds.getEmail());
            }
        } else {
            System.out.println("There aren't dog sitters available!");
        }


        HashSet<String> dogSitterMailList = new HashSet<String>();
        for (DogSitter ds : dogSitterSearchList) {
            dogSitterMailList.add(ds.getEmail());
        }

        return dogSitterMailList;
    }


    /**
     * Filter dog sitters that work in the city selected by customer.
     * @param meetingPoint the place where customer and dog sitter will meet.
     */
    private void searchMeetingPoint(Address meetingPoint){
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


    /**
     * Filter dog sitters that work in the days of the week selected by the customer.
     * @param dateStart start of the assignment.
     * @param dateEnd end of the assignment.
     * @param nStartDay number of the day of the week in which the assignment starts.
     * @param nEndDay number of the day of the week in which the assignment ends.
     */
    private void searchDayOfWork(Date dateStart, Date dateEnd, int nStartDay, int nEndDay){
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


    /**
     * Filter dog sitters that work in times selected by the customer.
     * @param dateStart start of the assignment.
     * @param dateEnd end of the assignment.
     * @param nStartDay number of the day of the week in which the assignment starts.
     * @param nEndDay number of the day of the week in which the assignment ends.
     */
    private void searchTimeOfWork(Date dateStart, Date dateEnd, int nStartDay, int nEndDay){
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


    /**
     * Filter dog sitters that accept at least the number of dogs requested by the customer.
     * @param dogList the list of dogs to assign to the dog sitter.
     */
    private void searchDogsNumber(HashSet<Dog> dogList){
        //funzione che escude i dogsitter che non danno disponibilità
        //per il numero di cani richiesto dall'utente
        HashSet<DogSitter> toRemove = new HashSet<DogSitter>();
        for (DogSitter ds : dogSitterSearchList) {
            if (ds.getDogsNumber() < dogList.size()){
                toRemove.add(ds);
            }
        }
        for (DogSitter ds : toRemove) {
            dogSitterSearchList.remove(ds);
        }
    }


    /**
     * Filter dog sitters that accept the sizes of dogs requested by the customer.
     * @param dogList the list of dogs to assign to the dog sitter.
     */
    private void searchDogSize(HashSet<Dog> dogList){
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


    /**
     * Filter dog sitters that are available in the period selected by the customer.
     * @param dateStart start of the assignment.
     * @param dateEnd end of the assignment.
     */
    private void searchAvailable(Date dateStart, Date dateEnd){
        //funzione che esclude i dog sitter che hanno già altri impegni nel periodo impostato dal cliente
        HashSet<DogSitter> toRemove = new HashSet<DogSitter>();
        for (DogSitter ds : dogSitterSearchList) {
            HashMap<Integer, Assignment> listAssignment = ds.getAssignmentList();
            for (Integer key : listAssignment.keySet()) {
                Assignment a = listAssignment.get(key);
                if (((dateStart.after(a.getDateStart()) || dateStart.equals(a.getDateStart())) && (dateEnd.before(a.getDateEnd()) || dateEnd.equals(a.getDateEnd())))  ||  ((dateStart.before(a.getDateStart()) || dateStart.equals(a.getDateStart())) && (dateEnd.after(a.getDateEnd()) || dateEnd.equals(a.getDateEnd())))){
                    toRemove.add(ds);
                }

                if (dateStart.before(a.getDateStart()) && (dateEnd.after(a.getDateStart()) && dateEnd.before(a.getDateEnd()))){
                    toRemove.add(ds);
                }

                if (dateEnd.after(a.getDateEnd()) && (dateStart.after(a.getDateStart()) && dateStart.before(a.getDateEnd()))){
                    toRemove.add(ds);
                }
            }
        }

        for (DogSitter ds : toRemove) {
            dogSitterSearchList.remove(ds);
        }
    }


    /**
     * Filter dog sitter based on the payment method.
     * @param cash if customer want to pay in cash.
     */
    private void searchPaymentMethod(boolean cash){
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


    /**
     * Estimate the price of the assignment with the following parameters.
     * @param dogList the list of dogs to assign to the dog sitter.
     * @param dateStart start of assignment.
     * @param dateEnd end of assignment.
     * @return the price of the assignment.
     */
    //TODO refactor
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


    /**
     * Update the customer's name.
     * @param name the new customer's name.
     * @return true if the update is successfully performed.
     */
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


    /**
     * Update the customer's surname.
     * @param surname the new customer's surname.
     * @return true if the update is successfully performed.
     */
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


    /**
     * Update the customer's password.
     * @param password the new customer's password.
     * @return true if the update is successfully performed.
     */
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


    /**
     * Update the customer's phone number.
     * @param phoneNumber the new customer's phone number.
     * @return true if the update is successfully performed.
     */
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


    /**
     * Update the customer's date of birth.
     * @param dateOfBirth the new customer's date of birth.
     * @return true if the update is successfully performed.
     */
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


    /**
     * Update the customer's address with the following data inserted by the customer.
     * @param country the country in which the customer lives.
     * @param city the city where the customer lives.
     * @param street the street in which customer lives.
     * @param number civic number.
     * @param cap the cap of the city where the customer lives.
     * @return true if the update is successfully performed.
     */
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


    /**
     * Update the customer's payment method with the following data inserted by the customer.
     * @param number card's number.
     * @param name owner's name.
     * @param surname owner's surname.
     * @param expirationDate the card's expiration date.
     * @param cvv the secure code of the card.
     * @return true if the update is successfully performed.
     */
    public boolean updatePaymentMethod(String number, String name, String surname, Date expirationDate, String cvv){
        double amount = this.paymentMethod.getAmount();
        String oldNum = this.paymentMethod.getNumber();
        this.paymentMethod = new PaymentMethod(number, name, surname, expirationDate, cvv, amount);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strExpiration = dateFormat.format(expirationDate);
        DBConnector dbConnector = new DBConnector();
        try {
            boolean isUpdated = dbConnector.updateDB("INSERT INTO CREDIT_CARDS VALUES ('" + number + "', '" + name + "', '" + surname + "', '" + strExpiration + "', '" + cvv + "', " + amount + ")");
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


    /**
     * Load the list of the dog sitters registered to Canibau.
     */
    private void loadDogSitterList(){
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


    /**
     * Check if the payment method of the assignment is in cash.
     * @param code the code of the assignment.
     * @return true if the customer pays the dog sitter in cash.
     */
    public Boolean isInCashPaymentMethodOfAssignment(int code){
        DBConnector dbConnector = new DBConnector();
        Singleton singleton = new Singleton();
        try {
            ResultSet rs = dbConnector.askDB("SELECT * FROM TRANSACTIONS WHERE CODE_ASSIGNMENT = " + code);
            rs.last();
            int cardFlag = rs.getRow();
            dbConnector.closeConnection();
            if (cardFlag == 1){
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateDogName(int ID, String name){
        DBConnector dbConnector = new DBConnector();
        try {
            boolean isUpdated = dbConnector.updateDB("UPDATE DOGS SET NAME = '" + name + "' WHERE ID = '" + ID + "';");
            dbConnector.closeUpdate();

            if (isUpdated) {
                System.out.println("The dog's name with ID = " + ID + " now is up to date!");
                return true;
            } else {
                System.out.println("Error in updating the dog's name with ID = " + ID + "!");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateDogAge(int ID, Date dateOfBirth){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strBirth = dateFormat.format(dateOfBirth);
        DBConnector dbConnector = new DBConnector();
        try {
            boolean isUpdated = dbConnector.updateDB("UPDATE DOGS SET AGE = '" + strBirth + "' WHERE ID = '" + ID + "';");
            dbConnector.closeUpdate();

            if (isUpdated) {
                System.out.println("The date of birth of the dog with ID = " + ID + " now is up to date!");
                return true;
            } else {
                System.out.println("Error in updating the date of birth of the dog with ID = " + ID + "!");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
