package server;
import database.DBConnector;
import interfaces.InterfaceCustomer;
import server.places.Address;

import java.awt.desktop.SystemSleepEvent;
import java.io.*;
import java.net.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import static server.tools.StringManipulator.capitalizeFirstLetter;

public class ServerProxy extends Thread
{
    private ServerSocket Server;

    public static void main(String[] args) {
        try {
            new ServerProxy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ServerProxy() throws Exception{
        Server = new ServerSocket(4000);
        System.out.println("The Server is waiting on port 4000...");
        this.start();
    }

    public void run(){
        while(true) {
            try{
                System.out.println("Waiting for connection...");
                Socket client = Server.accept();
                System.out.println("Connection accepted by: " +
                        client.getInetAddress());
                Connect c = new Connect(client);
            }
            catch(Exception e) {}
        }
    }
}

class Connect extends Thread {
    private Socket client = null;
    private BufferedReader msgIn = null;
    private PrintStream msgOut = null;

    public Connect(Socket clientSocket){
        client = clientSocket;
        try{
            msgIn = new BufferedReader(
                    new InputStreamReader(client.getInputStream()));
            msgOut = new PrintStream(client.getOutputStream(), true);
        }
        catch(Exception e1){
            try {
                client.close();
            }
            catch(Exception e) { System.out.println(e.getMessage());}
            return;
        }
        this.start();
    }

    public void run(){
        try{
            msgOut.println(executeClientCmd());
            msgOut.flush();
            // chiude gli stream e le connessioni
            msgOut.close();
            msgIn.close();
            client.close();
        }
        catch(Exception e) {

        }
    }

    private String executeClientCmd(){
        String serverMsg = null;
        int code;
        try {
            StringTokenizer tokenMsg = null;
            try {
                tokenMsg = new StringTokenizer(msgIn.readLine(), "#");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            int action = Integer.parseInt(tokenMsg.nextToken());
            switch (action){
                case 0:
                    String inputUser = tokenMsg.nextToken();
                    String inputPassword = tokenMsg.nextToken();
                    serverMsg = customerAccessDataVerifier(inputUser, inputPassword);
                    break;
                case 1:
                    String email = tokenMsg.nextToken();
                    serverMsg = getCustomerListAssignment(email);
                    break;
                case 2:
                    code = Integer.parseInt(tokenMsg.nextToken());
                    serverMsg = getDogSitterNameOfAssignment(code);
                    break;
                case 3:
                    code = Integer.parseInt(tokenMsg.nextToken());
                    serverMsg = getDogSitterSurnameOfAssignment(code);
                    break;
                case 4:
                    code = Integer.parseInt(tokenMsg.nextToken());
                    serverMsg = getReview(code);
                    break;
                default:
            }
        } finally {
            return serverMsg;
        }
    }

    private String customerAccessDataVerifier(String inputUser, String inputPasword){
        Login loginCustomer = new Login();
        try {
            if(loginCustomer.customerAccessDataVerifier(inputUser, inputPasword)){
                return "true";
            } else{
                return "false";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "false";
        }
    }

    private String getCustomerListAssignment(String email){
       //TODO
        Singleton singleton = new Singleton();
        HashMap<Integer, Assignment> customerListAssignment = singleton.getCustomerListAssignmentFromDB(email);
        String msg = "";
        for (Integer key : customerListAssignment.keySet()) {
            Assignment a = customerListAssignment.get(key);
            //TODO aggiungere chiamata per ottenere la lista dei cani e il meeting point di a
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String strDateStart = dateFormat.format(a.getDateStart());
            String strDateEnd = dateFormat.format(a.getDateEnd());
            msg = msg + a.getCode() + "#" + getDogListOfAssignment(a.getCode()) + "#" + strDateStart + "#" + strDateEnd + "#" + a.getState() + "#" + getMeetingPoint(a.getCode()) + "#";
        }
        return msg;
    }

    private String getDogSitterNameOfAssignment(int code){
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
        return dogSitter.name;
    }

    private String getDogSitterSurnameOfAssignment(int code){
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
        return dogSitter.surname;
    }

    private String getMeetingPoint(int code){
        Singleton singleton = new Singleton();
        Address meetingPoint = singleton.getMeetingPointFromDB(code);
        return meetingPoint.getCountry() + "*" + meetingPoint.getCity() + "*" + meetingPoint.getStreet() + "*"
                + meetingPoint.getNumber() + "*" + meetingPoint.getCap();
    }

    private String getDogListOfAssignment(int code){
        String msg = "";
        Singleton singleton = new Singleton();
        HashSet<Dog> dogList = singleton.getDogListFromDB(code);
        for (Dog d : dogList) {
            msg = msg + d.getID() + "&" + d.getName() + "&" + d.getBreed() + "&" + d.getSize() + "&" + d.getAge() + "&"
                        + d.getWeight() + "*";
        }
        return msg;
    }

    private String getReview(int code){
        Singleton singleton = new Singleton();
        Review review = singleton.getReview(code);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String date = dateFormat.format(review.getDate());
        //TODO
        return  date + "#" + review.getRating() + "#" +review.getTitle() + "#" + review.getComment() + "#" + review.getReply();
    }
}