package client.gui;

import server.Assignment;

public class GUIAssignmentInformationDogsitter extends GUIAssignmentInformationCustomer{


    private GUIDogSitter guiDogSitter;

    public GUIAssignmentInformationDogsitter(Assignment assignment, String email, GUIDogSitter guiDogSitter)  {
        super(assignment, email, guiDogSitter);


        initComponents();
    }


    public void initComponents() {

        getLabelDogsitter1().setText("Customer: ");
        getLabelDogsitter2().setText(getCustomerProxy().getName() + " " + getCustomerProxy().getSurname());

        if (!getLabelPaymentMethod2().getText().equals("Cash"))
        getLabelPaymentMethod2().setText("Credit card");


    }



}
