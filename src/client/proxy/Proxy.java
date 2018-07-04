/**
 * This abstract class provides main methods for performing a comunication between client and server
 * translating objects in messages to send to the server and traslating server's messages in objects.
 */

package client.proxy;

import server.bank.PaymentMethod;
import server.places.Address;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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


    protected Address decodeMeetingPoint(String msg) {
        StringTokenizer tokenMsg = new StringTokenizer(msg, "*");
        String country = tokenMsg.nextToken();
        String city = tokenMsg.nextToken();
        String street = tokenMsg.nextToken();
        String number = tokenMsg.nextToken();
        String cap = tokenMsg.nextToken();
        return new Address(country, city, street, number, cap);
    }

    protected Address decodeAddress(String msg) {
        StringTokenizer tokenMsg = new StringTokenizer(msg, "#");
        String country = tokenMsg.nextToken();
        String city = tokenMsg.nextToken();
        String street = tokenMsg.nextToken();
        String number = tokenMsg.nextToken();
        String cap = tokenMsg.nextToken();
        return new Address(country, city, street, number, cap);
    }

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
}
