package enumeration;

import database.DBConnector;
import server.*;
import server.bank.PaymentMethod;
import server.places.Address;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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

    };


    public abstract String execute(String clientMsg);
}
