package gui;

import database.DBConnector;
import engine.Assignment;
import engine.Customer;
import enumeration.CalendarState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class GUIListAssignments extends JFrame{
    private int assignmentNumber, reviewNumber;

    final int WIDTH = 512;
    final int HEIGHT = 512;
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );



    private JPanel contentPanel = new JPanel(); //pannello esterno
    private JScrollPane scrollPanel = new JScrollPane(contentPanel);

    private JLabel labelState[]; //Da Fare
    private JLabel[] labelDescription; //non va a capo, trovare un alternativa
    private JButton[] buttonAction;
    private JPanel[] infoPanel;  //infopanel[i] contiene una label e un bottone
    private Customer customer;

    public GUIListAssignments(CalendarState cs, Customer customer){
        setTitle("Your assignments");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        this.customer = customer;

        initComponents(cs, customer);
    }

    private void initComponents(CalendarState cs, Customer customer){

        assignmentNumber = customer.getAssignmentList().size();
        reviewNumber = customer.listReview().size();
        //System.out.println("ho letto:" + N); //OK fino a qua!

        if(cs.equals(CalendarState.DELETING_REVIEW)|| cs.equals(CalendarState.SHOW_REVIEWS)){
            infoPanel = new JPanel[reviewNumber];
            labelDescription = new JLabel[reviewNumber];
            buttonAction = new JButton[reviewNumber];

        } else {
            infoPanel = new JPanel[assignmentNumber];
            labelDescription = new JLabel[assignmentNumber];
            buttonAction = new JButton[assignmentNumber];
            labelState = new JLabel[assignmentNumber];
        }

        //contentPanel.setLayout((new GridLayout(infoPanel.length, 1, 5, 5)));

        Assignment a = null;
        if (cs.equals(CalendarState.REVIEWING)){
            setTitle("Write a review");


            for(Integer i = 0; i < infoPanel.length; i++){
                int j= i+1;
                a = customer.getAssignmentList().get(j);
                String s = Integer.toString(a.getCode()) + " " + a.getDateStart() +  " " + a.getDateEnd();
                labelDescription[i]= new JLabel(s);
                buttonAction[i]= new JButton("Write a review");

                createPanel(i);
            }


        }
        else if (cs.equals(CalendarState.DELETING_REVIEW)){
            setTitle("Your reviews");
            /*for(Integer i = 0; i < infoPanel.length; i++){
                int j= i+1;
                a = customer.listReview().get(j);
                String s = Integer.toString(a.getCode()) + " \n " + a.getDateStart() + a.getDateEnd();
                labelDescription[i]= new JLabel(s);
                buttonAction[i]= new JButton("Delete review");

                createPanel(i);
            }*/



        }
        else if (cs.equals(CalendarState.SHOW_REVIEWS)){
            setTitle("Your reviews");

            /*for(Integer i = 0; i < infoPanel.length; i++){
                int j= i+1;
                a = customer.listReview().get(j);
                String s = Integer.toString(a.getCode()) + " \n " + a.getDateStart() + a.getDateEnd();
                labelDescription[i]= new JLabel(s);
                buttonAction[i]= new JButton("Show more");

                createPanel(i);
            }*/


        } else {
            for(Integer i = 0; i < infoPanel.length; i++){
                int j= i+1;
                a = customer.getAssignmentList().get(j);
                String s = Integer.toString(a.getCode()) + " \n " + a.getDateStart() + a.getDateEnd();
                labelDescription[i]= new JLabel(s);
                buttonAction[i]= new JButton("Info");


                createPanel(i);
            }



                //manca il controllo della data dell'appuntamento per specificare la label dello stato
                //verde:confermato
                //rosso: già passato
                //giallo: da confermare
                //chiamo il metodo createLabelState

        }

        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPanel);




        /*this.addWindowListener (new WindowAdapter() {
            public void windowClosing (WindowEvent we) {
                gc.setCalendarState(CalendarState.NORMAL);
            }
         });*/
    }

    //metodo che crea infoPanel[i] e gli assegna bottone e label
    private void createPanel(int i){
        infoPanel[i] = new JPanel();
        infoPanel[i].setLayout(new BorderLayout());
        infoPanel[i].add(labelDescription[i], BorderLayout.CENTER);
        infoPanel[i].add(buttonAction[i], BorderLayout.EAST);
        contentPanel.add(infoPanel[i]);
    }


    //metodo per settare il colore della labelState
    private void createLabelState(Date d){
        //da Fare
        //da customer vedo la lista degli assignment, controllo dateEnd e state
        //state è false(annullato), true(confermato) o null(da confermare)
    }





}
