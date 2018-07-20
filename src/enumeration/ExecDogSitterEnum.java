package enumeration;

import server.*;
import server.bank.PaymentMethod;
import server.dateTime.WeekDays;
import server.dateTime.WorkingTime;
import server.places.Address;
import server.places.Area;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;
import static enumeration.enumStaticMethods.*;


/**
 * This enumeration documents all actions that a dog sitter can do.
 */
public enum ExecDogSitterEnum {
    ACCESSVERIFIER{

        /**
         * Verify dog sitter's access data.
         * @param clientMsg the message received from the client.
         * @return the message for the client.
         */
        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String inputUser = tokenMsg.nextToken();
            String inputPassword = tokenMsg.nextToken();

            Login loginDogSitter = new Login();
            try {
                if(loginDogSitter.dogSitterAccessDataVerifier(inputUser, inputPassword)){
                    return "true";
                } else{
                    return "false";
                }

            } catch (SQLException e) {
                e.printStackTrace();
                return "false";
            }
        }

    },

    GETLISTASSIGNMENT{

        /**
         * Get the dog sitter's list of assignments.
         * @param clientMsg the message received from the client.
         * @return the message for the client.
         */
        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            HashMap<Integer, Assignment> dogSitterListAssignment = singleton.getDogSitterListAssignmentFromDB(email);
            return getListAssignment(dogSitterListAssignment);
        }

    },

    GETCUSTOMERNAMEOFASSIGNMENT{

        /**
         * Get the customer's name of a specified assignment.
         * @param clientMsg the message received from the client.
         * @return the message for the client.
         */
        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            int code = Integer.parseInt(tokenMsg.nextToken());
            Customer customer = getCustomerOfAssignment(code);
            return customer.getName();
        }

    },

    GETCUSTOMERSURNAMEOFASSIGNMENT{

        /**
         * Get the customer's surname of a specified assignment.
         * @param clientMsg the message received from the client.
         * @return the message for the client.
         */
        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            int code = Integer.parseInt(tokenMsg.nextToken());
            Customer customer = getCustomerOfAssignment(code);
            return customer.getSurname();
        }

    },

    GETREVIEW{

        /**
         * Get a specified review.
         * @param clientMsg the message received from the client.
         * @return the message for the client.
         */
        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            int code = Integer.parseInt(tokenMsg.nextToken());

            return getReview(code);
        }

    },

    GETNAME{

        /**
         * Get the dog sitter's name.
         * @param clientMsg the message received from the client.
         * @return the message for the client.
         */
        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            DogSitter dogSitter = singleton.createDogSitterFromDB(email);
            return dogSitter.getName();
        }

    },

    GETSURNAME{

        /**
         * Get the dog sitter's surname.
         * @param clientMsg the message received from the client.
         * @return the message for the client.
         */
        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            DogSitter dogSitter = singleton.createDogSitterFromDB(email);
            return dogSitter.getSurname();
        }

    },

    GETPASSWORD{

        /**
         * Get the dog sitter's password.
         * @param clientMsg the message received from the client.
         * @return the message for the client.
         */
        public String execute(String clientMsg) {
            System.out.println(clientMsg);
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            DogSitter dogSitter = singleton.createDogSitterFromDB(email);
            return dogSitter.getPassword();
        }

    },

    GETPHONENUMBER{

        /**
         * Get the dog sitter's phone number.
         * @param clientMsg the message received from the client.
         * @return the message for the client.
         */
        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            DogSitter dogSitter = singleton.createDogSitterFromDB(email);
            return dogSitter.getPhoneNumber();
        }

    },

    GETDATEOFBIRTH{

        /**
         * Get the dog sitter's date of birth.
         * @param clientMsg the message received from the client.
         * @return the message for the client.
         */
        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            DogSitter dogSitter = singleton.createDogSitterFromDB(email);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return dateFormat.format(dogSitter.getDateOfBirth());
        }

    },

    GETADDRESS{

        /**
         * Get the dog sitter's address.
         * @param clientMsg the message received from the client.
         * @return the message for the client.
         */
        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            Address address = singleton.createDogSitterFromDB(email).getAddress();
            return address.getCountry() + "#" + address.getCity() + "#" + address.getStreet() + "#"
                    + address.getNumber() + "#" + address.getCap();
        }

    },

    GETPAYMENTMETHOD{

        /**
         * Get the dog sitter's payment method.
         * @param clientMsg the message received from the client.
         * @return the message for the client.
         */
        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            PaymentMethod pm = singleton.createDogSitterFromDB(email).getPaymentMethod();
            String strExpiration = dateFormat.format(pm.getExpirationDate());
            return pm.getNumber() + "*" + pm.getName() + "*" + pm.getSurname() + "*"
                    + strExpiration + "*" + pm.getCvv() + "*" + pm.getAmount();
        }

    },

    UPDATENAME{

        /**
         * Update the dog sitter's name.
         * @param clientMsg the message received from the client.
         * @return the message for the client.
         */
        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();
            String name = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            DogSitter dogSitter = singleton.createDogSitterFromDB(email);
            if (dogSitter.updateName(name)){
                return "true";
            } else {
                return "false";
            }
        }

    },

    UPDATESURNAME{

        /**
         * Update the dog sitter's surname.
         * @param clientMsg the message received from the client.
         * @return the message for the client.
         */
        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();
            String surname = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            DogSitter dogSitter = singleton.createDogSitterFromDB(email);
            if (dogSitter.updateSurname(surname)){
                return "true";
            } else {
                return "false";
            }
        }

    },

    UPDATEPASSWORD{

        /**
         * Update the dog sitter's password.
         * @param clientMsg the message received from the client.
         * @return the message for the client.
         */
        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();
            String password = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            DogSitter dogSitter = singleton.createDogSitterFromDB(email);
            if (dogSitter.updatePassword(password)){
                return "true";
            } else {
                return "false";
            }
        }

    },

    UPDATEPHONENUMBER{

        /**
         * Update the dog sitter's phone number.
         * @param clientMsg the message received from the client.
         * @return the message for the client.
         */
        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();
            String phoneNumber = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            DogSitter dogSitter = singleton.createDogSitterFromDB(email);
            if (dogSitter.updatePhoneNumber(phoneNumber)){
                return "true";
            } else {
                return "false";
            }
        }

    },

    UPDATEDATEOFBIRTH{

        /**
         * Update the dog sitter's date of birth.
         * @param clientMsg the message received from the client.
         * @return the message for the client.
         */
        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            SimpleDateFormat dateFormatDays = new SimpleDateFormat("dd/MM/yyyy");
            String email = tokenMsg.nextToken();
            String strDateOfBirth = tokenMsg.nextToken();
            Date dateOfBirth = null;
            try {
                dateOfBirth = dateFormatDays.parse(strDateOfBirth);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Singleton singleton = new Singleton();
            DogSitter dogSitter = singleton.createDogSitterFromDB(email);
            if (dogSitter.updateDateOfBirth(dateOfBirth)){
                return "true";
            } else {
                return "false";
            }
        }

    },

    UPDATEADDRESS{

        /**
         * Update the dog sitter's address.
         * @param clientMsg the message received from the client.
         * @return the message for the client.
         */
        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();
            String country = tokenMsg.nextToken();
            String city = tokenMsg.nextToken();
            String street = tokenMsg.nextToken();
            String number = tokenMsg.nextToken();
            String cap = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            DogSitter dogSitter = singleton.createDogSitterFromDB(email);
            if (dogSitter.updateAddress(country, city, street, number, cap)){
                return "true";
            } else {
                return "false";
            }
        }

    },

    UPDATEPAYMENTMETHOD{

        /**
         * Update the dog sitter's payment method.
         * @param clientMsg the message received from the client.
         * @return the message for the client.
         */
        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            SimpleDateFormat dateFormatDays = new SimpleDateFormat("dd/MM/yyyy");
            String email = tokenMsg.nextToken();
            String number = tokenMsg.nextToken();
            String name = tokenMsg.nextToken();
            String surname = tokenMsg.nextToken();
            Date expirationDate = null;
            try {
                expirationDate = dateFormatDays.parse(tokenMsg.nextToken());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String cvv = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            DogSitter dogSitter = singleton.createDogSitterFromDB(email);
            if (dogSitter.updatePaymentMethod(number, name, surname, expirationDate, cvv)){
                return "true";
            } else {
                return "false";
            }
        }

    },

    GETREVIEWLIST{

        /**
         * Get the dog sitter's list of reviews.
         * @param clientMsg the message received from the client.
         * @return the message for the client.
         */
        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();

            String serverMsg = "";
            Singleton singleton = new Singleton();
            DogSitter dogSitter = singleton.createDogSitterFromDB(email);
            HashMap<Integer, Review> reviewList = dogSitter.getReviewList();
            for (Integer key : reviewList.keySet()) {
                Review r = reviewList.get(key);
                serverMsg = serverMsg + r.getCode() + "#";
            }
            System.out.println(serverMsg);
            return serverMsg;
        }

    },

    UPDATEASSIGNMENTSTATE{

        /**
         * Update the state of a specified assignment.
         * @param clientMsg the message received from the client.
         * @return the message for the client.
         */
        public String execute(String clientMsg) {
            System.out.println("test: " + clientMsg);
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();
            int code = Integer.parseInt(tokenMsg.nextToken());
            String strState = tokenMsg.nextToken();
            Boolean state;

            if (strState.equals("true")){
                state = true;
            } else if (strState.equals("false")){
                state = false;
            } else {
                state = null;
            }

            Singleton singleton = new Singleton();
            DogSitter dogSitter = singleton.createDogSitterFromDB(email);
            if (dogSitter.updateAssignmentState(code, state)){
                return "true";
            } else {
                return "false";
            }
        }

    },

    UPDATECASHFLAG{

        /**
         * Update the dog sitter's cash flag.
         * @param clientMsg the message received from the client.
         * @return the message for the client.
         */
        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();
            String strState = tokenMsg.nextToken();
            boolean state;

            if (strState.equals("true")){
                state = true;
            } else {
                state = false;
            }

            Singleton singleton = new Singleton();
            DogSitter dogSitter = singleton.createDogSitterFromDB(email);
            if (dogSitter.updateCashFlag(state)){
                return "true";
            } else {
                return "false";
            }
        }

    },

    GETDOGSNUMBER{

        /**
         * Get the max number of dogs that a dog sitter's accepts simultaneously.
         * @param clientMsg the message received from the client.
         * @return the message for the client.
         */
        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            DogSitter dogSitter = singleton.createDogSitterFromDB(email);
            Integer nDogs = dogSitter.getDogsNumber();
            return nDogs.toString();
        }

    },

    GETAREA{

        /**
         * Get the dog sitter's area of work.
         * @param clientMsg the message received from the client.
         * @return the message for the client.
         */
        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            DogSitter dogSitter = singleton.createDogSitterFromDB(email);
            Area area = dogSitter.getArea();
            String serverMsg = "";
            for (String city: area.getPlaces()) {
                serverMsg = serverMsg + city + "#";
            }
            return serverMsg;
        }

    },

    ISACCEPTINGCASH{

        /**
         * Check if dog sitter accepts cash payment.
         * @param clientMsg the message received from the client.
         * @return the message for the client.
         */
        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            DogSitter dogSitter = singleton.createDogSitterFromDB(email);
            if (dogSitter.isAcceptingCash()){
                return "true";
            } else {
                return "false";
            }
        }

    },

    LISTDOGSIZE{

        /**
         * Get the list of dog's size that the dog sitter's accepts.
         * @param clientMsg the message received from the client.
         * @return the message for the client.
         */
        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            DogSitter dogSitter = singleton.createDogSitterFromDB(email);
            String serverMsg = "";
            for (DogSize size : dogSitter.getListDogSize()) {
                serverMsg = serverMsg + size + "#";
            }
            return serverMsg;
        }

    },

    ADDNEWPLACEAREA{

        /**
         * Add a new city where the dog sitter can work.
         * @param clientMsg the message received from the client.
         * @return the message for the client.
         */
        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();
            String city = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            DogSitter dogSitter = singleton.createDogSitterFromDB(email);
            if (dogSitter.addNewPlaceArea(city)){
                return "true";
            } else {
                return "false";
            }
        }

    },

    REMOVEPLACEAREA{

        /**
         * Remove a city from the dog sitter's area of work.
         * @param clientMsg the message received from the client.
         * @return the message for the client.
         */
        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();
            String city = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            DogSitter dogSitter = singleton.createDogSitterFromDB(email);
            if (dogSitter.removePlaceArea(city)){
                return "true";
            } else {
                return "false";
            }
        }

    },

    UPDATEDOGSNUMBER{

        /**
         * Update the max number of dogs that the dog sitter accepts simultaneously.
         * @param clientMsg the message received from the client.
         * @return the message for the client.
         */
        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();
            int nDogs = Integer.parseInt(tokenMsg.nextToken());

            Singleton singleton = new Singleton();
            DogSitter dogSitter = singleton.createDogSitterFromDB(email);
            if (dogSitter.updateDogsNumber(nDogs)){
                return "true";
            } else {
                return "false";
            }
        }

    },

    GETAVAILABILITY{

        /**
         * Get the dog sitter's availability.
         * @param clientMsg the message received from the client.
         * @return the message for the client.
         */
        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            DogSitter dogSitter = singleton.createDogSitterFromDB(email);
            String serverMsg = "";
            Availability availability = dogSitter.getDateTimeAvailability();
            int nWeekDays = 7;
            int i;
            WorkingTime[] workingTimeArray = availability.getArrayDays();
            for (i = 0; i < nWeekDays; i++){
                Time start = workingTimeArray[i].getStart();
                Time end = workingTimeArray[i].getEnd();
                serverMsg = serverMsg + start + "#" + end + "#";
            }
            return serverMsg;
        }

    },

    UPDATELISTDOGSIZE{

        /**
         * Update the list of dog's size that the dog sitter accepts.
         * @param clientMsg the message received from the client.
         * @return the message for the client.
         */
        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();
            boolean small = Boolean.parseBoolean(tokenMsg.nextToken());
            boolean medium = Boolean.parseBoolean(tokenMsg.nextToken());
            boolean big = Boolean.parseBoolean(tokenMsg.nextToken());
            boolean giant = Boolean.parseBoolean(tokenMsg.nextToken());

            Singleton singleton = new Singleton();
            DogSitter dogSitter = singleton.createDogSitterFromDB(email);
            if (dogSitter.updateListDogSize(small, medium, big, giant)){
                return "true";
            } else {
                return "false";
            }
        }

    },

    UPDATEAVAILABILITY{

        /**
         * Update the dog sitter's availability.
         * @param clientMsg the message received from the client.
         * @return the message for the client.
         */
        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();

            Availability availability = new Availability();
            Time start;
            Time end;
            for (WeekDays wd : WeekDays.values()) {
                String strStart = tokenMsg.nextToken();
                if (!(strStart.equals("null"))){
                    start = Time.valueOf(strStart);
                } else {
                    start = null;
                }
                String strEnd = tokenMsg.nextToken();
                if (!(strEnd.equals("null"))){
                    end = Time.valueOf(strEnd);
                } else {
                    end = null;
                }
                WorkingTime workingTime = new WorkingTime(start, end);
                availability.setDayAvailability(workingTime, wd);
            }

            Singleton singleton = new Singleton();
            DogSitter dogSitter = singleton.createDogSitterFromDB(email);
            if (dogSitter.updateDateTimeAvailability(availability)){
                return "true";
            } else {
                return "false";
            }
        }

    },

    SIGNUP{

        /**
         * Perform a registration for a new dog sitter to Canibau.
         * @param clientMsg the message received from the client.
         * @return the message for the client.
         */
        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();
            String name = tokenMsg.nextToken();
            String surname = tokenMsg.nextToken();
            String password = tokenMsg.nextToken();
            String phoneNumber = tokenMsg.nextToken();
            String strBirth = tokenMsg.nextToken();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date dateOfBirth = new Date();
            try {
                dateOfBirth = dateFormat.parse(strBirth);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String country = tokenMsg.nextToken();
            String city = tokenMsg.nextToken();
            String street = tokenMsg.nextToken();
            String number = tokenMsg.nextToken();
            String cap = tokenMsg.nextToken();
            Address address = new Address(country, city, street, number, cap);
            String cardNumber = tokenMsg.nextToken();
            String cardName = tokenMsg.nextToken();
            String cardSurname = tokenMsg.nextToken();
            String strExpiration = tokenMsg.nextToken();
            Date expirationDate = new Date();
            try {
                expirationDate = dateFormat.parse(strExpiration);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String cvv = tokenMsg.nextToken();
            double amount = Double.parseDouble(tokenMsg.nextToken());
            PaymentMethod paymentMethod = new PaymentMethod(cardNumber, cardName, cardSurname, expirationDate, cvv, amount);

            Area area = decodeArea(tokenMsg.nextToken());

            boolean small = Boolean.parseBoolean(tokenMsg.nextToken());
            boolean medium = Boolean.parseBoolean(tokenMsg.nextToken());
            boolean big = Boolean.parseBoolean(tokenMsg.nextToken());
            boolean giant = Boolean.parseBoolean(tokenMsg.nextToken());

            HashSet<DogSize> dogSizeList = createDogSizeList(small, medium, big, giant);

            int dogsNumber = Integer.parseInt(tokenMsg.nextToken());
            String biography = tokenMsg.nextToken();

            Availability availability = new Availability();
            Time start;
            Time end;
            for (WeekDays wd : WeekDays.values()) {
                String strStart = tokenMsg.nextToken();
                if (!(strStart.equals("null"))){
                    start = Time.valueOf(strStart);
                } else {
                    start = null;
                }
                String strEnd = tokenMsg.nextToken();
                if (!(strEnd.equals("null"))){
                    end = Time.valueOf(strEnd);
                } else {
                    end = null;
                }
                WorkingTime workingTime = new WorkingTime(start, end);
                availability.setDayAvailability(workingTime, wd);
            }

            boolean cashFlag;
            if (tokenMsg.nextToken().equals("true")){
                cashFlag = true;
            } else {
                cashFlag = false;
            }

            SignUp signUp = new SignUp();
            if (signUp.dogSitterSignUp(email, name, surname, password, phoneNumber, dateOfBirth, address, paymentMethod, area, dogSizeList, dogsNumber, biography, availability, cashFlag)){
                return "true";
            } else {
                return "false";
            }
        }

    },

    GETBIOGRAPHY{

        /**
         * Get the dog sitter's biography.
         * @param clientMsg the message received from the client.
         * @return the message for the client.
         */
        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            DogSitter dogSitter = singleton.createDogSitterFromDB(email);
            return dogSitter.getBiography();
        }

    },

    REPLYTOREVIEW{

        /**
         * Reply to a customer's review.
         * @param clientMsg the message received from the client.
         * @return the message for the client.
         */
        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();
            int code = Integer.parseInt(tokenMsg.nextToken());
            String reply = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            DogSitter dogSitter = singleton.createDogSitterFromDB(email);
            if(dogSitter.replyToReview(code, reply)){
                System.out.println("test reply");
                return "true";
            } else {
                return "false";
            }
        }

    },

    GETCUSTOMEREMAILOFASSIGNMENT{

        /**
         * Get the customer's email address of a specified assignment.
         * @param clientMsg the message received from the client.
         * @return the message for the client.
         */
        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();
            int code = Integer.parseInt(tokenMsg.nextToken());

            Singleton singleton = new Singleton();
            DogSitter dogSitter = singleton.createDogSitterFromDB(email);
            return dogSitter.getCustomerEmailOfAssignment(code);
        }

    };

    /**
     * Perform an action based on the received message.
     * @param clientMsg the message received from the client.
     * @return the message for the client.
     */
    public abstract String execute(String clientMsg);
}
