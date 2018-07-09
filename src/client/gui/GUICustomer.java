package client.gui;

import client.clientEnumerations.MenuBarAction;
import client.proxy.CustomerProxy;
import server.Assignment;
import enumeration.CalendarState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

import static server.tools.StringManipulator.capitalizeFirstLetter;

public class GUICustomer extends GUIHome{
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
    private JMenuItem menuItemAddAssignment = new JMenuItem("New assignment");
    private JMenuItem menuItemRemoveAssignment = new JMenuItem("Delete assignment");
    private JMenuItem menuItemAddReview = new JMenuItem("Write a review");
    private JMenuItem menuItemRemoveReview = new JMenuItem("Delete review");
    private JMenuItem menuItemDogs = new JMenuItem("Dogs");
    protected JMenuItem menuItemCancel = new JMenuItem("Cancel");

    public static GUINewAssignment guiNewAssignment;

    private CustomerProxy proxy;
    private String email;
    private HashSet<Integer> codeFirstFiveAssignmentsList = new HashSet<>();


    public GUICustomer(String email) throws ParseException {
        super(email);
        setTitle("CaniBau (Customer)");
        //setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        this.email = email;
        this.proxy = new CustomerProxy(email);

        initComponents();
    }

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
                clickOnMenuBarButton(menuAe);
            }
        };

        ActionListener todayAssignmentsAl = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent todayAssignmentAe) {
                if (!(todayAssignmentAe.getActionCommand().equals(""))){
                    JButton pressedButton = (JButton) todayAssignmentAe.getSource();
                    StringTokenizer cmdToken = new StringTokenizer(buttonTodayAssignment[0].getText(), " ");
                    String cmd = cmdToken.nextToken();
                    if (cmd.equals("Assignment")){
                        calendarState = CalendarState.NORMAL;
                        cancel();
                        SimpleDateFormat dateMonth = new SimpleDateFormat("M");
                        Date date = new Date();
                        String strMonthNumber = dateMonth.format(date);
                        int monthNumber = Integer.parseInt(strMonthNumber);
                        try {
                            updateCalendar(monthNumber, proxy);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        HashMap<Integer, Assignment> listAssignment = proxy.getAssignmentList();
                        Assignment a = listAssignment.get(pressedButton.getDisplayedMnemonicIndex());
                        GUIAssignmentInformationCustomer guiAssignment = new GUIAssignmentInformationCustomer(a, email);
                        guiAssignment.setVisible(true);
                    }
                }
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
            showAssignmentOnCalendar(email, proxy);
        }
    }

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

    protected void loadTheFirstFiveAssignments(int nShownAssignments){
        if (nShownAssignments > MAXVISIBLETODAYASSIGNMENT){
            nShownAssignments = MAXVISIBLETODAYASSIGNMENT;
        }

        HashMap<Integer, Assignment> assignmentList = proxy.getAssignmentList();
        codeFirstFiveAssignmentsList = getCodeFirstFiveAssignments(nShownAssignments, assignmentList);

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

    protected void disposeMenuBar(){
        //Crea il menù
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

    public void showAllAssignments(){
        openListAssignment(proxy);
    }

    public void writeReview(){
        calendarState = CalendarState.REVIEWING;
        openListAssignment(proxy);
    }

    public void deleteReview(){
        calendarState = CalendarState.DELETING_REVIEW;
        openListAssignment(proxy);
    }

    public void showAllReviews(){
        calendarState = CalendarState.SHOW_REVIEWS;
        openListAssignment(proxy);
    }

    public void accountSettings(){
        GUISettings guiSettings = new GUISettings(email);
        guiSettings.setVisible(true);
    }

    public void changePassword(){
        GUIChangePassword guiChangePassword = new GUIChangePassword(email);
        guiChangePassword.setVisible(true);
    }

    public void dogsSettings(){
        GUIDogs guiDogs = new GUIDogs(email);
        guiDogs.setVisible(true);
    }
}
