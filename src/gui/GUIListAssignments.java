package gui;

import database.DBConnector;
import engine.Assignment;
import engine.Customer;
import engine.DogSitter;
import engine.Review;
import enumeration.CalendarState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import static staticClasses.ObjectCreator.createDogSitterFromDB;
import static staticClasses.StringManipulator.capitalizeFirstLetter;

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

    public GUIListAssignments(CalendarState cs, Customer customer, GUICustomer guiCustomer){
        setTitle("Your assignments");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        this.customer = customer;

        initComponents(cs, customer, guiCustomer);
    }

    private void initComponents(CalendarState cs, Customer customer, GUICustomer guiCustomer){
        //DA FARE: dicitura esatta per la labelDescription, stato dell'assignment,
        // e vedere se funziona la visualizzazione delle recensioni
        //vedere se funzionano le query con sottoquery


        assignmentNumber = customer.getAssignmentList().size();
        reviewNumber = customer.getReviewList().size();


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

        //contentPanel.setLayout((new GridLayout(infoPanel.length, 1, 5, 5)));



        DBConnector dbConnector = new DBConnector();


        if (cs.equals(CalendarState.REVIEWING)){
            setTitle("Write a review");

            for(Integer i = 0; i < infoPanel.length; i++){
                Assignment a = null;
                //non completo!
                try{
                    int j = i + 1;
                    String s = "";
                    a = customer.getAssignmentList().get(j);
                    ResultSet rs = dbConnector.askDB("SELECT DOGSITTER FROM ASSIGNMENT WHERE CODE = '"+ a.getCode()+ "'");
                    rs.next();

                    s += "Assignment with " + rs.getString("DOGSITTER") ;


                   /*ResultSet rs = dbConnector.askDB("SELECT NAME, SURNAME FROM DOGSITTERS WHERE EMAIL = " +
                            "(SELECT DOGSITTER FROM ASSIGNMENT WHERE DOGSITTER = '"+ a.getCode()+ "'");
                    rs.next();
                    s = "Assignment with " + rs.getString("NAME") + " " + rs.getString("SURNAME");
                    NON FUNZIONA!!

                    */

                    labelDescription[i]= new JLabel(s);
                    buttonAction[i]= new JButton("Write a review");

                    createPanel(i);

                }catch(SQLException e){
                    System.out.println(e.getMessage());
                }

            }


        }
        else if (cs.equals(CalendarState.DELETING_REVIEW)){
            setTitle("Your reviews");
            for(Integer i = 0; i < infoPanel.length; i++){
                Review r = null;

                int j= i+1;
                r = customer.getReviewList().get(j);
                String s = "Review for " + r.toString();

                labelDescription[i]= new JLabel(s);
                buttonAction[i]= new JButton("Delete review");

                createPanel(i);
            }



        }
        else if (cs.equals(CalendarState.SHOW_REVIEWS)){
            setTitle("Your reviews");

            for(Integer i = 0; i < infoPanel.length; i++){
                Review r = null;

                int j= i+1;
                r = customer.getReviewList().get(j);
                String s = "Review for " + r.toString();
                labelDescription[i]= new JLabel(s);
                buttonAction[i]= new JButton("Show more");

                createPanel(i);
            }


        } else {
            setTitle("Your assignments");
            for(Integer i = 0; i < infoPanel.length; i++){
                Assignment a = null;
                try{
                    int j = i + 1;
                    String s = "";
                    a = customer.getAssignmentList().get(j);
                    ResultSet rs = dbConnector.askDB("SELECT DOGSITTER FROM ASSIGNMENT WHERE CODE = '"+ a.getCode()+ "'");
                    rs.next();

                    s += "Assignment with " + rs.getString("DOGSITTER") ;


                   /* ResultSet rs = dbConnector.askDB("SELECT NAME, SURNAME FROM DOGSITTERS WHERE EMAIL = " +
                            "(SELECT DOGSITTER FROM ASSIGNMENT WHERE DOGSITTER = '"+ a.getCode()+ "'");
                    s = "Assignment with " + rs.getString("NAME") + " " + rs.getString("SURNAME");

                    */

                    labelDescription[i]= new JLabel(s);
                    buttonAction[i]= new JButton("Info");

                    createPanel(i);

                }catch(SQLException e){
                    System.out.println(e.getMessage());
                }

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



        //problema: se voglio vedere di nuovo la finestra di show all assignment dopo aver visto quella delle review, rimane quell delle review
        //RISOLTO con questo pezzo di codice
        this.addWindowListener (new WindowAdapter() {
            public void windowClosing (WindowEvent we) {
                guiCustomer.setCalendarState(CalendarState.NORMAL);
            }
         });
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
    private void createLabelState(Assignment a){
        //da Fare
        //da customer vedo la lista degli assignment, controllo dateEnd e state
        //state è false(annullato), true(confermato) o null(da confermare)
        
    }





}
