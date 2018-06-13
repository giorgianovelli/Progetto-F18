package dogSitterClient;

import server.Assignment;
import server.Dog;
import server.DogSize;
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

public class DogSitterProxy {
    private BufferedReader msgIn = null;
    private PrintStream msgOut = null;
    private Socket socket = null;
    private String serverReply;
    private String email;

    public DogSitterProxy(String email) {
        this.email = email;
    }

    public DogSitterProxy() {
        this.email = null;
    }

    private String getReply(String clientMsg) {
        try {
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            return serverReply;
        }
    }

    public boolean dogSitterAccessDataVerifier(String inputUser, String inputPasword) {
        String clientMsg = "100#" + inputUser + "#" + inputPasword;
        if (getReply(clientMsg).equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    /*public HashMap<Integer, Assignment> getDogSitterListAssignment() {
        String serverMsg = getReply("101#" + email);
        StringTokenizer tokenMsg = new StringTokenizer(serverMsg, "#");
        HashMap<Integer, Assignment> dogSitterListAssignment = new HashMap<Integer, Assignment>();
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
            boolean state;
            if (tokenMsg.nextToken().equals("true")) {
                state = true;
            } else if  {
                state = false;
            }
            Address meetingPoint = decodeMeetingPoint(tokenMsg.nextToken());
            Assignment a = new Assignment(code, dogList, dateStart, dateEnd, state, meetingPoint);
            dogSitterListAssignment.put(code, a);
        }
        return dogSitterListAssignment;
    }*/

    private HashSet<Dog> decodeDogList(String msg) {
        StringTokenizer tokenMsg = new StringTokenizer(msg, "*");
        HashSet<Dog> dogList = new HashSet<Dog>();
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

    private Address decodeMeetingPoint(String msg) {
        StringTokenizer tokenMsg = new StringTokenizer(msg, "*");
        String country = tokenMsg.nextToken();
        String city = tokenMsg.nextToken();
        String street = tokenMsg.nextToken();
        String number = tokenMsg.nextToken();
        String cap = tokenMsg.nextToken();
        return new Address(country, city, street, number, cap);
    }
}
