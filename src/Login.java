public class Login {
    private String user = new String();
    private String password = new String();

    public Login(String inputUser, String inputPassword){
        getAccessData();
        if(accessDataVerifier(inputUser, inputPassword)){
            System.out.println("Access allowed!");
        } else{
            System.out.println("Access denied!");
        }
    }

    private void getAccessData(){
        //get access data from database

        //temporary method
        user = "user";
        password = "password";
    }

    private boolean accessDataVerifier(String inputUser, String inputPasword){
        if ((inputUser.equals(user)) && (inputPasword.equals(password))){
            return true;
        } else{
            return false;
        }
    }
}
