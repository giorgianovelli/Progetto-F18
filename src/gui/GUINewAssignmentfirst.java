package gui;

import javax.swing.*;
import java.awt.*;

public class GUINewAssignmentfirst extends JFrame{
    NewAssignmentBox newAssignmentBox = new NewAssignmentBox();
    NewAssignmentText address = new NewAssignmentText("Address:");
    NewAssignmentText city = new NewAssignmentText("City:");
    NewAssignmentText code = new NewAssignmentText("Code:");
    NewAssignmentText country = new NewAssignmentText("Country:");
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();


    public GUINewAssignmentfirst() {
        setTitle("New Assignment");
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(512, 512);
        setResizable(false);
        setLayout(new GridLayout(5, 1, 30, 20));
        add(newAssignmentBox);
        add(address);
        add(city);
        add(code);
        add(country);

    }
}

class NewAssignmentBoxfirst extends JPanel{

    JComboBox<String> fdayList, tdayList;
    JComboBox<String> fmonthList, tmonthList;
    JComboBox<String> fyearList, tyearList;
    JComboBox<String> fhourList, thourList;
    JComboBox<String> fminuteList, tminuteList;
    JLabel fromLabel = new JLabel("From:");
    JLabel toLabel = new JLabel("To:");
    JLabel blanckLabel = new JLabel("");
    JLabel daysLabel = new JLabel("Day");
    JLabel monthsLabel = new JLabel("Moths");
    JLabel yearsLabel = new JLabel("Years");
    JLabel hoursLabel = new JLabel("Hour");
    JLabel minutesLabel = new JLabel("Minute");



    String[] day = new String[]{};
    String[] month = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    String[] year = new String[]{"2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040"};
    String[] hour = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
    String[] minute = new String[]{"00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"};

    public NewAssignmentBoxfirst(){
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

        setLayout(new GridLayout(3, 6));
        add(blanckLabel); add(daysLabel); add(monthsLabel); add(yearsLabel); add(hoursLabel); add(minutesLabel);
        add(fromLabel); add(fdayList); add(fmonthList); add(fyearList); add(fhourList); add(fminuteList);
        add(toLabel); add(tdayList); add(tmonthList); add(tyearList); add(thourList); add(tminuteList);

    }

}

class NewAssignmentTextfirst extends JPanel{
    JLabel label;
    TextField field = new TextField("",30);
    JLabel blanckLabel = new JLabel("");

    public NewAssignmentTextfirst(String text){
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
}*/