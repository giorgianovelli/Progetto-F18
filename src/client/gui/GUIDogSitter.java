package client.gui;

import client.proxy.DogSitterProxy;
import client.clientEnumerations.CalendarState;
import server.Assignment;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import static server.tools.StringManipulator.capitalizeFirstLetter;

/**
 * This class show all the components
 * that a DogSitter has and extend the class GUIHome.
 */
public class GUIDogSitter extends GUIHome{
    /**
     * The dog sitter proxy.
     */
    private DogSitterProxy proxy;



    /**
     * Create a new home screen for the dog sitter specified by the email address.
     * @param email the dog sitter's email address.
     * @throws ParseException
     */
    public GUIDogSitter(String email) throws ParseException  {
        super(email);
        setTitle("CaniBau (Dog sitter)");
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        this.email = email;
        this.proxy = new DogSitterProxy(email);

        initComponents();
    }


    /**
     * Dispose the graphical object onto the home screen.
     * @throws ParseException
     */
    private void initComponents() throws ParseException {
        disposeMenuBar();
        int nShownTodayAssignments = disposeTodayPanel(proxy);


        ActionListener cal = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent cae) {
                clickOnCalendarButton(cae);
            }
        };

        ActionListener ctrlCal = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ctrlAe) {
                clickOnCtrlCalendarButton(ctrlAe, proxy);
            }
        };

        ActionListener menuAl = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent menuAe) {
                clickOnMenuBarItem(menuAe);
            }
        };

        ActionListener todayAssignmentsAl = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent todayAssignmentAe) {
                callClickOnTodayAssignment(todayAssignmentAe);
            }
        };

        startCalendar(cal, ctrlCal, proxy);

        menuItemExit.addActionListener(menuAl);
        menuItemLogout.addActionListener(menuAl);
        menuItemShowAssignments.addActionListener(menuAl);
        menuItemShowReviews.addActionListener(menuAl);
        menuItemAccount.addActionListener(menuAl);
        menuItemChangePassword.addActionListener(menuAl);
        menuItemInfo.addActionListener(menuAl);
        menuItemAwards.addActionListener(menuAl);
        buttonShowMoreTodayAssignments.addActionListener(ctrlCal);

        int i;
        for (i = 0; i < nShownTodayAssignments; i++){
            buttonTodayAssignment[i].addActionListener(todayAssignmentsAl);
        }

    }


    /**
     * Load in an HashMap the first five today's assignments to be shown.
     * @param nShownTodayAssignments the number of today's assignments to be shown.
     */
    protected void loadTheFirstFiveAssignments(int nShownTodayAssignments){
        if (nShownTodayAssignments > MAXVISIBLETODAYASSIGNMENT){
            nShownTodayAssignments = MAXVISIBLETODAYASSIGNMENT;
        }

        HashMap<Integer, Assignment> assignmentList = proxy.getAssignmentList();
        codeFirstFiveAssignmentsList = getCodeFirstFiveAssignments(nShownTodayAssignments, assignmentList);

        int n = 0;
        for (Integer key : codeFirstFiveAssignmentsList) {
            Assignment a = assignmentList.get(key);
            String nameDogSitter = proxy.getCustomerNameOfAssignment(a.getCode());
            String surnameDogSitter = proxy.getCustomerSurnameOfAssignment(a.getCode());
            buttonTodayAssignment[n].setText("Assignment with " + capitalizeFirstLetter(nameDogSitter) + " " + capitalizeFirstLetter(surnameDogSitter));
            buttonTodayAssignment[n].setDisplayedMnemonicIndex(key);
            n++;
        }
    }


    /**
     * Dispose the menu bar.
     */
    protected void disposeMenuBar(){
        //Crea il menù
        menuBar.add(menuFile);
        menuFile.add(menuItemLogout);
        menuFile.add(menuItemExit);
        menuAssignment.add(menuItemShowAssignments);
        menuBar.add(menuAssignment);
        menuReview.add(menuItemShowReviews);
        menuBar.add(menuReview);
        menuSettings.add(menuItemAccount);
        menuSettings.add(menuItemChangePassword);
        menuBar.add(menuSettings);
        menuExtra.add(menuItemInfo);
        menuExtra.add(menuItemAwards);
        menuBar.add(menuExtra);
        add(menuBar, BorderLayout.NORTH);
    }


    /**
     * Perform an action when a day's button is pressed.
     * @param cae the day's button ActionEvent.
     */
    protected void clickOnCalendarButton(ActionEvent cae){
        JButton pressedButton = (JButton) cae.getSource();
        String strTodayDate;
        if (Integer.parseInt(pressedButton.getText()) < 10){
            strTodayDate =  "0" + pressedButton.getText() + "/" + labelDateMonthYear.getText();
        } else {
            strTodayDate = pressedButton.getText() + "/" + labelDateMonthYear.getText();
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        try {
            todayDate = dateFormat.parse(strTodayDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if ((!(cae.getActionCommand().equals(""))) &&  ((calendarState.equals(CalendarState.NORMAL)))){
            GUIDailyAssignmentDogsitter guiDailyAssignmentDogsitter = new GUIDailyAssignmentDogsitter(calendarState, email, todayDate, guiHome);
            guiDailyAssignmentDogsitter.setVisible(true);
        }

    }


    /**
     * Open a window that shows all dog sitter's assignments.
     */
    public void showAllAssignments(){
        GUIShowDogsitterAssignment guiShowDogsitterAssignment = new GUIShowDogsitterAssignment(CalendarState.NORMAL, proxy.getAssignmentList(), email, this);
        guiShowDogsitterAssignment.setVisible(true);

    }


    /**
     * Open a window that shows all dog sitter's reviews.
     */
    public void showAllReviews(){
        GUIShowDogsitterAssignment guiShowDogsitterAssignment = new GUIShowDogsitterAssignment(CalendarState.SHOW_REVIEWS, proxy.getAssignmentList(), email, this);
        guiShowDogsitterAssignment.setVisible(true);

    }


    /**
     * Open the account settings.
     */
    public void accountSettings(){
        //TODO da implementare
        //GUIDogSitterSettings guiDogSitterSettings = new GUIDogSitterSettings(email);
        //guiDogSitterSettings.setVisible(true);
    }


    /**
     * Open a window that permits to the dog sitter to change his password.
     */
    public void changePassword(){
        GUIDogSitterChangePassword guiDogSitterChangePassword = new GUIDogSitterChangePassword(email, guiHome);
        guiDogSitterChangePassword.setVisible(true);
    }


    /**
     * Call clickOnTodayAssignment when the dog sitter clicks on a today's assignment.
     * @param todayAssignmentAe the ActionEvent of today's assignments.
     */
    protected void callClickOnTodayAssignment(ActionEvent todayAssignmentAe){
        clickOnTodayAssignment(todayAssignmentAe, proxy, this);
    }

}
