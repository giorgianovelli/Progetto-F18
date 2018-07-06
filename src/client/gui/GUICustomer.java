package client.gui;

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
    private HashSet<Integer> codeFirstFiveAssignmentsList = new HashSet<Integer>();


    public GUICustomer(String email) throws ParseException {
        super(email);
        setTitle("CaniBau (Customer)");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        this.email = email;
        this.proxy = new CustomerProxy(email);

        initComponents();
    }

    private void initComponents() throws ParseException {
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

        panelToday.setLayout(new GridLayout(7, 1, 5, 5));
        panelToday.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        labelTodayAssignments.setForeground(new Color(0,0, 255));
        labelTodayAssignments.setFont(new Font("Serif", Font.BOLD, 24));
        panelToday.add(labelTodayAssignments);
        int i;

        //restituisce il numero di appuntamenti del giorno
        nTodayAssignments = getNDailyAssignments(proxy);

        int nShownTodayAssignments = disposeTodayAssignmentList();

        //carica i primi 5 appuntamenti del giorno
        loadTheFirstFiveAssignments(nShownTodayAssignments);


        add(panelToday, BorderLayout.EAST);

        ActionListener cal = new ActionListener() {
            ;

            @Override
            public void actionPerformed(ActionEvent cae) {
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
                    guiNewAssignment = new GUINewAssignment(todayDate, email);
                    guiNewAssignment.setVisible(true);
                }

            }
        };

        ActionListener ctrlCal = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ctrlAe) {

                if (!(ctrlAe.getActionCommand().equals(""))){
                    JButton pressedButton = (JButton) ctrlAe.getSource();
                    String strCmd = pressedButton.getText().toUpperCase();
                    strCmd = strCmd.replace(" ", "");
                    execCalendarAction(strCmd, proxy);

                }
            }
        };

        ActionListener menuAl = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent menuAe) {
                if (menuAe.getActionCommand().equals("Quit")){
                    System.exit(0);
                }

                if (menuAe.getActionCommand().equals("Logout")){
                    GUILogin guiLogin = new GUILogin();
                    guiLogin.setVisible(true);
                    setVisible(false);
                }

                if (menuAe.getActionCommand().equals("Show all assignments")){
                    openListAssignment(proxy);
                }

                if (menuAe.getActionCommand().equals("Write a review")){
                    calendarState = CalendarState.REVIEWING;
                    openListAssignment(proxy);
                }

                if (menuAe.getActionCommand().equals("Delete review")){
                    calendarState = CalendarState.DELETING_REVIEW;
                    openListAssignment(proxy);
                }

                if (menuAe.getActionCommand().equals("Show all reviews")){
                    calendarState = CalendarState.SHOW_REVIEWS;
                    openListAssignment(proxy);
                }

                if (menuAe.getActionCommand().equals("New assignment")){
                    try {
                        enableDisableDateButtonAssignment();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    newAssignment();
                }

                if (menuAe.getActionCommand().equals("Delete assignment")){
                    try {
                        enableDisableDateButtonAssignment();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    removeAssignment();
                }

                if (menuAe.getActionCommand().equals("Account")){
                    GUISettings guiSettings = new GUISettings(email);    //TODO customer
                    guiSettings.setVisible(true);
                }

                if(menuAe.getActionCommand().equals("Change Password")) {
                    GUIChangePassword guiChangePassword = new GUIChangePassword(email);
                    guiChangePassword.setVisible(true);
                }

                if (menuAe.getActionCommand().equals("Dogs")){
                    GUIDogs guiDogs = new GUIDogs(email);
                    guiDogs.setVisible(true);
                }

                if (menuAe.getActionCommand().equals("Cancel")){
                    cancel();
                }

                if (menuAe.getActionCommand().equals("Info")){
                    GUIInfo info = new GUIInfo();
                    info.setVisible(true);
                }

                if (menuAe.getActionCommand().equals("Credits")){
                    GUIAwards credits = new GUIAwards();
                    credits.setVisible(true);
                }


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

        for (i = 0; i < nShownTodayAssignments; i++){
            buttonTodayAssignment[i].addActionListener(todayAssignmentsAl);
        }

    }

    private void newAssignment(){
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

    private void cancel(){
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

    private void removeAssignment(){
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

    private void loadTheFirstFiveAssignments(int nShownAssignments){
        if (nShownAssignments > MAXVISIBLETODAYASSIGNMENT){
            nShownAssignments = MAXVISIBLETODAYASSIGNMENT;
        }

        int i = 0;
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();

        HashMap<Integer, Assignment> listAssignment = proxy.getAssignmentList();

        for (Integer key : listAssignment.keySet()) {
            Assignment a = listAssignment.get(key);
            String strDateStart = date.format(a.getDateStart());
            String strDateEnd = date.format(a.getDateEnd());
            String strTodayDate = date.format(todayDate);
            try {
                Date dayStart = date.parse(strDateStart);
                Date dayEnd = date.parse(strDateEnd);
                Date today = date.parse(strTodayDate);
                if (((today.after(dayStart) || today.equals(dayStart)) && (today.before(dayEnd)) || today.equals(dayEnd)) && (i < nShownAssignments)){
                    codeFirstFiveAssignmentsList.add(key);
                    i++;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        int n = 0;
        for (Integer key : codeFirstFiveAssignmentsList) {
            Assignment a = listAssignment.get(key);
            String nameDogSitter = proxy.getDogSitterNameOfAssignment(a.getCode());
            String surnameDogSitter = proxy.getDogSitterSurnameOfAssignment(a.getCode());
            buttonTodayAssignment[n].setText("Assignment with " + capitalizeFirstLetter(nameDogSitter) + " " + capitalizeFirstLetter(surnameDogSitter));
            buttonTodayAssignment[n].setDisplayedMnemonicIndex(key);
            n++;
        }
    }

}
