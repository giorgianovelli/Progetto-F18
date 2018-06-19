package enumeration;

import server.Assignment;
import server.Login;
import server.Singleton;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.StringTokenizer;

import static enumeration.enumStaticMethods.getDogListOfAssignment;
import static enumeration.enumStaticMethods.getListAssignment;
import static enumeration.enumStaticMethods.getMeetingPoint;

public enum ExecDogSitterEnum {
    ACCESSVERIFIER{

        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String inputUser = tokenMsg.nextToken();
            String inputPassword = tokenMsg.nextToken();

            Login loginDogSitter = new Login();
            try {
                if(loginDogSitter.dogSitterAccessDataVerifier(inputUser, inputPassword)){
                    return "true";
                } else{
                    return "false";
                }

            } catch (SQLException e) {
                e.printStackTrace();
                return "false";
            }
        }

    },

    GETLISTASSIGNMENT{

        public String execute(String clientMsg) {
            StringTokenizer tokenMsg = new StringTokenizer(clientMsg, "#");
            String email = tokenMsg.nextToken();

            Singleton singleton = new Singleton();
            HashMap<Integer, Assignment> dogSitterListAssignment = singleton.getDogSitterListAssignmentFromDB(email);
            return getListAssignment(dogSitterListAssignment);
        }

    };


    public abstract String execute(String clientMsg);
}
