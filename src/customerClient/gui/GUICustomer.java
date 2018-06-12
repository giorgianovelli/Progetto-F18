package customerClient.gui;

import customerClient.CustomerProxy;
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

public class GUICustomer extends JFrame{
    final int WIDTH = 1024;
    final int HEIGHT = 600;
    final int NDAYMONTH = 31;
    final int NDAYWEEK = 7;
    final int NEMPTYLABEL = 11;
    final int NCALENDARCELLS = 49;
    final int MAXVISIBLETODAYASSIGNMENT = 5;

    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuFile = new JMenu("File");
    private JMenuItem menuItemExit = new JMenuItem("Quit");
    private JMenuItem menuItemLogout = new JMenuItem("Logout");
    private JMenu menuAssignment = new JMenu("Assignment");
    private JMenuItem menuItemAddAssignment = new JMenuItem("New assignment");
    private JMenuItem menuItemRemoveAssignment = new JMenuItem("Delete assignment");
    private JMenuItem menuItemShowAssignments = new JMenuItem("Show all assignments");
    private JMenu menuReview = new JMenu("Review");
    private JMenuItem menuItemAddReview = new JMenuItem("Write a review");
    private JMenuItem menuItemRemoveReview = new JMenuItem("Delete review");
    private JMenuItem menuItemShowReviews = new JMenuItem("Show all reviews");
    private JMenu menuSettings = new JMenu("Settings");
    private JMenuItem menuItemAccount = new JMenuItem("Account");
    private JMenuItem menuItemChangePassword = new JMenuItem("Change Password");
    private JMenuItem menuItemDogs = new JMenuItem("Dogs");
    private JMenu menuExtra = new JMenu("?");
    private JMenuItem menuItemInfo = new JMenuItem("Info");
    private JMenuItem menuItemAwards = new JMenuItem("Awards");
    private JMenuItem menuItemCancel = new JMenuItem("Cancel");

    private JPanel panelToday = new JPanel();
    private JLabel labelTodayAssignments = new JLabel("Today's assignments");
    private JButton buttonTodayAssignment[];
    private JButton buttonShowMoreTodayAssignments = new JButton("Show more");
    private JLabel labelEmptyTodayAssignments[];
    private int nTodayAssignments = 0;

    private JPanel calendar = new JPanel();
    private JPanel panelDateCalendar = new JPanel();
    private JPanel panelGridCalendar = new JPanel();
    private JLabel labelDay[];
    private JButton buttonDay[];
    private JLabel labelEmpty[];
    private JButton buttonPreviousYear = new JButton("<<");
    private JButton buttonPreviousMonth = new JButton("<");
    private JButton buttonNextYear = new JButton(">>");
    private JButton buttonNextMonth = new JButton(">");
    private JLabel labelDateMonthYear = new JLabel("08/2019", SwingConstants.CENTER);
    private CalendarState calendarState = CalendarState.NORMAL;

    private CustomerProxy proxy;
    private String email;
    private HashSet<Integer> codeFirstFiveAssignmentsList = new HashSet<Integer>();


    public GUICustomer(String email) throws ParseException {
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
                if ((!(cae.getActionCommand().equals(""))) && ((calendarState.equals(CalendarState.NORMAL)) || (calendarState.equals(CalendarState.REMOVING)))){
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


                    GUIDailyAssignments guiDailyAssignments = new GUIDailyAssignments(calendarState, email, todayDate);
                    guiDailyAssignments.setVisible(true);
                }

                if ((!(cae.getActionCommand().equals(""))) && (calendarState.equals(CalendarState.ADDING))){
                    JButton pressedButton = (JButton) cae.getSource();
                    GUINewAssignment guiNewAssignment = new GUINewAssignment();
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



                if (menuAe.getActionCommand().equals("Cancel")){
                    cancel();
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
                        HashMap<Integer, Assignment> listAssignment = proxy.getCustomerListAssignment();
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
        buttonShowMoreTodayAssignments.addActionListener(ctrlCal);

        for (i = 0; i < nShownTodayAssignments; i++){
            buttonTodayAssignment[i].addActionListener(todayAssignmentsAl);
        }

    }

    private void startCalendar(ActionListener cal, ActionListener ctrlCal) throws ParseException {
        calendar.setLayout(new BorderLayout());
        panelDateCalendar.setLayout(new GridLayout(1, 5));
        panelDateCalendar.add(buttonPreviousYear);
        panelDateCalendar.add(buttonPreviousMonth);
        panelDateCalendar.add(labelDateMonthYear);
        panelDateCalendar.add(buttonNextMonth);
        panelDateCalendar.add(buttonNextYear);
        calendar.add(panelDateCalendar, BorderLayout.NORTH);
        panelGridCalendar.setLayout((new GridLayout(7, 7, 5, 5)));
        calendar.add(panelGridCalendar, BorderLayout.CENTER);
        labelDay = new JLabel[NDAYWEEK];
        int i = 0;
        for (WeekDays wd: WeekDays.values()) {
            labelDay[i] = new JLabel(wd.toString(), SwingConstants.CENTER);
            panelGridCalendar.add(labelDay[i]);
            i++;
        }

        //ottiene informazioni sul sistema operativo utilizzato
        String os = System.getProperty("os.name");

        buttonDay = new JButton[NDAYMONTH];
        for (i = 0; i < NDAYMONTH; i++){
            buttonDay[i] = new JButton(Integer.toString(i + 1));
            buttonDay[i].setBackground(new Color(204, 230, 255));
            buttonDay[i].addActionListener(cal);
            if (os.equals("Mac OS X")){
                buttonDay[i].setOpaque(true);
                buttonDay[i].setBorderPainted(false);
            }
        }

        add(calendar, BorderLayout.CENTER);
        initializeCalendar();

        buttonPreviousMonth.addActionListener(ctrlCal);
        buttonNextMonth.addActionListener(ctrlCal);
        buttonPreviousYear.addActionListener(ctrlCal);
        buttonNextYear.addActionListener(ctrlCal);
    }

    private void initializeCalendar() throws ParseException {
        SimpleDateFormat dateMonth = new SimpleDateFormat("MM");
        Date currentMonth = new Date();
        int monthNumber = Integer.parseInt(dateMonth.format(currentMonth));
        SimpleDateFormat dateYear = new SimpleDateFormat("yyyy");
        Date currentYear = new Date();
        labelDateMonthYear.setText(dateMonth.format(currentMonth) + "/" + dateYear.format(currentYear));
        updateCalendar(monthNumber);
    }

    private void goBackMonthCalendar() throws ParseException {
        panelGridCalendar.removeAll();
        panelGridCalendar.revalidate();
        panelGridCalendar.repaint();
        int i = 0;
        for (WeekDays wd: WeekDays.values()) {
            labelDay[i] = new JLabel(wd.toString(), SwingConstants.CENTER);
            panelGridCalendar.add(labelDay[i]);
            i++;
        }

        SimpleDateFormat dateMonthYear = new SimpleDateFormat("MM/yyyy");
        SimpleDateFormat dateMonth = new SimpleDateFormat("M");
        SimpleDateFormat dateYear = new SimpleDateFormat("yyyy");
        Date date = dateMonthYear.parse(labelDateMonthYear.getText());
        String strMonthNumber = dateMonth.format(date);
        String strYear = dateYear.format(date);
        int monthNumber = Integer.parseInt(strMonthNumber) - 1;
        if (monthNumber == 0){
            monthNumber = 12;
            Integer newYear = Integer.parseInt(strYear) - 1;
            strYear = newYear.toString();
        }
        if (monthNumber < 10){
            labelDateMonthYear.setText("0" + monthNumber + "/" + strYear);
        } else {
            labelDateMonthYear.setText(monthNumber + "/" + strYear);
        }

        updateCalendar(monthNumber);
    }

    private void goBackYearCalendar() throws ParseException {
        panelGridCalendar.removeAll();
        panelGridCalendar.revalidate();
        panelGridCalendar.repaint();
        int i = 0;
        for (WeekDays wd: WeekDays.values()) {
            labelDay[i] = new JLabel(wd.toString(), SwingConstants.CENTER);
            panelGridCalendar.add(labelDay[i]);
            i++;
        }

        SimpleDateFormat dateMonthYear = new SimpleDateFormat("MM/yyyy");
        SimpleDateFormat dateMonth = new SimpleDateFormat("M");
        SimpleDateFormat dateYear = new SimpleDateFormat("yyyy");
        Date date = dateMonthYear.parse(labelDateMonthYear.getText());
        String strMonthNumber = dateMonth.format(date);
        String strYear = dateYear.format(date);
        int monthNumber = Integer.parseInt(strMonthNumber);
        Integer newYear = Integer.parseInt(strYear) - 1;
        strYear = newYear.toString();
        if (monthNumber < 10){
            labelDateMonthYear.setText("0" + monthNumber + "/" + strYear);
        } else {
            labelDateMonthYear.setText(monthNumber + "/" + strYear);
        }

        updateCalendar(monthNumber);
    }

    private void goForwardMonthCalendar() throws ParseException {
        panelGridCalendar.removeAll();
        panelGridCalendar.revalidate();
        panelGridCalendar.repaint();
        int i = 0;
        for (WeekDays wd: WeekDays.values()) {
            labelDay[i] = new JLabel(wd.toString(), SwingConstants.CENTER);
            panelGridCalendar.add(labelDay[i]);
            i++;
        }

        SimpleDateFormat dateMonthYear = new SimpleDateFormat("MM/yyyy");
        SimpleDateFormat dateMonth = new SimpleDateFormat("M");
        SimpleDateFormat dateYear = new SimpleDateFormat("yyyy");
        Date date = dateMonthYear.parse(labelDateMonthYear.getText());
        String strMonthNumber = dateMonth.format(date);
        String strYear = dateYear.format(date);
        int monthNumber = Integer.parseInt(strMonthNumber) + 1;
        if (monthNumber == 13){
            monthNumber = 1;
            Integer newYear = Integer.parseInt(strYear) + 1;
            strYear = newYear.toString();
        }
        if (monthNumber < 10){
            labelDateMonthYear.setText("0" + monthNumber + "/" + strYear);
        } else {
            labelDateMonthYear.setText(monthNumber + "/" + strYear);
        }

        updateCalendar(monthNumber);
    }

    private void goForwardYearCalendar() throws ParseException {
        panelGridCalendar.removeAll();
        panelGridCalendar.revalidate();
        panelGridCalendar.repaint();
        int i = 0;
        for (WeekDays wd: WeekDays.values()) {
            labelDay[i] = new JLabel(wd.toString(), SwingConstants.CENTER);
            panelGridCalendar.add(labelDay[i]);
            i++;
        }

        SimpleDateFormat dateMonthYear = new SimpleDateFormat("MM/yyyy");
        SimpleDateFormat dateMonth = new SimpleDateFormat("M");
        SimpleDateFormat dateYear = new SimpleDateFormat("yyyy");
        Date date = dateMonthYear.parse(labelDateMonthYear.getText());
        String strMonthNumber = dateMonth.format(date);
        String strYear = dateYear.format(date);
        int monthNumber = Integer.parseInt(strMonthNumber);
        Integer newYear = Integer.parseInt(strYear) + 1;
        strYear = newYear.toString();
        if (monthNumber < 10){
            labelDateMonthYear.setText("0" + monthNumber + "/" + strYear);
        } else {
            labelDateMonthYear.setText(monthNumber + "/" + strYear);
        }

        updateCalendar(monthNumber);
    }

    private void updateCalendar(int monthNumber) throws ParseException {
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = "01/" + labelDateMonthYear.getText();
        Date currentDate = date.parse(strDate);
        SimpleDateFormat dateDayNumber = new SimpleDateFormat("u");
        dateDayNumber.setLenient(false);
        String stringDayNumber = dateDayNumber.format(currentDate);
        int dayNumber = Integer.parseInt(stringDayNumber);
        int i;
        int ie;
        int ne = 0;
        int cc = NDAYWEEK;
        labelEmpty= new JLabel[NEMPTYLABEL];
        switch (dayNumber){
            case 1:
                ne = 0;
                break;
            case 2:
                ne = 1;
                break;
            case 3:
                ne = 2;
                break;
            case 4:
                ne = 3;
                break;
            case 5:
                ne = 4;
                break;
            case 6:
                ne = 5;
                break;
            case 7:
                ne = 6;
                break;
        }
        for (ie = 0; ie < ne; ie++){
            labelEmpty[ie] = new JLabel();
            panelGridCalendar.add(labelEmpty[ie]);
            cc++;
        }

        i = 0;
        int nd;
        switch (monthNumber){
            case 4:
            case 6:
            case 9:
            case 11:
                nd = 30;
                break;
            case 2:
                if (isLeap(currentDate)){
                    nd = 29;
                } else {
                    nd = 28;
                }
                break;
            default:
                nd = 31;
                break;
        }

        for (i = 0; i < nd; i++){
            panelGridCalendar.add(buttonDay[i]);
            cc++;
        }

        while (cc < NCALENDARCELLS){
            labelEmpty[ie] = new JLabel();
            panelGridCalendar.add(labelEmpty[ie]);
            cc++;
        }

        if ((calendarState.equals(CalendarState.ADDING)) || (calendarState.equals(CalendarState.REMOVING))){
            enableDisableDateButtonAssignment();
        }

        for (i = 0; i < NDAYMONTH; i++){
            //buttonDay[i].setBackground(new Color(204, 230, 255));

            if (!(calendarState.equals(CalendarState.ADDING)) && !(calendarState.equals(CalendarState.REMOVING))){
                buttonDay[i].setBackground(new Color(204, 230, 255));
            }

            String strCurrentDate = date.format(new Date());
            String dateButton;
            int nDay = Integer.parseInt(buttonDay[i].getText());
            if (nDay < 10){
                dateButton = "0" + buttonDay[i].getText() + "/" + labelDateMonthYear.getText();
            } else {
                dateButton = buttonDay[i].getText() + "/" + labelDateMonthYear.getText();
            }
            if (dateButton.equals(strCurrentDate)){
                buttonDay[i].setForeground(new Color(255, 0, 0));
            } else {
                buttonDay[i].setForeground(new Color(0, 0, 0));
            }
        }

        if (!(calendarState.equals(CalendarState.ADDING)) && !(calendarState.equals(CalendarState.REMOVING))){
            showAssignmentOnCalendar(email);
        }
    }

    private boolean isLeap(Date yearToCheck){
        SimpleDateFormat date = new SimpleDateFormat("yyyy");
        String strYear = date.format(yearToCheck);
        int year = Integer.parseInt(strYear);
        if (year % 4 == 0){
            if (year % 100 == 0){
                if (year % 400 == 0){
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } else {
            return false;
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

    private void openListAssignment(){
        GUIListAssignments guiListAssignments = new GUIListAssignments(calendarState, proxy.getCustomerListAssignment(), email, this);
        guiListAssignments.setVisible(true);
    }

    public void setCalendarState(CalendarState cs){
        calendarState = cs;
    }

    private void enableDisableDateButtonAssignment() throws ParseException {
        int i;
        String strDay;
        String strSelectedDate;
        Date selectedDate;
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
        Date currentDate = new Date();
        for (i = 0; i < NDAYMONTH; i++){
            Integer day = Integer.parseInt(buttonDay[i].getText());
            if (day < 10){
                strDay = "0" + day;
            } else {
                strDay = day.toString();
            }
            strSelectedDate = strDay + "/" + labelDateMonthYear.getText();
            selectedDate = formatDate.parse(strSelectedDate);
            if (selectedDate.before(currentDate)){
                buttonDay[i].setEnabled(false);
            } else {
                buttonDay[i].setEnabled(true);
            }
        }
    }

    private void showAssignmentOnCalendar(String email){
        HashMap<Integer, Assignment> listAssignment = proxy.getCustomerListAssignment();
        boolean included = false;
        for (Integer key : listAssignment.keySet()) {
            Assignment a = listAssignment.get(key);
            Date dateStart = a.getDateStart();
            Date dateEnd = a.getDateEnd();
            SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
            String strDateStart = date.format(dateStart);
            String strDateEnd = date.format(dateEnd);
            int i;
            String strButtonDate;
            for (i = 0; i < NDAYMONTH; i++){
                if (i < 10){
                    strButtonDate = "0" + buttonDay[i].getText() + "/" + labelDateMonthYear.getText();
                } else {
                    strButtonDate = buttonDay[i].getText() + "/" + labelDateMonthYear.getText();
                }
                if (strButtonDate.equals(strDateStart) ){
                    buttonDay[i].setBackground(Color.ORANGE);
                    included = true;
                }
                if (strButtonDate.equals(strDateEnd) ){
                    buttonDay[i].setBackground(Color.ORANGE);
                    included = false;
                }
                if (included){
                    buttonDay[i].setBackground(Color.ORANGE);
                }
            }
        }
    }

    private int getNDailyAssignments(){
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        int nAssignments = 0;
        HashMap<Integer, Assignment> listAssignment = proxy.getCustomerListAssignment();
        for (Integer key : listAssignment.keySet()) {
            Assignment a = listAssignment.get(key);
            String strDateStart = date.format(a.getDateStart());
            String strTodayDate = date.format(todayDate);
            String strDateEnd = date.format(a.getDateEnd());
            try {
                Date dayStart = date.parse(strDateStart);
                Date dayEnd = date.parse(strDateEnd);
                Date today = date.parse(strTodayDate);

                if ((today.after(dayStart) || today.equals(dayStart)) && (today.before(dayEnd)) || today.equals(dayEnd)){
                    nAssignments++;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return nAssignments;
    }

    private void loadTheFirstFiveAssignments(int nShownAssignments){
        if (nShownAssignments > MAXVISIBLETODAYASSIGNMENT){
            nShownAssignments = MAXVISIBLETODAYASSIGNMENT;
        }

        int i = 0;
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        HashMap<Integer, Assignment> listAssignment = proxy.getCustomerListAssignment();

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
