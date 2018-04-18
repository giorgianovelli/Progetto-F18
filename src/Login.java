public class Login {
    private String user = new String();
    private String password = new String();
    private TypeUser typeUser;

    public Login(){
        getAccessData();
    }

    private void getAccessData(){
        //get access data from database

        //temporary method
        user = "user";
        password = "password";
        //typeUser = TypeUser.DOGSITTER;
        typeUser = TypeUser.CUSTOMER;
    }

    public boolean accessDataVerifier(String inputUser, String inputPasword){

        //login automatico per velocizzare i test sulla GUI
        inputUser = "user";
        inputPasword = "password";

        if ((inputUser.equals(user)) && (inputPasword.equals(password))){
            System.out.println("Access allowed!");
            return true;
        } else{
            System.out.println("Access denied!");
            return false;
        }
    }

    public TypeUser getTypeUser() {
        return typeUser;
    }
}
