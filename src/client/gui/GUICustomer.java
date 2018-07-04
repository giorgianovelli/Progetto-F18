package client.gui;

import client.CustomerProxy;
import server.Assignment;
import enumeration.CalendarState;
import server.dateTime.WeekDays;
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
        int nShownTodayAssignments = 0;

        //restituisce il numero di appuntamenti del giorno
        nTodayAssignments = getNDailyAssignments();

        //redundant code?
        if (nTodayAssignments <= MAXVISIBLETODAYASSIGNMENT){
            buttonTodayAssignment = new JButton[nTodayAssignments];
            for (i = 0; i < nTodayAssignments; i++){
                buttonTodayAssignment[i] = new JButton("Test assignment");
                buttonTodayAssignment[i].setBackground(new Color(179, 237, 255));
                buttonTodayAssignment[i].setOpaque(true);
                buttonTodayAssignment[i].setBorderPainted(false);
                buttonTodayAssignment[i].setDisplayedMnemonicIndex(i);
                panelToday.add(buttonTodayAssignment[i]);
            }
        } else {
            buttonTodayAssignment = new JButton[MAXVISIBLETODAYASSIGNMENT];
            for (i = 0; i < MAXVISIBLETODAYASSIGNMENT; i++){
                buttonTodayAssignment[i] = new JButton("Test assignment");
                buttonTodayAssignment[i].setBackground(new Color(179, 237, 255));
                buttonTodayAssignment[i].setOpaque(true);
                buttonTodayAssignment[i].setBorderPainted(false);
                buttonTodayAssignment[i].setDisplayedMnemonicIndex(i);
                panelToday.add(buttonTodayAssignment[i]);
            }
        }

        if ((nTodayAssignments > 0) && (nTodayAssignments <= MAXVISIBLETODAYASSIGNMENT)){
            labelEmptyTodayAssignments = new JLabel[MAXVISIBLETODAYASSIGNMENT - nTodayAssignments + 1];
            for (i = 0; i < MAXVISIBLETODAYASSIGNMENT - nTodayAssignments + 1; i++){
                labelEmptyTodayAssignments[i] = new JLabel();
                panelToday.add(labelEmptyTodayAssignments[i]);
                nShownTodayAssignments = nTodayAssignments;
            }
        } else if (nTodayAssignments > MAXVISIBLETODAYASSIGNMENT){
            panelToday.add(buttonShowMoreTodayAssignments);
            nShownTodayAssignments = MAXVISIBLETODAYASSIGNMENT;
        } else {
            labelEmptyTodayAssignments = new JLabel[1];
            labelEmptyTodayAssignments[0] = new JLabel("No assignment to show", SwingConstants.CENTER);
            panelToday.add(labelEmptyTodayAssignments[0]);
            nShownTodayAssignments = 0;
        }

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
                if (ctrlAe.getActionCommand().equals("<")){
                    try {
                        goBackMonthCalendar();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }

                if (ctrlAe.getActionCommand().equals(">")){
                    try {
                        goForwardMonthCalendar();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }

                if (ctrlAe.getActionCommand().equals("<<")){
                    try {
                        goBackYearCalendar();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }

                if (ctrlAe.getActionCommand().equals(">>")){
                    try {
                        goForwardYearCalendar();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }

                if (ctrlAe.getActionCommand().equals("Show more")){
                    JButton pressedButton = (JButton) ctrlAe.getSource();

                    String strTodayDate;
                    if (Integer.parseInt(pressedButton.getText()) < 10){
                        strTodayDate =  "0" + pressedButton.getText() + "/" + labelDateMonthYear.getText();
                    } else {
                        strTodayDate = pressedButton.getText() + "/" + labelDateMonthYear.getText();
                    }

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date todayDate = null;
                    try {
                        todayDate = dateFormat.parse(strTodayDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    GUIDailyAssignments guiDailyAssignments = new GUIDailyAssignments(calendarState, email, todayDate);

                    guiDailyAssignments.setVisible(true);
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
                    openListAssignment();
                }

                if (menuAe.getActionCommand().equals("Write a review")){
                    calendarState = CalendarState.REVIEWING;
                    openListAssignment();
                }

                if (menuAe.getActionCommand().equals("Delete review")){
                    calendarState = CalendarState.DELETING_REVIEW;
                    openListAssignment();
                }

                if (menuAe.getActionCommand().equals("Show all reviews")){
                    calendarState = CalendarState.SHOW_REVIEWS;
                    openListAssignment();
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
                            updateCalendar(monthNumber);
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
        startCalendar(cal, ctrlCal);

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
            showAssignmentOnCalendar(email);
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


}
