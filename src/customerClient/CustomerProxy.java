package customerClient;

import interfaces.InterfaceCustomer;
import server.Assignment;
import server.Dog;
import server.places.Address;

import java.io.*;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CustomerProxy implements InterfaceCustomer {
    private BufferedReader msgIn = null;
    private PrintStream msgOut = null;
    private Socket socket = null;
    private String serverReply;

    public String getReply(String clientMsg){
        try{
            // open a socket connection
            socket = new Socket("127.0.0.1", 4000); //4000 customer e 4001 dog sitter
            // Apre i canali I/O
            msgIn = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            msgOut = new PrintStream(socket.getOutputStream(), true);
            msgOut.println(clientMsg);
            msgOut.flush();
            // Legge dal server
            serverReply = msgIn.readLine();
            System.out.println("Received message from server: " + serverReply);
            msgOut.close();
            msgIn.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            return serverReply;
        }
    }

    public boolean customerAccessDataVerifier(String inputUser, String inputPasword){
        String clientMsg = "0#" + inputUser + "#" + inputPasword;
        if (getReply(clientMsg).equals("true")){
            return true;
        } else {
            return false;
        }
    }

    public HashMap<Integer, Assignment> getCustomerListAssignment(String email){
        String serverMsg = getReply("1#" + email);
        StringTokenizer tokenMsg = new StringTokenizer(serverMsg, "#");
        //msg = msg + a.getCode() + "#" + "a.doglist" + a.getDateStart() + "#" + a.getDateEnd() + "#" + a.getState() + "#" + "a.getMeetingPoint";
        HashMap<Integer, Assignment> customerListAssignment = new HashMap<Integer, Assignment>();
        while (tokenMsg.hasMoreTokens()){
            int code = Integer.parseInt(tokenMsg.nextToken());
            HashSet<Dog> dogList = null;    //TODO
            tokenMsg.nextToken();           //...
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy HH:mm");
            Date dateStart = new Date();
            Date dateEnd = new Date();
            try {
                dateStart = dateFormat.parse(tokenMsg.nextToken());
                dateEnd = dateFormat.parse(tokenMsg.nextToken());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            tokenMsg.nextToken();   //TODO state
            Address meetingPoint = null;    //TODO
            tokenMsg.nextToken();           //...
            Assignment a = new Assignment(code, dogList, dateStart, dateEnd, meetingPoint);
            customerListAssignment.put(code, a);
        }
        return customerListAssignment;
    }
}