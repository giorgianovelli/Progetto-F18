package client.gui;

import client.clientEnumerations.MenuBarAction;
import client.proxy.DogSitterProxy;
import enumeration.CalendarState;
import server.Assignment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;

import static server.tools.StringManipulator.capitalizeFirstLetter;

public class GUIDogSitter extends GUIHome{
    final int WIDTH = 1024;
    final int HEIGHT = 600;
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
    private JMenuItem menuItemReplyToReview = new JMenuItem("Reply to a review");

    private String email;
    private DogSitterProxy proxy;

    public GUIDogSitter(String email) throws ParseException  {
        super(email);
        setTitle("CaniBau (Dog sitter)");
        //setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        this.email = email;
        this.proxy = new DogSitterProxy(email);

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
                        //TODO da implementare
                    }
                }
            }
        };

        //prepara il calendario
        startCalendar(cal, ctrlCal, proxy);

        menuItemExit.addActionListener(menuAl);
        menuItemLogout.addActionListener(menuAl);
        menuItemShowAssignments.addActionListener(menuAl);
        menuItemReplyToReview.addActionListener(menuAl);
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

    protected void loadTheFirstFiveAssignments(int nShownAssignments){
        if (nShownAssignments > MAXVISIBLETODAYASSIGNMENT){
            nShownAssignments = MAXVISIBLETODAYASSIGNMENT;
        }

        HashMap<Integer, Assignment> assignmentList = proxy.getAssignmentList();
        codeFirstFiveAssignmentsList = getCodeFirstFiveAssignments(nShownAssignments, assignmentList);

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

    protected void disposeMenuBar(){
        //Crea il menù
        menuBar.add(menuFile);
        menuFile.add(menuItemLogout);
        menuFile.add(menuItemExit);
        menuAssignment.add(menuItemShowAssignments);
        menuBar.add(menuAssignment);
        menuReview.add(menuItemReplyToReview);
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

        if ((!(cae.getActionCommand().equals(""))) && ((calendarState.equals(CalendarState.NORMAL)) || (calendarState.equals(CalendarState.REMOVING)))){
            GUIDailyAssignments guiDailyAssignments = new GUIDailyAssignments(calendarState, email, todayDate);
            guiDailyAssignments.setVisible(true);
        }
    }

    public void showAllAssignments(){
        //TODO da implementare
        //openListAssignment(proxy);
    }

    public void showAllReviews(){
        //TODO da implementare
        //calendarState = CalendarState.SHOW_REVIEWS;
        //openListAssignment(proxy);
    }

    public void accountSettings(){
        //TODO da implementare
        //GUISettings guiSettings = new GUISettings(email);
        //guiSettings.setVisible(true);
    }

    public void changePassword(){
        //TODO da implementare
    }

    public void replyToReview(){
        //TODO da implementare
        System.out.println("Test reply");
    }

}
