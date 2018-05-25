package customerClient;

import java.io.*;
import java.net.*;
import java.util.*;

public class CustomerProxy{
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
            System.out.print("Received message: " + serverReply);
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
}