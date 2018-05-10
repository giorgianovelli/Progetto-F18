package gui;

//import javafx.scene.control.CheckBox;

import java.awt.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.awt.event.ActionEvent;
//import java.awt.event.*;


import java.util.ArrayList;
import java.util.List;
//import java.util.HashSet;
//import java.util.Set;

import javax.swing.JFrame;
import javax.swing.*;
//import javax.swing.text.html.Option;
//import javax.swing.border.Border;
//import javax.swing.border.EmptyBorder;
import javax.swing.JPanel;
//import javax.swing.BorderFactory;
//import javax.swing.JCheckBox;
//import javafx.scene.control.CheckBox;








public class GUINewAssignment extends JFrame {


   private JPanel topPanel, fPanel, tPanel, aPanel, nPanel, cPanel, coPanel, couPanel;
   private JLabel flabel;
   private JLabel tlabel;
   private JLabel addresslabel;
   private JLabel numberlabel;
   private JLabel citylabel;
   private JLabel codelabel;
   private JLabel countrylabel;
   //private JLabel headerLabel;
   //private JLabel statusLabel;
   private List<JPanel> frow = new ArrayList<>();
   private List<JPanel> trow = new ArrayList<>();
   private List<JPanel> addressrow = new ArrayList<>();
   private List<JPanel> numberrow = new ArrayList<>();
   private List<JPanel> cityrow = new ArrayList<>();
   private List<JPanel> coderow = new ArrayList<>();
   private List<JPanel> countryrow = new ArrayList<>();
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
   //private JPanel controlPanel;
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

   //private static final String options[] = {"", ""}; //numero di righe, come variabile, proveniente dal database
   //JCheckBoxList checkBoxList = new JCheckBoxList(model);  //aggiungo checkbox dinamico
   //per la lista, con checkbox:
   /*JFrame frame = new JFrame("Dogs' list");
   JPanel panel = new JPanel();
   JCheckBox checkbox1 = new CheckBox("Uno");
   JCheckBox checkbox2 = new CheckBox("Due");
   JCheckBox checkbox3 = new CheckBox("Tre");*/
   //private JFrame mainFrame;





   public GUINewAssignment() throws HeadlessException {

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
      //controlPanel = new JPanel();

      String[] day = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
      String[] month = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
      String[] year = new String[]{"2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040"};
      String[] hour = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
      String[] minute = new String[]{"00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"};

      fdayList = new JComboBox<>(day);
      fmonthList = new JComboBox<>(month);
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
      fPanel.setLayout(new GridLayout(1, 6, 10, 10));
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

      //pPanel = new JPanel(new GridLayout(0, 1));
      aPanel = new JPanel();
      aPanel.setLayout(new GridLayout(1, 6, 2, 2));
      aPanel.add(addresslabel);
      for (JPanel el : addressrow) {
         aPanel.add(el);
      }

      nPanel = new JPanel();
      nPanel.setLayout(new GridLayout(1,6,2,2));
      nPanel.add(numberlabel);
      for (JPanel el: numberrow){
         nPanel.add(el);
      }

      cPanel = new JPanel();
      cPanel.setLayout(new GridLayout(1, 6, 2, 2));
      cPanel.add(citylabel);
      for (JPanel el : cityrow){
         cPanel.add(el);
      }

      coPanel = new JPanel();
      coPanel.setLayout(new GridLayout(1, 6, 2, 2));
      coPanel.add(codelabel);
      for (JPanel el : coderow){
         coPanel.add(el);
      }

      couPanel = new JPanel();
      couPanel.setLayout(new GridLayout(1,6,2,2));
      couPanel.add(countrylabel);
      for (JPanel el: countryrow){
          couPanel.add(el);
      }

      /*setLayout(new BorderLayout());
      setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
      setSize(new java.awt.Dimension(512, 512));
      setResizable(false);
      topPanel.add(fPanel);
      topPanel.add(tPanel);
      topPanel.add(pPanel);
      topPanel.add(controlPanel);
      add(topPanel, BorderLayout.NORTH);*/

      /*mainFrame = new JFrame("Prova");
      mainFrame.setSize(400, 400);
      mainFrame.setLayout(new GridLayout(3, 1));
      mainFrame.addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent WindowEvent) {
            System.exit(0);
         }
      });
      headerLabel = new JLabel("", JLabel.CENTER);
      statusLabel = new JLabel("", JLabel.CENTER);
      statusLabel.setSize(350, 100);
      controlPanel.setLayout(new FlowLayout());
      mainFrame.add(headerLabel);
      mainFrame.add(controlPanel);
      mainFrame.add(statusLabel);
      mainFrame.setVisible(true);*/


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
      //topPanel.add(controlPanel);
      add(topPanel, BorderLayout.NORTH);



      /*panel.add(checkbox1);
      panel.add(checkbox2);
      panel.add(checkbox3);

      frame.add(panel);

      frame.pack();
      frame.setVisible(true);*/




      /*Set<Object> options = new HashSet<>();
      options.add(new Option<Integer>("One", 1));
      options.add(new Option<Integer>("Two", 2));
      CheckBox c = new CheckBox(options);
      this.add(c);
      this.setVisible(true);
      this.setLocationRelativeTo(null);
      this.pack();
   }

   private class Option<T> implements Comparable<T> {
      private String label;
      private T value;

      public Option(String label, T value) {
         this.label = label;
         this.value = value;
      }

      public String toString() {
         return this.label;
      }

      @Override
      public int compareTo(T o) {
         return 0;
      }*/
   }

   /*private void showCheckBoxDemo(){
      headerLabel.setText("Prova1");

      final JCheckBox chk1 = new JCheckBox("Uno");
      final JCheckBox chk2 = new JCheckBox("Due");
      //final JCheckBox chk3 = new JCheckBox("Tre");

      chk1.setMnemonic(KeyEvent.VK_C);
      chk2.setMnemonic(KeyEvent.VK_M);

      chk1.addItemListener(new ItemListener() {
         @Override
         public void itemStateChanged(ItemEvent e) {
            statusLabel.setText("Uno Checkbox"
            +(e.getStateChange()==1?"checked":"unchecked"));
         }
      });

      chk2.addItemListener(new ItemListener() {
         @Override
         public void itemStateChanged(ItemEvent e) {
            statusLabel.setText("Due Checkbox"
            +(e.getStateChange()==1?"checked":"unchecked"));
         }
      });

      controlPanel.add(chk1);
      controlPanel.add(chk2);

      mainFrame.setVisible(true);
   }*/

}

   /*public class JCheckBoxList extends JList<JCheckBox>{
      protected static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);

      public JCheckBoxList(){
         setCellRenderer(new CellRendererPane());
         addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
               int index = locationToIndex(e.getPoint());
               if (index != -1) {
                  JCheckBox checkBox = (JCheckBox) getModel().getElementAt(index);
                  checkBox.setSelected(!checkBox.isSelected());
                  repaint();
               }
            }
         });
         setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      }

      public JCheckBoxList(ListModel<JCheckBox> model){
         this();
         setModel(model);
      }

      protected class CellRenderer implements ListCellRenderer<JCheckBox>{
         public Component getListCellRendererComponent(
                 JList<? extends JCheckBox> list, JCheckBox value, int index,
                 boolean isSelected, boolean cellHasFocus){
            JCheckBox checkBox = value;

            checkBox.setBackground(isSelected ? getSelectionBackground()
            : getBackground());
            checkBox.setForeground(isSelected ? getSelectionForeground()
            : getForeground());
            checkBox.setEnabled(isEnabled());
            checkBox.setFont(getFont());
            checkBox.setFocusPainted(false);
            checkBox.setBorderPainted(true);
            checkBox.setBorder(isSelected ? UIManager
               .getBorder("List.focusCellHighlightBorder") : noFocusBorder);
            return checkBox;
         }
      }
   }*/
