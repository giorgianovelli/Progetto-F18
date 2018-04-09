public class Login {
    private String user = new String();
    private String password = new String();
    //private String inputUser = new String();
    //private String inputPassword = new String();

    public Login(String inputUser, String inputPassword){
        getAccessData();
        if(accessDataVerifier(inputUser, inputPassword)){
            System.out.println("Allowed access");
        } else{
            System.out.println("Denied access");
        }
    }

    public void getAccessData(){
        //get access data from database

        //temporary submethod
        user = "user";
        password = "password";
    }

    public boolean accessDataVerifier(String inputUser, String inputPasword){
        if ((inputUser.equals(user)) && (inputPasword.equals(password))){
            return true;
        } else{
            return false;
        }
    }
}
