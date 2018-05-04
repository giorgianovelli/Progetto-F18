package gui;

import enumeration.CalendarState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

public class GUIListAssignments extends JFrame{
    final int N = 20;

    final int WIDTH = 512;
    final int HEIGHT = 512;
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );



    private JPanel contentPanel = new JPanel(); //pannello esterno
    private JScrollPane scrollPanel = new JScrollPane(contentPanel);

    private JLabel labelState = new JLabel("stato");
    private JLabel[] labelDescription = new JLabel[N];
    private JButton[] buttonAction = new JButton[N];
    private JPanel[] infoPanel = new JPanel[N]; //infopanel[i] contiene una label e un bottone

    public GUIListAssignments(CalendarState cs, GUICustomer guiCustomer){
        setTitle("Your assignments");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        initComponents(cs, guiCustomer);
    }

    private void initComponents(CalendarState cs, GUICustomer gc){


        contentPanel.setLayout((new GridLayout(infoPanel.length, 1, 5, 5)));

        if (cs.equals(CalendarState.REVIEWING)){
            setTitle("Write a review");
            for(int i = 0; i < labelDescription.length; i++){
                labelDescription[i]= new JLabel("assignment's info");
                buttonAction[i] = new JButton("Write a review");


                createPanel(i);

            }

        }
        else if (cs.equals(CalendarState.DELETING_REVIEW)){
            setTitle("Your reviews");

            for(int i = 0; i < labelDescription.length; i++){
                labelDescription[i]= new JLabel("your review's info");
                buttonAction[i]= new JButton("Delete");

                createPanel(i);
            }

        }
        else if (cs.equals(CalendarState.SHOW_REVIEWS)){
            setTitle("Your reviews");
            for(int i = 0; i < labelDescription.length; i++){
                labelDescription[i]= new JLabel("your review's info");
                buttonAction[i]= new JButton("Show more");

                createPanel(i);
            }

        } else {
            for(int i = 0; i < N; i++){
                labelDescription[i]= new JLabel("assignment's info");
                buttonAction[i]= new JButton("Info");

                createPanel(i);

                //manca il controllo della data dell'appuntamento per specificare la label dello stato
                //verde:confermato
                //rosso: già passato
                //giallo: da confermare
                //chiamo il metodo createLabelState
            }
        }

        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPanel);




        this.addWindowListener (new WindowAdapter() {
            public void windowClosing (WindowEvent we) {
                gc.setCalendarState(CalendarState.NORMAL);
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
    private void createLabelState(Date d){
        //da Fare
        //da customer vedo la lista degli assignment, controllo dateEnd e state
        //state è false(annullato), true(confermato) o null(da confermare)
    }
}
