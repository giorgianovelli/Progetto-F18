package gui;

import java.awt.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class GUINewAssignment extends JFrame {


   private JPanel topPanel, fPanel, tPanel, pPanel;
   private List<JPanel> frow = new ArrayList<>();
   private JLabel flabel;
   private JLabel plabel;
   private List<JPanel> trow = new ArrayList<>();
   private List<JPanel> prow = new ArrayList<>();
   private JLabel tlabel;
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
   private JPanel positionPanel;
   private JComboBox<String> fdayList, tdayList;
   private JComboBox<String> fmonthList, tmonthList;
   private JComboBox<String> fyearList, tyearList;
   private JComboBox<String> fhourList, thourList;
   private JComboBox<String> fminuteList, tminuteList;
   private TextField position;

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
      positionPanel = new JPanel();

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
      position = new TextField("", 20);
      

      fdayPanel.add(fdayList);
      fmonthPanel.add(fmonthList);
      fyearPanel.add(fyearList);
      fhourPanel.add(fhourList);
      fminutePanel.add(fminuteList);
      positionPanel.add(position);

      flabel = new JLabel("From: ");
      plabel = new JLabel("Position: ");


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

      topPanel = new JPanel(new GridLayout(3, 1));
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

      prow.add(positionPanel);
      pPanel = new JPanel(new GridLayout(0, 1));
      pPanel = new JPanel();
      pPanel.setLayout(new GridLayout(1, 6, 2, 2));
      pPanel.add(plabel);
      for (JPanel el : prow) {
         pPanel.add(el);
     }

      setLayout(new BorderLayout());
      setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
      setSize(new java.awt.Dimension(512, 512));
      setResizable(false);
      topPanel.add(fPanel);
      topPanel.add(tPanel);
      topPanel.add(pPanel);
      add(topPanel, BorderLayout.NORTH);

   }
}
