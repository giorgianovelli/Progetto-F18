package gui;


import java.awt.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.JPanel;
//import java.awt.List;


public class GUINewAssignment extends JFrame {
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int WIDTH = 512;
    int HEIGHT = 512;
    private JPanel topPanel, fPanel, tPanel, aPanel, nPanel, cPanel, coPanel, couPanel;
    private JLabel flabel;
    private JLabel tlabel;
    private JLabel addresslabel;
    private JLabel numberlabel;
    private JLabel citylabel;
    private JLabel codelabel;
    private JLabel countrylabel;
    private ArrayList <JPanel> frow = new ArrayList<>();
    private ArrayList <JPanel> trow = new ArrayList<>();
    private ArrayList <JPanel> addressrow = new ArrayList<>();
    private ArrayList <JPanel> numberrow = new ArrayList<>();
    private ArrayList <JPanel> cityrow = new ArrayList<>();
    private ArrayList <JPanel> coderow = new ArrayList<>();
    private ArrayList <JPanel> countryrow = new ArrayList<>();
    private JPanel fdayPanel;
    private JPanel fmonthPanel;
    private JPanel fyearPanel;
    private JPanel fhourPanel;
    private JPanel fminutePanel;
    private JPanel tdayPanel;
    private JPanel tmonthPanel;
    private JPanel tyearPanel;
    private JPanel thourPanel;
    private JPanel tminutePanel;
    private JPanel cityPanel;
    private JPanel addressPanel;
    private JPanel numberPanel;
    private JPanel codePanel;
    private JPanel countryPanel;
    private JComboBox<String> fdayList, tdayList;
    private JComboBox<String> fmonthList, tmonthList;
    private JComboBox<String> fyearList, tyearList;
    private JComboBox<String> fhourList, thourList;
    private JComboBox<String> fminuteList, tminuteList;
    private TextField city;
    private TextField address;
    private TextField number;
    private TextField code;
    private TextField country;


    public GUINewAssignment() throws HeadlessException {
        setTitle("New Assignment");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        initComponents();
    }


    private void initComponents() {
        fdayPanel = new JPanel();
        fmonthPanel = new JPanel();
        fyearPanel = new JPanel();
        fhourPanel = new JPanel();
        fminutePanel = new JPanel();
        tdayPanel = new JPanel();
        tmonthPanel = new JPanel();
        tyearPanel = new JPanel();
        thourPanel = new JPanel();
        tminutePanel = new JPanel();
        cityPanel = new JPanel();
        addressPanel = new JPanel();
        numberPanel = new JPanel();
        codePanel = new JPanel();
        countryPanel = new JPanel();

        String[] day = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
        String[] month = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        String[] year = new String[]{"2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040"};
        String[] hour = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
        String[] minute = new String[]{"00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"};

        fdayList = new JComboBox<>(day);
        fmonthList = new JComboBox(month);
        fyearList = new JComboBox<>(year);
        fhourList = new JComboBox<>(hour);
        fminuteList = new JComboBox<>(minute);

        fdayPanel.add(fdayList);
        fmonthPanel.add(fmonthList);
        fyearPanel.add(fyearList);
        fhourPanel.add(fhourList);
        fminutePanel.add(fminuteList);

        flabel = new JLabel("From: ");
        frow.add(fdayPanel);
        frow.add(fmonthPanel);
        frow.add(fyearPanel);
        frow.add(fhourPanel);
        frow.add(fminutePanel);


        tdayList = new JComboBox<>(day);
        tmonthList = new JComboBox<>(month);
        tyearList = new JComboBox<>(year);
        thourList = new JComboBox<>(hour);
        tminuteList = new JComboBox<>(minute);

        tdayPanel.add(tdayList);
        tmonthPanel.add(tmonthList);
        tyearPanel.add(tyearList);
        thourPanel.add(thourList);
        tminutePanel.add(tminuteList);

        tlabel = new JLabel("To: ");
        trow.add(tdayPanel);
        trow.add(tmonthPanel);
        trow.add(tyearPanel);
        trow.add(thourPanel);
        trow.add(tminutePanel);


        address = new TextField("", 30);
        number = new TextField("", 5);
        city = new TextField("", 20);
        code = new TextField("", 10);
        country = new TextField("", 20);

        addressPanel.add(address);
        numberPanel.add(number);
        cityPanel.add(city);
        codePanel.add(code);
        countryPanel.add(country);

        addresslabel = new JLabel("Address: ");
        addressrow.add(addressPanel);

        numberlabel = new JLabel("Number:");
        numberrow.add(numberPanel);

        citylabel = new JLabel("City:");
        cityrow.add(cityPanel);

        codelabel = new JLabel("Postal Code:");
        coderow.add(codePanel);

        countrylabel = new JLabel("Country:");
        countryrow.add(countryPanel);


        topPanel = new JPanel(new GridLayout(8, 1));
        fPanel = new JPanel();
        fPanel.setLayout(new GridLayout(1, 8, 10, 10));
        fPanel.add(flabel);
        for (JPanel el : frow) {
            fPanel.add(el);
        }

        tPanel = new JPanel();
        tPanel.setLayout(new GridLayout(1, 6, 10, 10));
        tPanel.add(tlabel);
        for (JPanel el : trow) {
            tPanel.add(el);
        }

        aPanel = new JPanel();
        aPanel.setLayout(new GridLayout(1, 6, 2, 2));
        aPanel.add(addresslabel);
        for (JPanel el : addressrow) {
            aPanel.add(el);
        }

        nPanel = new JPanel();
        nPanel.setLayout(new GridLayout(1, 6, 2, 2));
        nPanel.add(numberlabel);
        for (JPanel el : numberrow) {
            nPanel.add(el);
        }

        cPanel = new JPanel();
        cPanel.setLayout(new GridLayout(1, 6, 2, 2));
        cPanel.add(citylabel);
        for (JPanel el : cityrow) {
            cPanel.add(el);
        }

        coPanel = new JPanel();
        coPanel.setLayout(new GridLayout(1, 6, 2, 2));
        coPanel.add(codelabel);
        for (JPanel el : coderow) {
            coPanel.add(el);
        }

        couPanel = new JPanel();
        couPanel.setLayout(new GridLayout(1, 6, 2, 2));
        couPanel.add(countrylabel);
        for (JPanel el : countryrow) {
            couPanel.add(el);
        }

        setLayout(new BorderLayout());
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(512, 512));
        setResizable(false);
        topPanel.add(fPanel);
        topPanel.add(tPanel);
        topPanel.add(aPanel);
        topPanel.add(nPanel);
        topPanel.add(cPanel);
        topPanel.add(coPanel);
        topPanel.add(couPanel);
        add(topPanel, BorderLayout.NORTH);

    }

};