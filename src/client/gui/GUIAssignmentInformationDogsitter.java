package client.gui;

import server.Assignment;

public class GUIAssignmentInformationDogsitter extends GUIAssignmentInformationCustomer{

    /**
     * Constuctor of the class
     * @param assignment
     * @param email
     * @param guiDogSitter
     */
    public GUIAssignmentInformationDogsitter(Assignment assignment, String email, GUIDogSitter guiDogSitter)  {
        super(assignment, email, guiDogSitter);


        initComponents();
    }


    /**
     * Method that initalizes graphic components of the GUI
     */
    public void initComponents() {

        getLabelDogsitter1().setText("Customer: ");
        getLabelDogsitter2().setText(getCustomerProxy().getName() + " " + getCustomerProxy().getSurname());

        if (!getLabelPaymentMethod2().getText().equals("Cash"))
        getLabelPaymentMethod2().setText("Credit card");


    }



}
