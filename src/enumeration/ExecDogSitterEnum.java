package enumeration;

import database.DBConnector;
import server.*;
import server.bank.PaymentMethod;
import server.places.Address;
import server.places.Area;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;

import static enumeration.enumStaticMethods.*;

public enum ExecDogSitterEnum {
    ACCESSVERIFIER{

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

        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            HashMap<Integer, Assignment> dogSitterListAssignment = singleton.getDogSitterListAssignmentFromDB(email);
            return getListAssignment(dogSitterListAssignment);
        }

    },

    GETCUSTOMERNAMEOFASSIGNMENT{

        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            int code = Integer.parseInt(tokenMsg.nextToken());

            DBConnector dbConnector = new DBConnector();
            String emailCustomer = null;
            Customer customer = null;
            try {
                ResultSet rs = dbConnector.askDB("SELECT CUSTOMER FROM ASSIGNMENT WHERE CODE = '" + code + "'");
                rs.next();
                emailCustomer = rs.getString("CUSTOMER");
                Singleton singleton = new Singleton();
                customer = singleton.createCustomerFromDB(emailCustomer);
                dbConnector.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return customer.getName();
        }

    },

    GETCUSTOMERSURNAMEOFASSIGNMENT{

        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            int code = Integer.parseInt(tokenMsg.nextToken());

            DBConnector dbConnector = new DBConnector();
            String emailCustomer = null;
            Customer customer = null;
            try {
                ResultSet rs = dbConnector.askDB("SELECT CUSTOMER FROM ASSIGNMENT WHERE CODE = '" + code + "'");
                rs.next();
                emailCustomer = rs.getString("CUSTOMER");
                Singleton singleton = new Singleton();
                customer = singleton.createCustomerFromDB(emailCustomer);
                dbConnector.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return customer.getSurname();
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
            DogSitter dogSitter = singleton.createDogSitterFromDB(email);
            return dogSitter.getName();
        }

    },

    GETSURNAME{

        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            DogSitter dogSitter = singleton.createDogSitterFromDB(email);
            return dogSitter.getSurname();
        }

    },

    GETPASSWORD{

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

        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            DogSitter dogSitter = singleton.createDogSitterFromDB(email);
            return dogSitter.getPhoneNumber();
        }

    },

    GETDATEOFBIRTH{

        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            DogSitter dogSitter = singleton.createDogSitterFromDB(email);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String dateOfBirth = dateFormat.format(dogSitter.getDateOfBirth());
            return dateOfBirth;
        }

    },

    GETADDRESS{

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
            int cvv = Integer.parseInt(tokenMsg.nextToken());

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

    GETDOGNUMBER{

        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            DogSitter dogSitter = singleton.createDogSitterFromDB(email);
            Integer nDogs = dogSitter.getDogNumber();
            return nDogs.toString();
        }

    },

    GETAREA{

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

    };


    public abstract String execute(String clientMsg);
}
