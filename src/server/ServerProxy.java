package server;
import database.DBConnector;
import enumeration.ExecCustomerEnum;
import enumeration.ExecDogSitterEnum;
import enumeration.TypeUser;
import server.bank.PaymentMethod;
import server.places.Address;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

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
        StringTokenizer tokenMsg = null;
        try {
            tokenMsg = new StringTokenizer(msgIn.readLine(), "#");
        } catch (IOException e) {
            e.printStackTrace();
            return "Error in executing client message!";
        }

        String strExec = "";

        TypeUser userType = TypeUser.valueOf(tokenMsg.nextToken());
        switch (userType){
            case CUSTOMER:
                ExecCustomerEnum exeCustomer = ExecCustomerEnum.valueOf(tokenMsg.nextToken());
                while (tokenMsg.hasMoreTokens()){
                    strExec = strExec + "#" + tokenMsg.nextToken();
                }
                serverMsg = exeCustomer.execute(strExec);
                break;
            case DOGSITTER:
                ExecDogSitterEnum exeDogSitter = ExecDogSitterEnum.valueOf(tokenMsg.nextToken());
                while (tokenMsg.hasMoreTokens()){
                    strExec = strExec + "#" + tokenMsg.nextToken();
                }
                serverMsg = exeDogSitter.execute(strExec);
                break;
        }
            return serverMsg;
    }

    //TODO metodo da analizzare...
    /*private String getDogSitterListAssignment(String email){
        Singleton singleton = new Singleton();
        HashMap<Integer, Assignment> dogSitterListAssignment = singleton.getDogSitterListAssignmentFromDB(email);
        String msg = "";
        for (Integer key : dogSitterListAssignment.keySet()) {
            Assignment a = dogSitterListAssignment.get(key);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String strDateStart = dateFormat.format(a.getDateStart());
            String strDateEnd = dateFormat.format(a.getDateEnd());
            msg = msg + a.getCode() + "#" + getDogListOfAssignment(a.getCode()) + "#" + strDateStart + "#" + strDateEnd + "#" + a.getState() + "#" + getMeetingPoint(a.getCode()) + "#";
        }
        return msg;
    }*/

}