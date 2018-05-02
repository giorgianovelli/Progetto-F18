package staticClasses;

import database.DBConnector;
import engine.*;
import enumeration.DogSize;
import enumeration.WeekDays;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class ObjectCreator {
    public static Customer createCustomerFromDB(String customerEmail){
        DBConnector dbConnector = new DBConnector();
        try {
            ResultSet rs = dbConnector.askDB("SELECT EMAIL, NAME, SURNAME, PASSWORD, PHONE_NUMB, BIRTHDATE, PAYMENT FROM CUSTOMERS WHERE EMAIL = '" + customerEmail + "'");
            rs.next();
            String email = rs.getString("EMAIL");
            String name = rs.getString("NAME");
            String surname = rs.getString("SURNAME");
            String password = rs.getString("PASSWORD");
            String phone = rs.getString("PHONE_NUMB");
            Date birthdate = rs.getDate("BIRTHDATE");
            String payment = rs.getString("PAYMENT");
            dbConnector.closeConnection();

            rs = dbConnector.askDB("SELECT OWNER_NAME, OWNER_SURNAME, EXPIRATION_DATE, CVV, AMOUNT FROM CREDIT_CARDS WHERE NUM = '" + payment + "'");
            rs.next();
            String ownerName = rs.getString("OWNER_NAME");
            String ownerSurname = rs.getString("OWNER_SURNAME");
            Date expirationDate = rs.getDate("EXPIRATION_DATE");
            int cvv = rs.getInt("CVV");
            double amount = rs.getDouble("AMOUNT");
            PaymentMethod paymentMethod = new PaymentMethod(payment, ownerName, ownerSurname, expirationDate, cvv, amount);
            dbConnector.closeConnection();

            rs = dbConnector.askDB("SELECT COUNTRY, CITY, STREET, CNUMBER, CAP FROM ADDRESS WHERE EMAIL = '" + customerEmail + "'");
            rs.next();
            String country = rs.getString("COUNTRY");
            String city = rs.getString("CITY");
            String street = rs.getString("STREET");
            String cnumber = rs.getString("CNUMBER");
            String cap = rs.getString("CAP");
            Address address = new Address(country, city, street, cnumber, cap);
            dbConnector.closeConnection();

            return new Customer(email, name, surname, password, phone, birthdate, address, paymentMethod);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static DogSitter createDogSitterFromDB(String dogSitterEmail){
        DBConnector dbConnector = new DBConnector();
        try {
            ResultSet rs = dbConnector.askDB("SELECT EMAIL, NAME, SURNAME, PASSWORD, PHONE_NUMB, BIRTHDATE, PAYMENT, CASH_FLAG, NDOGS, BIOGRAPHY FROM DOGSITTERS WHERE EMAIL = '" + dogSitterEmail + "'");
            rs.next();
            String email = rs.getString("EMAIL");
            String name = rs.getString("NAME");
            String surname = rs.getString("SURNAME");
            String password = rs.getString("PASSWORD");
            String phone = rs.getString("PHONE_NUMB");
            Date birthdate = rs.getDate("BIRTHDATE");
            String payment = rs.getString("PAYMENT");
            boolean cashFlag = rs.getBoolean("CASH_FLAG");
            //String area = rs.getString("AREA");
            int nDogs = rs.getInt("NDOGS");
            String biography = rs.getString("BIOGRAPHY");
            dbConnector.closeConnection();

            rs = dbConnector.askDB("SELECT COUNTRY, CITY, STREET, CNUMBER, CAP FROM ADDRESS WHERE EMAIL = '" + dogSitterEmail + "'");
            rs.next();
            String country = rs.getString("COUNTRY");
            String city = rs.getString("CITY");
            String street = rs.getString("STREET");
            String cnumber = rs.getString("CNUMBER");
            String cap = rs.getString("CAP");
            Address address = new Address(country, city, street, cnumber, cap);
            dbConnector.closeConnection();

            rs = dbConnector.askDB("SELECT CITY FROM DOGSITTER_AREA WHERE DOGSITTER = '" + email + "'");
            Area listArea = new Area();
            while (rs.next()){
                String cityOp = rs.getString("CITY");
                listArea.addPlaces(city);
            }
            dbConnector.closeConnection();

            rs = dbConnector.askDB("SELECT SMALL, MEDIUM, BIG, GIANT FROM DOGS_ACCEPTED WHERE DOGSITTER = '" + email + "'");
            HashSet<DogSize> listDogSize = new HashSet<DogSize>();
            rs.next();
            boolean small = rs.getBoolean("SMALL");
            if (small){
                listDogSize.add(DogSize.SMALL);
            }
            boolean medium = rs.getBoolean("MEDIUM");
            if (medium){
                listDogSize.add(DogSize.MEDIUM);
            }
            boolean big = rs.getBoolean("BIG");
            if (big){
                listDogSize.add(DogSize.BIG);
            }
            boolean giant = rs.getBoolean("GIANT");
            System.out.println(giant);
            if (giant){
                listDogSize.add(DogSize.GIANT);
            }
            dbConnector.closeConnection();

            rs = dbConnector.askDB("SELECT NUM, OWNER_NAME, OWNER_SURNAME, EXPIRATION_DATE, CVV, AMOUNT FROM CREDIT_CARDS WHERE NUM = '" + payment + "'");
            rs.next();
            String num = rs.getString("NUM");
            String ownerName = rs.getString("OWNER_NAME");
            String ownerSurname = rs.getString("OWNER_SURNAME");
            Date expirationDate = rs.getDate("EXPIRATION_DATE");
            int cvv = rs.getInt("CVV");
            double amount = rs.getDouble("AMOUNT");
            PaymentMethod paymentMethod = new PaymentMethod(num, ownerName, ownerSurname, expirationDate, cvv, amount);
            dbConnector.closeConnection();

            rs = dbConnector.askDB("SELECT  MON_START, MON_END, TUE_START, TUE_END, WED_START, WED_END, THU_START, THU_END, FRI_START, FRI_END, SAT_START, SAT_END, SUN_START, SUN_END FROM AVAILABILITY WHERE DOGSITTER = '" + dogSitterEmail + "'");
            Availability availability = new Availability();
            rs.next();
            Time monStart = rs.getTime("MON_START");
            Time monEnd = rs.getTime("MON_END");
            WorkingTime mon = new WorkingTime(monStart, monEnd);
            availability.setDayAvailability(mon, WeekDays.MON);
            Time tueStart = rs.getTime("TUE_START");
            Time tueEnd = rs.getTime("TUE_END");
            WorkingTime tue = new WorkingTime(tueStart, tueEnd);
            availability.setDayAvailability(tue, WeekDays.TUE);
            Time wedStart = rs.getTime("WED_START");
            Time wedEnd = rs.getTime("WED_END");
            WorkingTime wed = new WorkingTime(wedStart, wedEnd);
            availability.setDayAvailability(wed, WeekDays.WED);
            Time thuStart = rs.getTime("THU_START");
            Time thuEnd = rs.getTime("THU_END");
            WorkingTime thu = new WorkingTime(thuStart, thuEnd);
            availability.setDayAvailability(thu, WeekDays.THU);
            Time friStart = rs.getTime("FRI_START");
            Time friEnd = rs.getTime("FRI_END");
            WorkingTime fri = new WorkingTime(friStart, friEnd);
            availability.setDayAvailability(fri, WeekDays.FRI);
            Time satStart = rs.getTime("SAT_START");
            Time satEnd = rs.getTime("SAT_END");
            WorkingTime sat = new WorkingTime(satStart, satEnd);
            availability.setDayAvailability(sat, WeekDays.SAT);
            Time sunStart = rs.getTime("SUN_START");
            Time sunEnd = rs.getTime("SUN_END");
            WorkingTime sun = new WorkingTime(sunStart, sunEnd);
            availability.setDayAvailability(sun, WeekDays.SUN);
            dbConnector.closeConnection();

            return new DogSitter(email, name, surname, password, phone, birthdate, address, paymentMethod, listArea, listDogSize, nDogs, biography, availability, cashFlag);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Dog createDogFromDB(int dogID){
        DBConnector dbConnector = new DBConnector();
        Dog dog = null;
        try {
            ResultSet rs = dbConnector.askDB("SELECT ID, NAME, BREED, WEIGHT, AGE, OWNER_EMAIL FROM DOGS WHERE ID = '" + dogID + "'");
            rs.next();
            int id = rs.getInt("ID");
            String name = rs.getString("NAME");
            String breed = rs.getString("BREED");
            double weight = rs.getDouble("WEIGHT");
            int age = rs.getInt("AGE");
            dbConnector.closeConnection();

            rs = dbConnector.askDB("SELECT SIZE FROM BREEDS WHERE NAME = '" + breed + "'");
            rs.next();
            DogSize size = DogSize.valueOf(rs.getString("SIZE"));
            dbConnector.closeConnection();

            dog = new Dog(name, breed, size, age, weight, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dog;
    }
}
