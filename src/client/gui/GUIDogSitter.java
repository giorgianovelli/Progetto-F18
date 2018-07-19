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
                clickOnMenuBarItem(menuAe);
            }
        };

        ActionListener todayAssignmentsAl = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent todayAssignmentAe) {
                callClickOnTodayAssignment(todayAssignmentAe);
            }
        };

        //prepara il calendario
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

        /*if ((!(cae.getActionCommand().equals(""))) && ((calendarState.equals(CalendarState.NORMAL)) || (calendarState.equals(CalendarState.REMOVING)))){
            GUIDailyAssignments guiDailyAssignments = new GUIDailyAssignments(calendarState, email, todayDate);
            guiDailyAssignments.setVisible(true);
        }*/
        if ((!(cae.getActionCommand().equals(""))) &&  ((calendarState.equals(CalendarState.NORMAL)))){
            GUIDailyAssignmentDogsitter guiDailyAssignmentDogsitter = new GUIDailyAssignmentDogsitter(calendarState, email, todayDate);
            guiDailyAssignmentDogsitter.setVisible(true);
        }

    }

    public void showAllAssignments(){
        GUIShowDogsitterAssignment guiShowDogsitterAssignment = new GUIShowDogsitterAssignment(CalendarState.NORMAL, proxy.getAssignmentList(), email, this);
        guiShowDogsitterAssignment.setVisible(true);

    }

    public void showAllReviews(){
        GUIShowDogsitterAssignment guiShowDogsitterAssignment = new GUIShowDogsitterAssignment(CalendarState.SHOW_REVIEWS, proxy.getAssignmentList(), email, this);
        guiShowDogsitterAssignment.setVisible(true);

    }


    public void accountSettings(){
        //TODO da implementare
       // GUIDogSitterSettings guiDogSitterSettings = new GUIDogSitterSettings(email);
      //  guiDogSitterSettings.setVisible(true);
    }

    public void changePassword(){
        GUIDogSitterChangePassword guiDogSitterChangePassword = new GUIDogSitterChangePassword(email);
        guiDogSitterChangePassword.setVisible(true);
    }

    private void callClickOnTodayAssignment(ActionEvent todayAssignmentAe){
        clickOnTodayAssignment(todayAssignmentAe, proxy, this);
    }

}
