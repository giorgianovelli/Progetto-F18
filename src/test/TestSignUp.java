package test;

import client.proxy.DogSitterProxy;
import server.Availability;
import server.DogSize;
import server.bank.PaymentMethod;
import server.dateTime.WeekDays;
import server.dateTime.WorkingTime;
import server.places.Address;
import server.places.Area;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

public class TestSignUp {
    public static void main(String[] args) {
        //SignUp signUp = new SignUp();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date birth = new Date();
        Date expiration = new Date();
        Address address = new Address("ITALY", "PIEVE PORTO MORONE", "VIA DEL CHI SA DOVE", "5", "27017");
        Area area = new Area();
        area.addPlace("PAVIA");
        area.addPlace("CORTEOLONA");
        area.addPlace("PIEVE PORTO MORONE");
        HashSet<DogSize> dogSizes = new HashSet<DogSize>();
        dogSizes.add(DogSize.BIG);
        dogSizes.add(DogSize.MEDIUM);

        Availability availability = new Availability();
        String strTime1 = "00:30:00";
        String strTime2 = "23:50:00";
        String strTime3 = "14:00:00";
        String strTime4 = "18:00:00";
        String strTime5 = "00:10:00";
        String strTime6 = "22:05:00";
        String strTime7 = "06:30:00";
        String strTime8 = "19:40:00";
        String strTime0 = "00:00:00";
        Time time1 = Time.valueOf(strTime1);
        Time time2 = Time.valueOf(strTime3);
        Time time5 = Time.valueOf(strTime5);
        Time time7 = Time.valueOf(strTime7);
        Time time8 = Time.valueOf(strTime8);
        availability.setDayAvailability(new WorkingTime(time1, time2), WeekDays.MON);
        availability.setDayAvailability(new WorkingTime(time7, time8), WeekDays.TUE);
        availability.setDayAvailability(new WorkingTime(Time.valueOf(strTime5), Time.valueOf(strTime7)), WeekDays.WED);
        availability.setDayAvailability(new WorkingTime(Time.valueOf(strTime1), Time.valueOf(strTime8)), WeekDays.THU);
        availability.setDayAvailability(new WorkingTime(Time.valueOf(strTime3), Time.valueOf(strTime6)), WeekDays.FRI);
        availability.setDayAvailability(new WorkingTime(Time.valueOf(strTime3), Time.valueOf(strTime4)), WeekDays.SAT);
        availability.setDayAvailability(new WorkingTime(Time.valueOf(strTime0), Time.valueOf(strTime0)), WeekDays.SUN);

        try {
            birth = dateFormat.parse("23/06/1996");
            expiration = dateFormat.parse("31/07/2023");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //PaymentMethod paymentMethod = new PaymentMethod("1234567890987654", "NICOLAS", "CAROLO", expiration, 000, 1000);
        PaymentMethod paymentMethod2 = new PaymentMethod("0987653267490924", "NICOLAS", "CAROLO", expiration, "111", 2000);
        //CustomerProxy proxy = new CustomerProxy();
        DogSitterProxy dsProxy = new DogSitterProxy();
        //proxy.customerSignUp("NICOLAS.CAROLO@EMAIL.COM", "NICOLAS", "CAROLO", "CIAOBELLO", "3337755000", birth, address, paymentMethod);
        dsProxy.dogSitterSignUp("NICOLAS.DOGSITTER@EMAIL.COM", "NICOLAS", "CAROLO", "CIAOBELLO", "3337755000", birth, address, paymentMethod2, area, dogSizes, 2, "Mi presento...", availability, false);
    }
}
