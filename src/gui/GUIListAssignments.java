package gui;

import enumeration.CalendarState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUIListAssignments extends JFrame{
    final int N = 20;

    final int WIDTH = 512;
    final int HEIGHT = 512;
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );



    private JPanel infoPanel = new JPanel();
    private JScrollPane scrollPanel = new JScrollPane(infoPanel); //non funziona

    //private JLabel labelState = new JLabel("stato"); ancora da fare
    private JLabel[] labelDescription = new JLabel[N];
    private JButton[] buttonAction = new JButton[N];

    public GUIListAssignments(CalendarState cs, GUICustomer guiCustomer){
        setTitle("Your assignments");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        setFont(new Font("Serif", Font.BOLD, 26));



        initComponents(cs, guiCustomer);
    }

    private void initComponents(CalendarState cs, GUICustomer gc){


        infoPanel.setLayout(new GridLayout(labelDescription.length,2));

        if (cs.equals(CalendarState.REVIEWING)){
            setTitle("Write a review");
            for(int i = 0; i < labelDescription.length; i++){
                labelDescription[i]= new JLabel("assignment's info");
                buttonAction[i]= new JButton("Write a review");

                infoPanel.add(labelDescription[i]);
                infoPanel.add(buttonAction[i]);
            }

        }
        else if (cs.equals(CalendarState.DELETING_REVIEW)){
            setTitle("Your reviews");

            for(int i = 0; i < labelDescription.length; i++){
                labelDescription[i]= new JLabel("your review's info");
                buttonAction[i]= new JButton("Delete");

                infoPanel.add(labelDescription[i]);
                infoPanel.add(buttonAction[i]);
            }

        }
        else if (cs.equals(CalendarState.SHOW_REVIEWS)){
            setTitle("Your reviews");
            for(int i = 0; i < labelDescription.length; i++){
                labelDescription[i]= new JLabel("your review's info");
                buttonAction[i]= new JButton("Show more");

                infoPanel.add(labelDescription[i]);
                infoPanel.add(buttonAction[i]);
            }

        } else {
            for(int i = 0; i < N; i++){
                labelDescription[i]= new JLabel("assignment's info");
                buttonAction[i]= new JButton("Info");

                infoPanel.add(labelDescription[i]);
                infoPanel.add(buttonAction[i]);

                //manca il controllo della data dell'appuntamento per specificare la label dello stato
                //verde:confermato
                //rosso: già passato
                //giallo: da confermare
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
}
