/**
 * This abstract class provides main methods for performing a comunication between client and server
 * translating objects in messages to send to the server and traslating server's messages in objects.
 */

package client.proxy;

import server.Assignment;
import server.Dog;
import server.DogSize;
import server.bank.PaymentMethod;
import server.places.Address;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

public abstract class Proxy {
    /**
     * The buffer containing message received from the server.
     */
    protected BufferedReader msgIn = null;

    /**
     * The message to send to the server.
     */
    protected PrintStream msgOut = null;

    /**
     * The socket used for comunicating with the server.
     */
    protected Socket socket = null;

    /**
     * The message received from the server.
     */
    protected String serverReply;

    /**
     * Return the server reply based on the message sended by the client.
     * @param clientMsg
     * @return the message sended by the server
     */
    protected String getReply(String clientMsg) {
        try {
            socket = new Socket("127.0.0.1", 4000); //4000 customer e 4001 dog sitter
            msgIn = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            msgOut = new PrintStream(socket.getOutputStream(), true);
            msgOut.println(clientMsg);
            msgOut.flush();
            serverReply = msgIn.readLine();
            System.out.println("Received message from server: " + serverReply);
            msgOut.close();
            msgIn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            return serverReply;
        }
    }


    /**
     * Decode a String that corresponding to a meeting point.
     * @param msg (or fragment of a message) received form the server.
     * @return the meeting point.
     */
    protected Address decodeMeetingPoint(String msg) {
        StringTokenizer tokenMsg = new StringTokenizer(msg, "*");
        String country = tokenMsg.nextToken();
        String city = tokenMsg.nextToken();
        String street = tokenMsg.nextToken();
        String number = tokenMsg.nextToken();
        String cap = tokenMsg.nextToken();
        return new Address(country, city, street, number, cap);
    }


    /**
     * Decode a String that corresponding to an address.
     * @param msg (or fragment of a message) received form the server.
     * @return an object of type Address.
     */
    protected Address decodeAddress(String msg) {
        StringTokenizer tokenMsg = new StringTokenizer(msg, "#");
        String country = tokenMsg.nextToken();
        String city = tokenMsg.nextToken();
        String street = tokenMsg.nextToken();
        String number = tokenMsg.nextToken();
        String cap = tokenMsg.nextToken();
        return new Address(country, city, street, number, cap);
    }


    /**
     * Decode a String that corresponding to a payment method.
     * @param msg (or fragment of a message) received form the server.
     * @return the payment method.
     */
    protected PaymentMethod decodePaymentMethod(String msg) {
        StringTokenizer tokenMsg = new StringTokenizer(msg, "*");
        String number = tokenMsg.nextToken();
        String name = tokenMsg.nextToken();
        String surname = tokenMsg.nextToken();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date expirationDate = new Date();
        try {
            expirationDate = dateFormat.parse(tokenMsg.nextToken());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String cvv = tokenMsg.nextToken();
        double amount = Double.parseDouble(tokenMsg.nextToken());
        return new PaymentMethod(number, name, surname, expirationDate, cvv, amount);
    }

    public abstract HashMap<Integer, Assignment> getAssignmentList();

    protected HashMap<Integer, Assignment> decodeAssignmentList(String serverMsg){
        StringTokenizer tokenMsg = new StringTokenizer(serverMsg, "#");
        HashMap<Integer, Assignment> assignmentList = new HashMap<>();
        while (tokenMsg.hasMoreTokens()) {
            int code = Integer.parseInt(tokenMsg.nextToken());
            HashSet<Dog> dogList = decodeDogList(tokenMsg.nextToken());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date dateStart = new Date();
            Date dateEnd = new Date();
            try {
                dateStart = dateFormat.parse(tokenMsg.nextToken());
                dateEnd = dateFormat.parse(tokenMsg.nextToken());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Boolean state;
            String strState = tokenMsg.nextToken();
            if (strState.equals("true")) {
                state = true;
            } else if (strState.equals("false")) {
                state = false;
            } else {
                state = null;
            }
            Address meetingPoint = decodeMeetingPoint(tokenMsg.nextToken());
            Assignment a = new Assignment(code, dogList, dateStart, dateEnd, state, meetingPoint);
            assignmentList.put(code, a);
        }

        return assignmentList;
    }


    /**
     * Decode the list of dogs.
     * @param msg the fragment of the message received from the server containing dogs' ID.
     * @return the HashSet of Dogs.
     */
    protected HashSet<Dog> decodeDogList(String msg) {
        StringTokenizer tokenMsg = new StringTokenizer(msg, "*");
        HashSet<Dog> dogList = new HashSet<>();
        int ID;
        while (tokenMsg.hasMoreTokens()) {
            StringTokenizer tokenDog = new StringTokenizer(tokenMsg.nextToken(), "&");
            String strID = tokenDog.nextToken();
            ID = Integer.parseInt(strID);
            String name = tokenDog.nextToken();
            String breed = tokenDog.nextToken();
            DogSize size = DogSize.valueOf(tokenDog.nextToken());
            int age = Integer.parseInt(tokenDog.nextToken());
            double weight = Double.parseDouble(tokenDog.nextToken());
            boolean isEnabled;
            if (tokenDog.nextToken().equals("true")){
                isEnabled = true;
            } else {
                isEnabled = false;
            }
            Dog d = new Dog(name, breed, size, age, weight, ID, isEnabled);
            dogList.add(d);
        }
        return dogList;
    }
}
