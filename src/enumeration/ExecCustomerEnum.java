package enumeration;

import database.DBConnector;
import server.*;
import server.bank.PaymentMethod;
import server.places.Address;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

import static enumeration.enumStaticMethods.*;

public enum ExecCustomerEnum {
    ACCESSVERIFIER{

        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String inputUser = tokenMsg.nextToken();
            String inputPassword = tokenMsg.nextToken();

            Login loginCustomer = new Login();
            try {
                if(loginCustomer.customerAccessDataVerifier(inputUser, inputPassword)){
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

        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            HashMap<Integer, Assignment> customerListAssignment = singleton.getCustomerListAssignmentFromDB(email);
            return getListAssignment(customerListAssignment);
        }

    },

    GETDOGSITTERNAMEOFASSIGNMENT{

        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            int code = Integer.parseInt(tokenMsg.nextToken());

            DBConnector dbConnector = new DBConnector();
            String emailDogSitter = null;
            DogSitter dogSitter = null;
            try {
                ResultSet rs = dbConnector.askDB("SELECT DOGSITTER FROM ASSIGNMENT WHERE CODE = '" + code + "'");
                rs.next();
                emailDogSitter = rs.getString("DOGSITTER");
                Singleton singleton = new Singleton();
                dogSitter = singleton.createDogSitterFromDB(emailDogSitter);
                dbConnector.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return dogSitter.getName();
        }

    },

    GETDOGSITTERSURNAMEOFASSIGNMENT{

        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            int code = Integer.parseInt(tokenMsg.nextToken());

            DBConnector dbConnector = new DBConnector();
            String emailDogSitter = null;
            DogSitter dogSitter = null;
            try {
                ResultSet rs = dbConnector.askDB("SELECT DOGSITTER FROM ASSIGNMENT WHERE CODE = '" + code + "'");
                rs.next();
                emailDogSitter = rs.getString("DOGSITTER");
                Singleton singleton = new Singleton();
                dogSitter = singleton.createDogSitterFromDB(emailDogSitter);
                dbConnector.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return dogSitter.getSurname();
        }

    },

    GETREVIEW{

        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            int code = Integer.parseInt(tokenMsg.nextToken());

            return getReview(code);
        }

    },


    GETNAME{

        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            Customer customer = singleton.createCustomerFromDB(email);
            return customer.getName();
        }

    },

    GETSURNAME{

        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            Customer customer = singleton.createCustomerFromDB(email);
            return customer.getSurname();
        }

    },

    GETPASSWORD{

        public String execute(String clientMsg) {
            System.out.println(clientMsg);
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            Customer customer = singleton.createCustomerFromDB(email);
            return customer.getPassword();
        }

    },

    GETPHONENUMBER{

        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            Customer customer = singleton.createCustomerFromDB(email);
            return customer.getPhoneNumber();
        }

    },

    GETDATEOFBIRTH{

        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            Customer customer = singleton.createCustomerFromDB(email);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String dateOfBirth = dateFormat.format(customer.getDateOfBirth());
            return dateOfBirth;
        }

    },

    GETADDRESS{

        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            Address address = singleton.createCustomerFromDB(email).getAddress();
            return address.getCountry() + "#" + address.getCity() + "#" + address.getStreet() + "#"
                    + address.getNumber() + "#" + address.getCap();
        }

    },

    GETPAYMENTMETHOD{

        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            PaymentMethod pm = singleton.createCustomerFromDB(email).getPaymentMethod();
            String strExpiration = dateFormat.format(pm.getExpirationDate());
            return pm.getNumber() + "*" + pm.getName() + "*" + pm.getSurname() + "*"
                    + strExpiration + "*" + pm.getCvv() + "*" + pm.getAmount();
        }

    },

    UPDATENAME{

        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();
            String name = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            Customer customer = singleton.createCustomerFromDB(email);
            if (customer.updateName(name)){
                return "true";
            } else {
                return "false";
            }
        }

    },

    UPDATESURNAME{

        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();
            String surname = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            Customer customer = singleton.createCustomerFromDB(email);
            if (customer.updateSurname(surname)){
                return "true";
            } else {
                return "false";
            }
        }

    },

    UPDATEPASSWORD{

        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();
            String password = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            Customer customer = singleton.createCustomerFromDB(email);
            if (customer.updatePassword(password)){
                return "true";
            } else {
                return "false";
            }
        }

    },

    UPDATEPHONENUMBER{

        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();
            String phoneNumber = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            Customer customer = singleton.createCustomerFromDB(email);
            if (customer.updatePhoneNumber(phoneNumber)){
                return "true";
            } else {
                return "false";
            }
        }

    },

    UPDATEDATEOFBIRTH{

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
            Customer customer = singleton.createCustomerFromDB(email);
            if (customer.updateDateOfBirth(dateOfBirth)){
                return "true";
            } else {
                return "false";
            }
        }

    },

    UPDATEADDRESS{

        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();
            String country = tokenMsg.nextToken();
            String city = tokenMsg.nextToken();
            String street = tokenMsg.nextToken();
            String number = tokenMsg.nextToken();
            String cap = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            Customer customer = singleton.createCustomerFromDB(email);
            if (customer.updateAddress(country, city, street, number, cap)){
                return "true";
            } else {
                return "false";
            }
        }

    },

    UPDATEPAYMENTMETHOD{

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
            Customer customer = singleton.createCustomerFromDB(email);
            if (customer.updatePaymentMethod(number, name, surname, expirationDate, cvv)){
                return "true";
            } else {
                return "false";
            }
        }

    },

    SEARCH{

        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            SimpleDateFormat dateFormatMinutes = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String email = tokenMsg.nextToken();
            Date dateStart = null;
            Date dateEnd = null;
            try {
                dateStart = dateFormatMinutes.parse(tokenMsg.nextToken());
                dateEnd = dateFormatMinutes.parse(tokenMsg.nextToken());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String country = tokenMsg.nextToken();
            String city = tokenMsg.nextToken();
            String street = tokenMsg.nextToken();
            String number = tokenMsg.nextToken();
            String cap = tokenMsg.nextToken();
            String strDogList = tokenMsg.nextToken();
            String strCash = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            Customer customer = singleton.createCustomerFromDB(email);
            Address meetingPoint = new Address(country, city, street, number, cap);

            boolean cash;
            cash = strCash.equals("true");

            StringTokenizer tokenDogList = new StringTokenizer(strDogList, "*");
            HashSet<Dog> dogList = new HashSet<Dog>();
            while (tokenDogList.hasMoreTokens()){
                Dog d = singleton.createDogFromDB(Integer.parseInt(tokenDogList.nextToken()));
                dogList.add(d);
            }

            HashSet<String> resultList = customer.search(dateStart, dateEnd, meetingPoint, dogList, cash);
            String serverMsg = "";
            for (String emailDs : resultList) {
                serverMsg = serverMsg + emailDs + "#";
            }

            return serverMsg;
        }

    },

    ESTIMATEPRICEASSIGNMENT{

        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            SimpleDateFormat dateFormatMinutes = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String email = tokenMsg.nextToken();
            String strDogList = tokenMsg.nextToken();
            Date dateStart = null;
            Date dateEnd = null;
            try {
                dateStart = dateFormatMinutes.parse(tokenMsg.nextToken());
                dateEnd = dateFormatMinutes.parse(tokenMsg.nextToken());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Singleton singleton = new Singleton();
            Customer customer = singleton.createCustomerFromDB(email);
            StringTokenizer tokenDogList = new StringTokenizer(strDogList, "*");
            HashSet<Dog> dogList = new HashSet<Dog>();
            while (tokenDogList.hasMoreTokens()) {
                Dog d = singleton.createDogFromDB(Integer.parseInt(tokenDogList.nextToken()));
                dogList.add(d);
            }

            Double price = customer.estimatePriceAssignment(dogList, dateStart, dateEnd);
            return price.toString();
        }

    },

    ADDASSIGNMENT{

        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            SimpleDateFormat dateFormatMinutes = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String email = tokenMsg.nextToken();
            String emailDogSitter = tokenMsg.nextToken();
            Date dateStartAssignment = null;
            Date dateEndAssignment = null;
            try {
                dateStartAssignment = dateFormatMinutes.parse(tokenMsg.nextToken());
                dateEndAssignment = dateFormatMinutes.parse(tokenMsg.nextToken());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String strDogList = tokenMsg.nextToken();
            String country = tokenMsg.nextToken();
            String city = tokenMsg.nextToken();
            String street = tokenMsg.nextToken();
            String number = tokenMsg.nextToken();
            String cap = tokenMsg.nextToken();
            boolean paymentInCash = Boolean.valueOf(tokenMsg.nextToken());

            Singleton singleton = new Singleton();
            Customer customer = singleton.createCustomerFromDB(email);

            StringTokenizer tokenDogList = new StringTokenizer(strDogList, "*");
            HashSet<Dog> dogList = new HashSet<Dog>();
            while (tokenDogList.hasMoreTokens()){
                Dog d = singleton.createDogFromDB(Integer.parseInt(tokenDogList.nextToken()));
                dogList.add(d);
            }

            Address meetingPoint = new Address(country, city, street, number, cap);

            if (customer.addAssignment(emailDogSitter, dateStartAssignment, dateEndAssignment, dogList, meetingPoint, paymentInCash)){
                return "true";
            } else {
                return "false";
            }
        }

    },

    REMOVEASSIGNMENT{

        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();
            int code = Integer.parseInt(tokenMsg.nextToken());

            Singleton singleton = new Singleton();
            Customer customer = singleton.createCustomerFromDB(email);
            if (customer.removeAssignment(code)){
                return "true";
            } else {
                return "false";
            }
        }

    },

    ADDREVIEW{

        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();
            int code = Integer.parseInt(tokenMsg.nextToken());
            String emailDogSitter = tokenMsg.nextToken();
            int rating = Integer.parseInt(tokenMsg.nextToken());
            String title = tokenMsg.nextToken();
            String comment = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            Customer customer = singleton.createCustomerFromDB(email);
            if (customer.addReview(code, emailDogSitter, rating, title, comment)){
                return "true";
            } else {
                return "false";
            }
        }

    },

    REMOVEREVIEW{

        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();
            int code = Integer.parseInt(tokenMsg.nextToken());

            Singleton singleton = new Singleton();
            Customer customer = singleton.createCustomerFromDB(email);
            if (customer.removeReview(code)){
                return "true";
            } else {
                return "false";
            }
        }

    },

    GETREVIEWLIST{

        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();

            String serverMsg = "";
            Singleton singleton = new Singleton();
            Customer customer = singleton.createCustomerFromDB(email);
            HashMap<Integer, Review> reviewList = customer.getReviewList();
            for (Integer key : reviewList.keySet()) {
                Review r = reviewList.get(key);
                serverMsg = serverMsg + r.getCode() + "#";
            }
            System.out.println(serverMsg);
            return serverMsg;
        }

    },

    ADDDOG{

        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();
            String name = tokenMsg.nextToken();
            String breed = tokenMsg.nextToken();
            SimpleDateFormat dateFormatDays = new SimpleDateFormat("dd/MM/yyyy");
            Date dateOfBirth = null;
            try {
                dateOfBirth = dateFormatDays.parse(tokenMsg.nextToken());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            double weight = Double.parseDouble(tokenMsg.nextToken());

            Singleton singleton = new Singleton();
            Customer customer = singleton.createCustomerFromDB(email);
            if (customer.addDog(email, name, breed, dateOfBirth, weight)){
                return "true";
            } else {
                return "false";
            }
        }

    },

    DISABLEDOG{

        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();
            int ID = Integer.parseInt(tokenMsg.nextToken());

            Singleton singleton = new Singleton();
            Customer customer = singleton.createCustomerFromDB(email);
            if (customer.disableDog(ID)){
                return "true";
            } else {
                return "false";
            }
        }

    },

    GETDOGLIST{

        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            Customer customer = singleton.createCustomerFromDB(email);
            HashSet<Dog> dogList = customer.getDogList();
            String msg = "";
            for (Dog d : dogList) {
                msg = msg + d.getID() + "&" + d.getName() + "&" + d.getBreed() + "&" + d.getSize() + "&" + d.getAge() + "&"
                        + d.getWeight() + "&" + d.isEnabled() + "*";
            }
            return msg;
        }

    },

    SIGNUP{

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

            SignUp signUp = new SignUp();
            if (signUp.customerSignUp(email, name, surname, password, phoneNumber, dateOfBirth, address, paymentMethod)){
                return "true";
            } else {
                return "false";
            }
        }

    },

    ISINCASHASSIGNMENT{

        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();
            int code = Integer.parseInt(tokenMsg.nextToken());

            Singleton singleton = new Singleton();
            Customer customer = singleton.createCustomerFromDB(email);
            if (customer.isInCashPaymentMethodOfAssignment(code) == true){
                return "true";
            } else if (customer.isInCashPaymentMethodOfAssignment(code) == false){
                return "false";
            } else {
                return "null";
            }
        }

    },

    UPDATEDOGNAME{

        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();
            int ID = Integer.parseInt(tokenMsg.nextToken());
            String name = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            Customer customer = singleton.createCustomerFromDB(email);
            if (customer.updateDogName(ID, name)){
                return "true";
            } else {
                return "false";
            }
        }

    },

    UPDATEDOGAGE{

        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();
            int ID = Integer.parseInt(tokenMsg.nextToken());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date dateOfBirth = new Date();
            try {
                dateOfBirth = dateFormat.parse(tokenMsg.nextToken());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Singleton singleton = new Singleton();
            Customer customer = singleton.createCustomerFromDB(email);
            if (customer.updateDogAge(ID, dateOfBirth)){
                return "true";
            } else {
                return "false";
            }
        }

    },

    UPDATEDOGWEIGHT{

        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();
            int ID = Integer.parseInt(tokenMsg.nextToken());
            Double weight = Double.parseDouble(tokenMsg.nextToken());

            Singleton singleton = new Singleton();
            Customer customer = singleton.createCustomerFromDB(email);
            if (customer.updateDogWeight(ID, weight)){
                return "true";
            } else {
                return "false";
            }
        }

    };


    public abstract String execute(String clientMsg);

}
