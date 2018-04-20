import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Date;
import java.util.StringTokenizer;

public class GUICustomer extends JFrame{
    final int WIDTH = 1024;
    final int HEIGHT = 600;
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuFile = new JMenu("File");
    private JMenuItem menuItemExit = new JMenuItem("Exit");
    private JMenuItem menuItemLogout = new JMenuItem("Logout");
    private JMenu menuAssignment = new JMenu("Assignment");
    private JMenuItem menuItemAddAssignment = new JMenuItem("New assignment");
    private JMenuItem menuItemRemoveAssignment = new JMenuItem("Delete assignment");
    private JMenuItem menuItemShowAssignments = new JMenuItem("Show all assignments");
    private JMenu menuReview = new JMenu("Review");
    private JMenuItem menuItemAddReview = new JMenuItem("Write a review");
    private JMenuItem menuItemRemoveReview = new JMenuItem("Delete review");
    private JMenuItem menuItemShowReview = new JMenuItem("Show all review");
    private JMenu menuSettings = new JMenu("Settings");
    private JMenuItem menuItemAccount = new JMenuItem("Account");
    private JMenuItem menuItemDogs = new JMenuItem("Dogs");
    private JMenu menuExtra = new JMenu("?");
    private JMenuItem menuItemInfo = new JMenuItem("Info");
    private JMenuItem menuItemAwards = new JMenuItem("Awards");
    private JMenuItem menuItemCancelAssignment = new JMenuItem("Cancel assignment");

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


    public GUICustomer() throws ParseException {
        setTitle("CaniBau (Customer)");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

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
        menuReview.add(menuItemShowReview);
        menuBar.add(menuReview);
        menuSettings.add(menuItemAccount);
        menuSettings.add(menuItemDogs);
        menuBar.add(menuSettings);
        menuExtra.add(menuItemInfo);
        menuExtra.add(menuItemAwards);
        menuBar.add(menuItemCancelAssignment);
        menuItemCancelAssignment.setVisible(false);
        menuBar.add(menuExtra);
        add(menuBar, BorderLayout.NORTH);

        ActionListener cal = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent cae) {
                if ((!(cae.getActionCommand().equals(""))) && (calendarState.equals(CalendarState.NORMAL))){
                    JButton pressedButton = (JButton) cae.getSource();
                    System.out.println(pressedButton.getText());
                    GUIDailyAssignments guiDailyAssignments = new GUIDailyAssignments();
                    guiDailyAssignments.setVisible(true);
                }

                if ((!(cae.getActionCommand().equals(""))) && (calendarState.equals(CalendarState.ADDING))){
                    JButton pressedButton = (JButton) cae.getSource();
                    System.out.println(pressedButton.getText());
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
                        goBackMonthCalendar(cal);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }

                if (ctrlAe.getActionCommand().equals(">")){
                    try {
                        goForwardMonthCalendar(cal);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }

                if (ctrlAe.getActionCommand().equals("<<")){
                    try {
                        goBackYearCalendar(cal);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }

                if (ctrlAe.getActionCommand().equals(">>")){
                    try {
                        goForwardYearCalendar(cal);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            }
        };

        ActionListener menuAl = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent menuAe) {
                if (menuAe.getActionCommand().equals("Exit")){
                    System.exit(0);
                }

                if (menuAe.getActionCommand().equals("Logout")){
                    GUILogin guiLogin = new GUILogin();
                    guiLogin.setVisible(true);
                    setVisible(false);
                }

                if (menuAe.getActionCommand().equals("New assignment")){
                    newAssignment();
                }

                if (menuAe.getActionCommand().equals("Cancel assignment")){
                    cancelAssignment();
                }
            }
        };

        //prepara il calendario
        startCalendar(cal, ctrlCal);

        menuItemExit.addActionListener(menuAl);
        menuItemLogout.addActionListener(menuAl);
        menuItemAddAssignment.addActionListener(menuAl);
        menuItemCancelAssignment.addActionListener(menuAl);

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
        panelGridCalendar.setLayout((new GridLayout(7, 7)));
        calendar.add(panelGridCalendar, BorderLayout.CENTER);
        labelDay = new JLabel[7];
        int i = 0;
        for (WeekDays wd: WeekDays.values()) {
            labelDay[i] = new JLabel(wd.toString(), SwingConstants.CENTER);
            panelGridCalendar.add(labelDay[i]);
            i++;
        }

        buttonDay = new JButton[31];
        for (i = 0; i < 31; i++){
            buttonDay[i] = new JButton(Integer.toString(i + 1));
            buttonDay[i].setBackground(new Color(204, 230, 255));
            buttonDay[i].addActionListener(cal);
        }

        add(calendar, BorderLayout.CENTER);
        initializeCalendar(cal);

        buttonPreviousMonth.addActionListener(ctrlCal);
        buttonNextMonth.addActionListener(ctrlCal);
        buttonPreviousYear.addActionListener(ctrlCal);
        buttonNextYear.addActionListener(ctrlCal);
    }

    private void initializeCalendar(ActionListener cal) throws ParseException {
        SimpleDateFormat dateMonth = new SimpleDateFormat("MM");
        Date currentMonth = new Date();
        int monthNumber = Integer.parseInt(dateMonth.format(currentMonth));
        //System.out.println(monthNumber);
        SimpleDateFormat dateYear = new SimpleDateFormat("yyyy");
        Date currentYear = new Date();
        labelDateMonthYear.setText(dateMonth.format(currentMonth) + "/" + dateYear.format(currentYear));
        updateCalendar(monthNumber, cal);
    }

    private void goBackMonthCalendar(ActionListener cal) throws ParseException {
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
        //System.out.println(strMonthNumber);
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

        updateCalendar(monthNumber, cal);
    }

    private void goBackYearCalendar(ActionListener cal) throws ParseException {
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
        //System.out.println(strMonthNumber);
        int monthNumber = Integer.parseInt(strMonthNumber);
        Integer newYear = Integer.parseInt(strYear) - 1;
        strYear = newYear.toString();
        if (monthNumber < 10){
            labelDateMonthYear.setText("0" + monthNumber + "/" + strYear);
        } else {
            labelDateMonthYear.setText(monthNumber + "/" + strYear);
        }

        updateCalendar(monthNumber, cal);
    }

    private void goForwardMonthCalendar(ActionListener cal) throws ParseException {
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
        System.out.println(strMonthNumber);
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

        updateCalendar(monthNumber, cal);
    }

    private void goForwardYearCalendar(ActionListener cal) throws ParseException {
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
        //System.out.println(strMonthNumber);
        int monthNumber = Integer.parseInt(strMonthNumber);
        Integer newYear = Integer.parseInt(strYear) + 1;
        strYear = newYear.toString();
        if (monthNumber < 10){
            labelDateMonthYear.setText("0" + monthNumber + "/" + strYear);
        } else {
            labelDateMonthYear.setText(monthNumber + "/" + strYear);
        }

        updateCalendar(monthNumber, cal);
    }

    private void updateCalendar(int monthNumber, ActionListener cal) throws ParseException {
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
        int cc = 7;
        labelEmpty= new JLabel[11];
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

        while (cc < 49){
            labelEmpty[ie] = new JLabel();
            panelGridCalendar.add(labelEmpty[ie]);
            cc++;
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
        for (i = 0; i < 31; i++){
            buttonDay[i].setBackground(new Color(179, 255, 179));
        }
        menuAssignment.setVisible(false);
        menuReview.setVisible(false);
        menuSettings.setVisible(false);
        menuExtra.setVisible(false);
        menuItemCancelAssignment.setVisible(true);
    }

    private void cancelAssignment(){
        calendarState = CalendarState.NORMAL;
        int i;
        for (i = 0; i < 31; i++){
            buttonDay[i].setBackground(new Color(204, 230, 255));
        }
        menuAssignment.setVisible(true);
        menuReview.setVisible(true);
        menuSettings.setVisible(true);
        menuExtra.setVisible(true);
        menuItemCancelAssignment.setVisible(false);
    }
}