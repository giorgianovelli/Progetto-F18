package customerClient.gui;

import customerClient.CustomerProxy;
import database.DBConnector;
import server.Assignment;
import server.Customer;
import server.Review;
import enumeration.CalendarState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class GUIListAssignments extends JFrame{



    private int assignmentNumber, reviewNumber;

    final int WIDTH = 512;
    final int HEIGHT = 512;
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );



    private JPanel contentPanel = new JPanel(); //pannello esterno
    private JScrollPane scrollPanel = new JScrollPane(contentPanel);

    private JLabel labelState[]; //TODO
    private JLabel[] labelDescription; //non va a capo, trovare un alternativa
    private JButton[] buttonAction;
    private JPanel[] infoPanel;  //infopanel[i] contiene una label e un bottone
    //private Customer customer;
    private HashMap<Integer, Assignment> listAssignment;
    private HashMap<Integer, Review> listReview;
    private CustomerProxy proxy;
    private String email;
    private Assignment assignment;

    public GUIListAssignments(CalendarState cs, HashMap<Integer, Assignment> listAssignment, String email, GUICustomer guiCustomer){
        setTitle("Your assignments");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        this.listAssignment = listAssignment;
        this.email = email;
        this.proxy = new CustomerProxy(email);
        listReview = proxy.getReviewList();


        initComponents(cs, guiCustomer);
    }

    private void initComponents(CalendarState cs, GUICustomer guiCustomer){


        assignmentNumber = listAssignment.size();
        reviewNumber = listReview.size();

        if(cs.equals(CalendarState.DELETING_REVIEW)|| cs.equals(CalendarState.SHOW_REVIEWS)){ //da controllare
            infoPanel = new JPanel[reviewNumber];
            labelDescription = new JLabel[reviewNumber];
            buttonAction = new JButton[reviewNumber];

        } else {
            infoPanel = new JPanel[assignmentNumber];
            labelDescription = new JLabel[assignmentNumber];
            buttonAction = new JButton[assignmentNumber];
            labelState = new JLabel[assignmentNumber];
        }

        contentPanel.setLayout(new GridLayout(infoPanel.length,1, 5,5));


        if (cs.equals(CalendarState.REVIEWING)){
            setTitle("Write a review");

            ActionListener write = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GUIWriteReview writeReview = new GUIWriteReview();
                    writeReview.setVisible(true);
                }
            };


            int j = 0;
            for(Integer i : listAssignment.keySet()){
                Assignment a = null;
                String labelString = "";

                a = listAssignment.get(i);
                String nameDogSitter = proxy.getDogSitterNameOfAssignment(a.getCode());
                String surnameDogSitter = proxy.getDogSitterSurnameOfAssignment(a.getCode());
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                date.setLenient(false);
                Date startAssignment = a.getDateStart();
                String dateStringStartAssigment = date.format(startAssignment);

                labelString = "<html>" + dateStringStartAssigment + "<br/>" + "Assignment with " + nameDogSitter + " " + surnameDogSitter + "</html>";

                labelDescription[j]= new JLabel(labelString);
                buttonAction[j]= new JButton("Write a review");
                buttonAction[j].addActionListener(write);
                createPanelReview(j);
                j++;
            }






        }
        else if (cs.equals(CalendarState.DELETING_REVIEW)){ //DA controllare!!!!!
            setTitle("Your reviews");

            int j = 0;
            for(Integer i: listReview.keySet()){
                Review r = null;
                String labelString;
                r = listReview.get(i);

                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                date.setLenient(false);
                Date reviewDate = r.getDate();
                String dateStringReview = date.format(reviewDate);

                labelString = dateStringReview + " " + r.getTitle();
                labelDescription[j]= new JLabel(labelString);
                buttonAction[j]= new JButton("Delete review");
                buttonAction[j].addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showConfirmDialog(null,"Are you sure you want to delete?","Conferm Delete Review",JOptionPane.YES_NO_OPTION);}
                });

                createPanelReview(j);
                j++;

            }


        }
        else if (cs.equals(CalendarState.SHOW_REVIEWS)){ //DA controllare!!!!!
            setTitle("Your reviews");


            int j = 0;
            for(Integer i: listReview.keySet()){
                Review r = null;
                String labelString;
                r = listReview.get(i);

                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                date.setLenient(false);
                Date reviewDate = r.getDate();
                String dateStringReview = date.format(reviewDate);

                labelString = dateStringReview + " " + r.getTitle();
                labelDescription[j]= new JLabel(labelString);
                buttonAction[j]= new JButton("Show more");
                buttonAction[j].addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        GUIShowReview showReview = new GUIShowReview(listReview.get(i));
                        showReview.setVisible(true);
                    }

                });

                createPanelReview(j);
                j++;

            }


        } else {
            int j = 0;

            for(Integer i : listAssignment.keySet()){
                Assignment a = null;

                a = listAssignment.get(i);

                String nameDogSitter = proxy.getDogSitterNameOfAssignment(a.getCode());
                String surnameDogSitter = proxy.getDogSitterSurnameOfAssignment(a.getCode());

                ActionListener showInfo = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        GUIAssignmentInformationCustomer assignmentInfo = new GUIAssignmentInformationCustomer(listAssignment.get(i), email);
                        assignmentInfo.setVisible(true);

                    }
                };




                labelDescription[j]= new JLabel("Assignment with " + nameDogSitter + " " + surnameDogSitter);
                buttonAction[j]= new JButton("Info");
                buttonAction[j].addActionListener(showInfo);


                createPanelAssignment(a,j);


                j++;

            }



        }

        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPanel);



        //problema: se voglio vedere di nuovo la finestra di show all assignment dopo aver visto quella delle review, rimane quell delle review
        //RISOLTO con questo pezzo di codice
        this.addWindowListener (new WindowAdapter() {
            public void windowClosing (WindowEvent we) {
                guiCustomer.setCalendarState(CalendarState.NORMAL);
            }
         });
    }

    //metodo che crea infoPanel[i] e gli assegna bottone e label
    private void createPanelAssignment(Assignment a, int i){
        infoPanel[i] = new JPanel();

        infoPanel[i].setLayout(new BorderLayout());

        labelState[i] = createLabelState(a,i);
        labelState[i].setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        infoPanel[i].add(labelState[i], BorderLayout.WEST);


        infoPanel[i].add(labelDescription[i], BorderLayout.CENTER);
        infoPanel[i].add(buttonAction[i], BorderLayout.EAST);

        contentPanel.add(infoPanel[i]);

    }

    private void createPanelReview (int i){
        infoPanel[i] = new JPanel();

        infoPanel[i].setLayout(new BorderLayout());

        infoPanel[i].setPreferredSize(new Dimension(480,40));
        labelDescription[i].setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 50));
        infoPanel[i].add(labelDescription[i], BorderLayout.CENTER);
        infoPanel[i].add(buttonAction[i], BorderLayout.EAST);

        contentPanel.add(infoPanel[i]);
    }


    //metodo per settare il colore della labelState
    private JLabel createLabelState(Assignment a, int i){
        //da controllare il funzionamento


        Date todayDate= new Date(System.currentTimeMillis());

        ImageIcon green = new ImageIcon("images/Green_square.svg.png");
        Image imageTransform = green.getImage(); // transform it
        Image newImage = imageTransform.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        green = new ImageIcon(newImage);  // transform it back


        ImageIcon gray = new ImageIcon("images/gray_square.png");
        imageTransform = gray.getImage(); // transform it
        newImage = imageTransform.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        gray = new ImageIcon(newImage);  // transform it back

        ImageIcon yellow = new ImageIcon("images/yellow.jpg");
        imageTransform = yellow.getImage(); // transform it
        newImage = imageTransform.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        yellow = new ImageIcon(newImage);  // transform it back
        Boolean state = a.getState();
        System.out.println(state);

        if(a.getDateEnd().before(todayDate)){
            labelState[i]= new JLabel(gray);

        }
        else if (state == Boolean.TRUE){
            labelState[i]= new JLabel(green);
        }
        else {
            labelState[i]= new JLabel(yellow);
        }

        return labelState[i];

    }







}
