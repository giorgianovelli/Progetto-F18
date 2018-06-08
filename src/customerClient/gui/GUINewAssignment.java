package customerClient.gui;

import javax.swing.*;
import java.awt.*;

public class GUINewAssignment extends JFrame{

    final int WIDTH = 512;
    final int HEIGHT = 512;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private JPanel panelOut = new JPanel();
    private JPanel panelData = new JPanel();
    private JPanel panelButtons = new JPanel();
    private JPanel panelTest = new JPanel();
    private JScrollPane scrollPane = new JScrollPane(panelOut);

    private JButton buttonCancel = new JButton("Cancel");
    private JButton buttonSearch = new JButton("Search");


    NewAssignmentBox newAssignmentBox = new NewAssignmentBox();
    NewAssignmentText address = new NewAssignmentText("Address:");
    NewAssignmentText city = new NewAssignmentText("City:");
    NewAssignmentText code = new NewAssignmentText("Code:");
    NewAssignmentText country = new NewAssignmentText("Country:");


//__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________

    //Costruttore

    public GUINewAssignment() {
        setTitle("New Assignment");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(true);             //TODO reset false
        setLayout(new BorderLayout());

        initComponents();

        }

//________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________

    private void initComponents() {


        panelOut.setLayout(new BorderLayout());
        panelData.setLayout(new GridLayout(5, 1, 30, 20));
        panelData.setBorder(BorderFactory.createTitledBorder("Complete every field to search a dogsitter: "));

        panelOut.add(panelData, BorderLayout.NORTH);
        panelOut.add(panelButtons, BorderLayout.SOUTH);

        panelTest.add(buttonSearch);

        panelData.add(newAssignmentBox);
        panelData.add(address);
        panelData.add(city);
        panelData.add(code);
        panelData.add(country);

        //TODO perchè i pulsanti vanno a destra invece che in basso???


        panelButtons.setLayout(new GridLayout(1,2,5,0));
        panelButtons.setBorder(BorderFactory.createEmptyBorder(20, 150, 20, 150)); //?????
        panelButtons.add(buttonCancel);
        panelButtons.add(buttonSearch);


        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);

    }
}

//__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________


class NewAssignmentBox extends JPanel{

    JComboBox<String> fdayList, tdayList;
    JComboBox<String> fmonthList, tmonthList;
    JComboBox<String> fyearList, tyearList;
    JComboBox<String> fhourList, thourList;
    JComboBox<String> fminuteList, tminuteList;
    JLabel fromLabel = new JLabel("From:");
    JLabel toLabel = new JLabel("To:");
    JLabel blanckLabel = new JLabel("");
    JLabel daysLabel = new JLabel("Day:");
    JLabel monthsLabel = new JLabel("Month:");
    JLabel yearsLabel = new JLabel("Year:");
    JLabel hoursLabel = new JLabel("Hour:");
    JLabel minutesLabel = new JLabel("Minute:");



    String[] day = new String[]{};
    String[] month = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    String[] year = new String[]{"2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040"};
    String[] hour = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
    String[] minute = new String[]{"00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"};


//__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________


    // Costruttore NewAssignmentBox

    public NewAssignmentBox(){
        fdayList = new JComboBox<>(day);
        fmonthList = new JComboBox(month);
        fyearList = new JComboBox<>(year);
        fhourList = new JComboBox<>(hour);
        fminuteList = new JComboBox<>(minute);
        tdayList = new JComboBox<>(day);
        tmonthList = new JComboBox<>(month);
        tyearList = new JComboBox<>(year);
        thourList = new JComboBox<>(hour);
        tminuteList = new JComboBox<>(minute);

        setLayout(new GridLayout(3, 6, 10, 10));

        //Label

        add(blanckLabel);
        add(daysLabel);
        add(monthsLabel);
        add(yearsLabel);
        add(hoursLabel);
        add(minutesLabel);

        //ComboBox

        add(fromLabel);
        add(fdayList);
        add(fmonthList);
        add(fyearList);
        add(fhourList);
        add(fminuteList);

        add(toLabel);
        add(tdayList);
        add(tmonthList);
        add(tyearList);
        add(thourList);
        add(tminuteList);

    }

}

//__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________

class NewAssignmentText extends JPanel{
    JLabel label;
    TextField field = new TextField("",30);
    JLabel blanckLabel = new JLabel("");

    public NewAssignmentText(String text){
        label = new JLabel(text);
        add(label, BorderLayout.LINE_START);
        add(field, BorderLayout.CENTER);
    }
}

/*class NewAssignmentCheckBox extends JPanel{

    JCheckBox checkBox;

    public NewAssignmentCheckBox(){

        checkBox = new JCheckBox();
        checkBox.

    }
};*/