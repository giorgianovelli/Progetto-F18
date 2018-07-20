package client.gui;

import client.proxy.CustomerProxy;
import server.Assignment;
import client.clientEnumerations.CalendarState;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import static server.tools.StringManipulator.capitalizeFirstLetter;

public class GUICustomer extends GUIHome{

    /**
     * The menu item "New assignment".
     */
    private JMenuItem menuItemAddAssignment = new JMenuItem("New assignment");

    /**
     * The menu item "Delete assignment".
     */
    private JMenuItem menuItemRemoveAssignment = new JMenuItem("Delete assignment");

    /**
     * The menu item "Write a review".
     */
    private JMenuItem menuItemAddReview = new JMenuItem("Write a review");

    /**
     * The menu item "Delete review".
     */
    private JMenuItem menuItemRemoveReview = new JMenuItem("Delete review");

    /**
     * The menu item "Dogs".
     */
    private JMenuItem menuItemDogs = new JMenuItem("Dogs");

    /**
     * The menu item "Cancel".
     */
    protected JMenuItem menuItemCancel = new JMenuItem("Cancel");

    /**
     * The form for creating a new assignment.
     */
    public static GUINewAssignment guiNewAssignment;

    /**
     * The customer proxy.
     */
    private CustomerProxy proxy;

    /**
     * The HashSet that contains the first five codes of today's assignments.
     */
    private HashSet<Integer> codeFirstFiveAssignmentsList = new HashSet<>();


    /**
     * Create a new home for the customer specified by the email.
     * @param email the customer's email address.
     * @throws ParseException
     */
    public GUICustomer(String email) throws ParseException {
        super(email);
        setTitle("CaniBau (Customer)");
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        this.email = email;
        this.proxy = new CustomerProxy(email);
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
                cancel();
            }
        };

        //prepara il calendario
        startCalendar(cal, ctrlCal, proxy);

        menuItemExit.addActionListener(menuAl);
        menuItemLogout.addActionListener(menuAl);
        menuItemAddAssignment.addActionListener(menuAl);
        menuItemCancel.addActionListener(menuAl);
        menuItemRemoveAssignment.addActionListener(menuAl);
        menuItemShowAssignments.addActionListener(menuAl);
        menuItemAddReview.addActionListener(menuAl);
        menuItemRemoveReview.addActionListener(menuAl);
        menuItemShowReviews.addActionListener(menuAl);
        menuItemAccount.addActionListener(menuAl);
        menuItemChangePassword.addActionListener(menuAl);
        menuItemInfo.addActionListener(menuAl);
        menuItemAwards.addActionListener(menuAl);
        menuItemDogs.addActionListener(menuAl);
        buttonShowMoreTodayAssignments.addActionListener(ctrlCal);

        int i;
        for (i = 0; i < nShownTodayAssignments; i++){
            buttonTodayAssignment[i].addActionListener(todayAssignmentsAl);
        }

    }


    /**
     * Put the home screen into adding mode. The day's button become green
     * and on the menu bar remain only "Canibau" and "Cancel items.
     */
    public void newAssignment(){
        try {
            enableDisableDateButtonAssignment();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendarState = CalendarState.ADDING;
        int i;
        for (i = 0; i < NDAYMONTH; i++){
            buttonDay[i].setBackground(new Color(179, 255, 179));
        }
        menuAssignment.setVisible(false);
        menuReview.setVisible(false);
        menuSettings.setVisible(false);
        menuExtra.setVisible(false);
        menuItemCancel.setVisible(true);
    }


    /**
     * When the user presses the "Cancel" button the calendar come back into normal mode.
     */
    public void cancel(){
        calendarState = CalendarState.NORMAL;
        int i;
        for (i = 0; i < NDAYMONTH; i++){
            buttonDay[i].setBackground(new Color(204, 230, 255));
            buttonDay[i].setEnabled(true);
        }
        menuAssignment.setVisible(true);
        menuReview.setVisible(true);
        menuSettings.setVisible(true);
        menuExtra.setVisible(true);
        menuItemCancel.setVisible(false);

        if (!(calendarState.equals(CalendarState.ADDING)) && !(calendarState.equals(CalendarState.REMOVING))){
            showAssignmentsOnCalendar(email, proxy);
        }
    }


    /**
     * Put the home screen into removing mode. The day's buttons become red
     * and on the menu bar remain only "Canibau" and "Cancel items.
     */
    public void removeAssignment(){
        try {
            enableDisableDateButtonAssignment();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendarState = CalendarState.REMOVING;
        int i;
        for (i = 0; i < NDAYMONTH; i++){
            buttonDay[i].setBackground(new Color(242, 82, 37));
        }
        menuAssignment.setVisible(false);
        menuReview.setVisible(false);
        menuSettings.setVisible(false);
        menuExtra.setVisible(false);
        menuItemCancel.setVisible(true);
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
            String nameDogSitter = proxy.getDogSitterNameOfAssignment(a.getCode());
            String surnameDogSitter = proxy.getDogSitterSurnameOfAssignment(a.getCode());
            buttonTodayAssignment[n].setText("Assignment with " + capitalizeFirstLetter(nameDogSitter) + " " + capitalizeFirstLetter(surnameDogSitter));
            buttonTodayAssignment[n].setDisplayedMnemonicIndex(key);
            n++;
        }
    }


    /**
     * Dispose the menu bar.
     */
    protected void disposeMenuBar(){
        menuBar.add(menuFile);
        menuFile.add(menuItemLogout);
        menuFile.add(menuItemExit);
        menuAssignment.add(menuItemAddAssignment);
        menuAssignment.add(menuItemRemoveAssignment);
        menuAssignment.add(menuItemShowAssignments);
        menuBar.add(menuAssignment);
        menuReview.add(menuItemAddReview);
        menuReview.add(menuItemRemoveReview);
        menuReview.add(menuItemShowReviews);
        menuBar.add(menuReview);
        menuSettings.add(menuItemAccount);
        menuSettings.add(menuItemChangePassword);
        menuSettings.add(menuItemDogs);
        menuBar.add(menuSettings);
        menuExtra.add(menuItemInfo);
        menuExtra.add(menuItemAwards);
        menuBar.add(menuItemCancel);
        menuItemCancel.setVisible(false);
        menuBar.add(menuExtra);
        add(menuBar, BorderLayout.NORTH);

    }


    /**
     * Perform an action when a day's button is pressed.
     * @param cae the day's button ActionEvent.
     */
    @Override
    protected void clickOnCalendarButton(ActionEvent cae) {
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

        if ((!(cae.getActionCommand().equals(""))) && ((calendarState.equals(CalendarState.NORMAL)) || (calendarState.equals(CalendarState.REMOVING)))){
            GUIDailyAssignments guiDailyAssignments = new GUIDailyAssignments(calendarState, email, todayDate);
            guiDailyAssignments.setVisible(true);
        }

        if ((!(cae.getActionCommand().equals(""))) && (calendarState.equals(CalendarState.ADDING))){
            //JButton pressedButton = (JButton) cae.getSource();
            guiNewAssignment = new GUINewAssignment(todayDate, email, this);
            guiNewAssignment.setVisible(true);
        }
    }


    /**
     * Open a window that shows all customer's assignments.
     */
    public void showAllAssignments(){
        openListAssignment(proxy);
    }


    /**
     * Open a window for selecting the assignment to be reviewed.
     */
    public void writeReview(){
        calendarState = CalendarState.REVIEWING;
        openListAssignment(proxy);
    }


    /**
     * Open a windows for selecting the review to be removed.
     */
    public void deleteReview(){
        calendarState = CalendarState.DELETING_REVIEW;
        openListAssignment(proxy);
    }


    /**
     * Open a window that shows all customer's reviews.
     */
    public void showAllReviews(){
        calendarState = CalendarState.SHOW_REVIEWS;
        openListAssignment(proxy);
    }


    /**
     * Open the account settings.
     */
    public void accountSettings(){
        GUISettings guiSettings = new GUISettings(email);
        guiSettings.setVisible(true);
    }


    /**
     * Open a window that permits to the customer to change his password.
     */
    public void changePassword(){
        GUIChangePassword guiChangePassword = new GUIChangePassword(email);
        guiChangePassword.setVisible(true);
    }


    /**
     * Open the windows for changing dogs' informations.
     */
    public void dogsSettings(){
        GUIDogs guiDogs = new GUIDogs(email);
        guiDogs.setVisible(true);
    }


    /**
     * Call clickOnTodayAssignment when the user clicks on a today's assignment.
     * @param todayAssignmentAe the ActionEvent of today's assignments.
     */
    protected void callClickOnTodayAssignment(ActionEvent todayAssignmentAe){
        clickOnTodayAssignment(todayAssignmentAe, proxy, this);
    }
}
