/**
 * This is the server
 */

package server;
import enumeration.ExecCustomerEnum;
import enumeration.ExecDogSitterEnum;
import enumeration.TypeUser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 * This class provides the server connection.
 */
public class ServerProxy extends Thread {

    /**
     * The server's socket.
     */
    private ServerSocket Server;


    /**
     * Launch the server.
     * @param args
     */
    public static void main(String[] args) {
        try {
            new ServerProxy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Create a new Server object.
     * @throws Exception
     */
    public ServerProxy() throws Exception{
        Server = new ServerSocket(4000);
        System.out.println("The Server is waiting on port 4000...");
        this.start();
    }


    /**
     * Run a new server's thread.
     */
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




/**
 * This class establish a new client-server connection.
 */
class Connect extends Thread {

    /**
     * The client's socket.
     */
    private Socket client = null;

    /**
     * The buffer that receives the message sended by the client.
     */
    private BufferedReader msgIn = null;

    /**
     * The message to be sended to the client.
     */
    private PrintStream msgOut = null;


    /**
     * Create a new connection with a new client.
     * @param clientSocket the client's socket.
     */
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


    /**
     * Establish a new client-server connection.
     */
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
            System.err.println("Error in client-server connection!");
        }
    }


    /**
     * Execute the client's request.
     * @return a message to the client.
     */
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

}