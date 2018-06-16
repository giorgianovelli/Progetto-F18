package enumeration;

import server.Login;
import java.sql.SQLException;
import java.util.StringTokenizer;

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

    };


    public abstract String execute(String clientMsg);
}
