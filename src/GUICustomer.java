import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        menuBar.add(menuExtra);
        add(menuBar, BorderLayout.NORTH);

        //dispone il resto dell'interfaccia
        startCalendar();

    }

    private void startCalendar() throws ParseException {
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

        add(calendar, BorderLayout.CENTER);
        updateCalendar();
    }

    private void updateCalendar() throws ParseException {
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = "01/" + labelDateMonthYear.getText();
        Date currentDate = date.parse(strDate);
        SimpleDateFormat dateDayNumber = new SimpleDateFormat("u");
        dateDayNumber.setLenient(false);
        String stringDayNumber = dateDayNumber.format(currentDate);
        int dayNumber = Integer.parseInt(stringDayNumber);
        int i = 0;
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
        buttonDay = new JButton[31];
        for (JButton bd : buttonDay){
            buttonDay[i] = new JButton(Integer.toString(i + 1));
            panelGridCalendar.add(buttonDay[i]);
            i++;
            cc++;
        }

        while (cc < 49){
            labelEmpty[ie] = new JLabel();
            panelGridCalendar.add(labelEmpty[ie]);
            cc++;
        }
    }
}