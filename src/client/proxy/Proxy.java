package client.proxy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Proxy {
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
}
