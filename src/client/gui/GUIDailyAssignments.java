package client.gui;

import customerClient.CustomerProxy;
import server.Assignment;
import enumeration.CalendarState;
import server.Customer;
import javax.print.attribute.DocAttribute;
import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.lang.*;



public class GUIDailyAssignments extends JFrame {

    final int WIDTH = 512;
    final int HEIGHT = 512;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private JPanel p = new JPanel();
    private JButton button[] =new JButton[SwingConstants.RIGHT] ;
    private JLabel[] labelDescription =new JLabel[SwingConstants.LEFT];
    private JPanel[] infoPanel;
    private JLabel lb = new JLabel();
    private JLabel[] createLabel ;
    private JScrollPane scroll = new JScrollPane(p);
    private HashMap<Integer, Assignment> listAssigment;
    private CustomerProxy proxy;
    private String email;
    private Date todayDate ;


    public GUIDailyAssignments(CalendarState cs, String email, Date todayDate) {
        //TODO cambiare il costruttore: l'oggetto customer non sarà più accessibile

        setTitle("Daily assignments");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        setVisible(true);
        this.email = email;
        proxy = new CustomerProxy(email);
        this.listAssigment = proxy.getAssignmentList();
        this.todayDate = todayDate;
        initComponents(cs);
    }


    private void initComponents(CalendarState cs) {

        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setLayout(new GridLayout(9, 1, 20, 20));

        if (cs.equals(CalendarState.REMOVING)) {

            setTitle("Daily assignment");

            HashMap<Integer, Assignment> todayAssigment = new HashMap<>();

            int n = 0;

            for (Integer i : listAssigment.keySet()) {
                Assignment a = null;
                a = listAssigment.get(i);
                Date dateStart = a.getDateStart();
                Date dateEnd = a.getDateEnd();
                SimpleDateFormat date1 = new SimpleDateFormat("dd/MM/yyyy");
                String dateString1 = date1.format(dateStart);
                String dateStringEnd1 = date1.format(dateEnd);
                SimpleDateFormat date2 = new SimpleDateFormat("dd/MM/yyyy");
                String dateString2 = date1.format(todayDate);
                String dateStringEnd2 = date1.format(todayDate);
                dateString1.equals(dateString2);
                dateStringEnd1.equals(dateStringEnd2);

                if (dateString1.equals(dateString2) || (dateStringEnd1.equals(dateStringEnd2))) {

                    todayAssigment.put(n, a);

                }

                n++;
            }

            System.out.println(todayAssigment.size());
            labelDescription = new JLabel[todayAssigment.size()];
            button = new JButton[todayAssigment.size()];
            infoPanel = new JPanel[todayAssigment.size()];


            if (todayAssigment.isEmpty()){

                lb = new JLabel(" There aren't assignments today to be cancel ", SwingConstants.CENTER);
                add(lb, BorderLayout.NORTH);

            }

            else{

                int j = 0;

                for (Integer i : todayAssigment.keySet()) {
                    Assignment a = null;
                    String labelString = "";
                    a = todayAssigment.get(i);
                    String nameDogSitter = proxy.getDogSitterNameOfAssignment(a.getCode());
                    String surnameDogSitter = proxy.getDogSitterSurnameOfAssignment(a.getCode());
                    labelString = "<html>" + "Assignment with " + nameDogSitter + " " + surnameDogSitter + "<br/>" + a.getDateStart() + "</html>";
                    labelString = "<html>" + "Assignment with " + nameDogSitter + " " + surnameDogSitter + "<br/>" + a.getDateEnd() + "</html>";

                    labelDescription[j] = new JLabel(labelString);
                    button[j] = new JButton("Delete");
                    button[j].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            int action = JOptionPane.showConfirmDialog(null, "Are you sure to cancel ?", "Conferm Actions", JOptionPane.YES_NO_OPTION);
                            if (action == JOptionPane.YES_OPTION) {
                                proxy.removeAssignment(listAssigment.get(i).getCode()); //TODO da testare
                                dispose();
                            }

                        }
                    });

                    infoPanel[j] = new JPanel();
                    infoPanel[j].add(labelDescription[j], BorderLayout.WEST);
                    infoPanel[j].add(button[j], BorderLayout.EAST);
                    add(infoPanel[j]);
                    // p.add(infoPanel[j]); non funziona il pannello esterno !!
                    //createPanelOrder(j); non mi fa vedere piu niente 
                    j++;

                }

            }



        }



        else if (cs.equals(CalendarState.NORMAL)) {
            setTitle("Daily assignment");

            HashMap<Integer, Assignment> todayAssigment = new HashMap<>();

            int n = 0;

            for (Integer i : listAssigment.keySet()) {
                Assignment a = null;
                a = listAssigment.get(i);
                Date dateStart = a.getDateStart();
                Date dateEnd = a.getDateEnd();
                SimpleDateFormat date1 = new SimpleDateFormat("dd/MM/yyyy");
                String dateString1 = date1.format(dateStart);
                String dateStringEnd1 = date1.format(dateEnd);
                SimpleDateFormat date2 = new SimpleDateFormat("dd/MM/yyyy");
                String dateString2 = date1.format(todayDate);
                String dateStringEnd2 = date1.format(todayDate);
                dateString1.equals(dateString2);
                dateStringEnd1.equals(dateStringEnd2);
                if (dateString1.equals(dateString2) || (dateStringEnd1.equals(dateStringEnd2))) {

                    todayAssigment.put(n, a);

                }

                n++;
            }

            System.out.println(todayAssigment.size());
            labelDescription = new JLabel[todayAssigment.size()];
            button = new JButton[todayAssigment.size()];
            infoPanel = new JPanel[todayAssigment.size()];


            if  (todayAssigment.isEmpty()){

                //p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
                //lb.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 50));

                lb = new JLabel(" There aren't assignments today ",SwingConstants.CENTER);

                p.add(lb);

            }

            else {

                int j = 0;

                for (Integer i : todayAssigment.keySet()) {
                    Assignment a = null;
                    String labelString = "";
                    a = todayAssigment.get(i);
                    String nameDogSitter = proxy.getDogSitterNameOfAssignment(a.getCode());
                    String surnameDogSitter = proxy.getDogSitterSurnameOfAssignment(a.getCode());
                    labelString = "<html>" + "Assignment with " + nameDogSitter + " " + surnameDogSitter + "</html>";
                    //labelString = "<html>" + a.getDateEnd() + "<br/>" + "Assignment with " + nameDogSitter + " " + surnameDogSitter + "</html>";
                    labelDescription[j] = new JLabel(labelString);
                    button[j] = new JButton("More info");

                    ActionListener showInfo = new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            GUIAssignmentInformationCustomer assignmentInfo = new GUIAssignmentInformationCustomer(todayAssigment.get(i), email);
                            assignmentInfo.setVisible(true);

                        }
                    };

                    button[j].addActionListener(showInfo);

                    createPanelOrder(j);

                   /* infoPanel[j] = new JPanel();
                    infoPanel[j].add(labelDescription[j]);
                    infoPanel[j].add(button[j]);
                    add(infoPanel[j]);
                    p.add(infoPanel[j]);*/

                    j++;


                }
            }
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            getContentPane().add(scroll);


        }
    }



       private void createPanelOrder(int i) {

       infoPanel[i] = new JPanel();


       infoPanel[i].setLayout(new BorderLayout());
       infoPanel[i].setMaximumSize(new Dimension(450,100));

       labelDescription[i].setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 50));

       infoPanel[i].add(labelDescription[i], BorderLayout.CENTER);
       infoPanel[i].add(button[i], BorderLayout.EAST);
       infoPanel[i].setBorder(BorderFactory.createEmptyBorder(15,10,5,15)); //5,5,5,5

       p.add(infoPanel[i]);

    }


}

// 1. manca da migliorare il layout del Delete assignment se c'e un appuntamento che non funziona con il box layout
// 2. Il metodo che cancella realmente l'appuntamento dal db - DEVE ESSERE CONTROLATO
