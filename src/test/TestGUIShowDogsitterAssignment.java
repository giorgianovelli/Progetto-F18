package test;

import client.gui.*;
import client.proxy.DogSitterProxy;

public class TestGUIShowDogsitterAssignment {
    public static void main(String[] args) {

        String email1 = "MARCO.CARTA@GMAIL.COM";
        String email2 = "RICCARDOGIURA@GMAIL.COM";

        DogSitterProxy proxy = new DogSitterProxy(email1);




        //System.out.println(list.size());


        /*
        CustomerProxy proxy1 = new CustomerProxy(email2);
        HashMap<Integer, Assignment> list2 = proxy1.getAssignmentList();
        System.out.println((list2.size()));*/

/*
        GUIDogSitter guiDogSitter = null;
        try {
            guiDogSitter = new GUIDogSitter("MARCO.CARTA@GMAIL.COM");
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("Errore");
        }

        HashMap<Integer, Review> list = proxy.getReviewList();

        GUIShowDogsitterAssignment guiShowDogsitterAssignment = new GUIShowDogsitterAssignment(NORMAL, proxy.getAssignmentList(), email2, guiDogSitter);
        guiShowDogsitterAssignment.setVisible(true);*/



        GUIShowDogsitterReview guiShowDogsitterReview = new GUIShowDogsitterReview(proxy.getReview(1), email1);
        guiShowDogsitterReview.setVisible(true);

        //System.out.println(proxy.getCustomerNameOfAssignment(1));






    }
}
