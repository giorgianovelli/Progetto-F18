package customerClient.gui;

import customerClient.CustomerProxy;
import server.Assignment;
import server.Customer;
import enumeration.CalendarState;
import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.*;
import java.util.Date;
import java.util.HashMap;

public class GUIDailyAssignments extends JFrame {
    final int WIDTH = 512;
    final int HEIGHT = 512;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private JPanel p = new JPanel();
    private JButton button[];
    private JLabel[] labelDescription;
    private JPanel[] infoPanel;
    private JLabel lb = new JLabel();
    private JScrollPane scroll = new JScrollPane(p);
    private JButton yes = new JButton("Yes");
    private JButton no = new JButton("No");
   // private JPanel panelButton = new JPanel();
   // private JPanel panelout = new JPanel();
    private HashMap<Integer, Assignment> listAssigment;
    private String todayDate;
    private CustomerProxy proxy;


    public GUIDailyAssignments(CalendarState cs,HashMap<Integer,Assignment> listAssigment ,String todayDate) {
        setTitle("Daily assignments");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        setVisible(true);
        initComponents(cs);
        this.listAssigment = listAssigment;
        this.todayDate = todayDate;
    }

    private void initComponents(CalendarState cs) {
        //inserire query per interrogare db
     /*   HashMap<Integer,Assignment> todayAssigment = new HashMap<>();
        for(Integer i: listAssigment.keySet()) {
            Assignment a = null;
            a = listAssigment.get(i);
            if ((a.getDateStart().equals(todayDate) || a.getDateEnd().equals(todayDate))) {
            todayAssigment.put(i,a);
            }

        }
        int nAssignments = todayAssigment.size();
       // panelout.add(panelButton);
        button = new JButton[nAssignments];

        if (cs.equals(CalendarState.REMOVING)) {
            infoPanel = new JPanel[nAssignments];
            labelDescription = new JLabel[nAssignments];

            setTitle("Daily assignment");
            p.setLayout(new GridLayout(button.length, 2));
            ActionListener write = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }

            };

            int j = 0;
            for (Integer i : todayAssigment.keySet()) {
                Assignment a = null;
                String labelString = "";

                a = todayAssigment.get(i);
                String nameDogSitter = proxy.getDogSitterNameOfAssignment(a.getCode());
                String surnameDogSitter = proxy.getDogSitterSurnameOfAssignment(a.getCode());

                labelString = "<html>" + a.getDateStart() + "<br/>" + "Assignment with " + nameDogSitter + " " + surnameDogSitter + "</html>";

                labelDescription[j] = new JLabel(labelString);
                button[j] = new JButton("Delete");
                button[j].addActionListener(write);

                j++;
            }
        }*/
           /* for (int i = 0; i < nAssignments; i++) {
                int n = i + 1;
                lb = new JLabel("Daily assignment n° " + n);
                button[i] = new JButton("Delete");
                p.add(lb);
                p.add(button[i]);

               // lb.setText("Delete");
               // panelButton.add(yes);
               // panelButton.add(no);
                panelButton.add(button[6]);
                add(panelout);
                button[nAssignments].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        if (e.getActionCommand().equals("Delete")){

                        }
                        JOptionPane.showMessageDialog(new JFrame(),"Are you sure ?", "Conferm Action", JOptionPane.YES_NO_OPTION);


                    }
                });
            }*//*


          else if(cs.equals(CalendarState.NORMAL)) {
        setTitle("Daily assignment");
        p.setLayout(new GridLayout(nAssignments, 2));
        for (int i = 0; i < nAssignments; i++) {
            int n = i + 1;
            lb = new JLabel("Daily assignment n° " + n);
            button[i] = new JButton("Info");
            p.add(lb);
            p.add(button[i]);
        }


    }*/
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().add(scroll);


}

    }


