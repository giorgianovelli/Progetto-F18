package test;

import client.proxy.DogSitterProxy;
import server.Assignment;

import java.util.HashMap;

public class TestDogSitterProxy {
    public static void main(String[] args) {
        DogSitterProxy proxy = new DogSitterProxy("MARCO.CARTA@GMAIL.COM");
        DogSitterProxy proxyLogin = new DogSitterProxy();
        System.out.println("test login true: " + proxyLogin.dogSitterAccessDataVerifier("MARCO.CARTA@GMAIL.COM", "PROGETTO123"));
        System.out.println("test login false: " + proxyLogin.dogSitterAccessDataVerifier("MARCO.CARTA@GMAIL.COM", "PROGGGGETTO123"));
        /*HashMap<Integer, Assignment> listAssignment = proxy.getAssignmentList();
        for (Integer key : listAssignment.keySet()) {
            Assignment a = listAssignment.get(key);
            System.out.println(a.getCode());
        }*/
        /*HashMap<Integer, Assignment> listAssignment = proxy.getAssignmentList();
        Assignment a = listAssignment.get(1);
        System.out.println(a.getDateStart());
        System.out.println(proxy.getCustomerNameOfAssignment(1));
        System.out.println(proxy.getCustomerSurnameOfAssignment(1));
        System.out.println(proxy.getReview(1));
        proxy.getName();
        proxy.getSurname();
        proxy.getPassword();
        proxy.getPhoneNumber();
        proxy.getDateOfBirth();
        proxy.getAddress();
        proxy.getPaymentMethod();*/

        /*proxy.updateName("PIPINO");
        proxy.updateSurname("IL GRANDE");
        proxy.updatePassword("CAMIBAU");
        proxy.updatePhoneNumber("3345522111");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            proxy.updateDateOfBirth(dateFormat.parse("22/12/2000"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        proxy.updateAddress("ITALY", "BOLOGNA", "VIA DELLA MORTADELLA", "6s", "40121");
        Date expiration = new Date();
        try {
            expiration = dateFormat.parse("31/06/2020");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        proxy.updatePaymentMethod("9999999999999999", "GIULIO", "ADRIATICO", expiration, 333);*/
        /*HashMap<Integer, Review> reviewList = proxy.getReviewList();
        Review r = reviewList.get(1);
        System.out.println(r.getComment());
        proxy.updateAssignmentState(1, true);
        Assignment one = listAssignment.get(1);
        System.out.println(one.getState());*/
        //proxy.updateCashFlag(true);
        /*System.out.println(proxy.getDogsNumber());
        Area area = proxy.getArea();
        area.printPlaces();
        System.out.println(proxy.isAcceptingCash());
        HashSet<DogSize> listDogSize = proxy.getListDogSize();
        System.out.println("dog size list:");
        for (DogSize ds : listDogSize) {
            System.out.println(ds);
        }*/
        //System.out.println(proxy.addNewPlaceArea("MILANO"));
        //System.out.println(proxy.removePlaceArea("MILANO"));
        //proxy.updateDogsNumber(7);
        /*int i;
        Availability availability = proxy.getDateTimeAvailability();
        WorkingTime[] workingTimeArray = availability.getArrayDays();
        for (i = 0; i < 7; i++){
            Time start = workingTimeArray[i].getStart();
            Time end = workingTimeArray[i].getEnd();
            System.out.println(start + ", " + end);
        }*/
        //proxy.updateListDogSize(true, true, true, true);
        /*Availability availability = new Availability();
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
        proxy.updateDateTimeAvailability(availability);*/
        //proxy.getBiography();
        proxy.replyToReview(1, "prova review!");
    }
}
