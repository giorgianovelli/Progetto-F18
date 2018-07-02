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

    //TODO refresh della finestra?

    private JPanel contentPanel = new JPanel(); //pannello esterno
    private JScrollPane scrollPanel = new JScrollPane(contentPanel);

    private JLabel labelState[];
    private JLabel labelStr;

    private JLabel[] labelDescription; //non va a capo, trovare un alternativa ok
    private JButton[] buttonAction;
    private JPanel[] infoPanel;  //infopanel[i] contiene una label e un bottone
    private HashMap<Integer, Assignment> listAssignment;
    private HashMap<Integer, Review> listReview;
    private CustomerProxy proxy;
    private String email;

    /**
     *
     * @param cs ??
     * @param listAssignment lista degli appuntamenti dell'utente
     * @param email riferimento all'utente
     * @param guiCustomer
     */

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

    /**
     *
     * @param cs ??
     * @param guiCustomer
     */

    private void initComponents(CalendarState cs, GUICustomer guiCustomer){

        assignmentNumber = listAssignment.size();
        reviewNumber = listReview.size();

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

        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        if (cs.equals(CalendarState.REVIEWING)){
            setTitle("Write a review");

            int j = 0;
            boolean haveAReview = false;//Fa vedere solo gli appuntamenti che non hanno ancora una recensione

            for(Integer i : listAssignment.keySet()){
                Assignment a = null;
                String labelString = "";

                a = listAssignment.get(i);
                haveAReview = false;

                for (Integer k : listReview.keySet()){
                    Review r = null;
                    r = listReview.get(k);
                    if(a.getCode()== r.getCode()){
                        haveAReview = true;
                    }
                }

                if(!haveAReview){
                    String nameDogSitter = proxy.getDogSitterNameOfAssignment(a.getCode());
                    String surnameDogSitter = proxy.getDogSitterSurnameOfAssignment(a.getCode());
                    SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    date.setLenient(false);
                    Date startAssignment = a.getDateStart();
                    String dateStringStartAssigment = date.format(startAssignment);

                    labelString = "<html>" + "Assignment with " + nameDogSitter + " " + surnameDogSitter + "<br/>" + dateStringStartAssigment +  "</html>";

                    labelDescription[j]= new JLabel(labelString);
                    buttonAction[j]= new JButton("Write a review");
                    buttonAction[j].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            GUIWriteReview writeReview = new GUIWriteReview(listAssignment.get(i), email);
                            writeReview.setVisible(true);
                            dispose();

                        }

                    });

                    createPanelReview(j);

                }


                j++;
            }
            if (haveAReview) {
                labelStr = new JLabel("There aren't assignment to review!");
                labelStr.setFont(new Font("TimesRoman", Font.PLAIN, 15));
                contentPanel.add(labelStr);
            }


        }
        else if (cs.equals(CalendarState.DELETING_REVIEW)){
            setTitle("Your reviews");

            int j = 0;
            for(Integer i: listReview.keySet()){
                Review r = null;
                String labelString;
                r = listReview.get(i);

                SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy HH:mm ");
                date.setLenient(false);
                Date reviewDate = r.getDate();
                String dateStringReview = date.format(reviewDate);

                labelString = "<html>" + "Assignment with " + proxy.getDogSitterNameOfAssignment(r.getCode()) + " " + proxy.getDogSitterSurnameOfAssignment(r.getCode()) +"<br/>"+ dateStringReview +"<br/>" + r.getTitle() +"<br/>" + "Vote: " + r.getRating() + "</html>";
                labelDescription[j]= new JLabel(labelString);
                buttonAction[j]= new JButton("Delete review");
                buttonAction[j].addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //proxy.removeReview((listAssignment.get(i)).getCode());
                        int action = (JOptionPane.showConfirmDialog(null,"Are you sure you want to delete?","Conferm Delete Review",JOptionPane.YES_NO_OPTION));
                        if (action == JOptionPane.YES_OPTION){
                            proxy.removeReview((listAssignment.get(i)).getCode()); //TODO controllare!!
                        }
                        dispose();

                    }

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

                SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                date.setLenient(false);
                Date reviewDate = r.getDate();
                String dateStringReview = date.format(reviewDate);

                labelString = "<html>" + "Assignment with " + proxy.getDogSitterNameOfAssignment(r.getCode()) + " " + proxy.getDogSitterSurnameOfAssignment(r.getCode()) +"<br/>"+ dateStringReview +"<br/>" + r.getTitle() +"<br/>" + "Vote: " + r.getRating() + "</html>";
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



    /**
     * crea un JPanel che contiene le informazioni dell'appuntamento: stato, descrizione, bottone
     * @param a riferimento all'appuntamento per creare la label dello stato
     * @param i indice del JPanel
     */
    private void createPanelAssignment(Assignment a, int i){
        infoPanel[i] = new JPanel();

        infoPanel[i].setLayout(new BorderLayout());
        //infoPanel[i].setMinimumSize(new Dimension(450, 100));
        //infoPanel[i].setPreferredSize(new Dimension(450, 100));
        infoPanel[i].setMaximumSize(new Dimension(450,100));

        labelState[i] = createLabelState(a);
        labelState[i].setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        infoPanel[i].add(labelState[i], BorderLayout.WEST);


        infoPanel[i].add(labelDescription[i], BorderLayout.CENTER);
        infoPanel[i].add(buttonAction[i], BorderLayout.EAST);

        infoPanel[i].setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        contentPanel.add(infoPanel[i]);

    }

    /**
     * crea un JPanel con la descrizione degli appuntamenti per la visualizzazione delle recensioni
     * @param i indice del JPanel
     */
    private void createPanelReview (int i){
        infoPanel[i] = new JPanel();

        infoPanel[i].setLayout(new BorderLayout());
        infoPanel[i].setMaximumSize(new Dimension(450,100));
        labelDescription[i].setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 50));

        infoPanel[i].add(labelDescription[i], BorderLayout.CENTER);

        infoPanel[i].add(buttonAction[i], BorderLayout.EAST);
        infoPanel[i].setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        contentPanel.add(infoPanel[i]);
    }


    /**
     * metodo per settare il colore della labelState
     * @param a riferimento all'appuntamento
     * @return JLabel con il colore che indica lo stato dell'appuntamento
     *          verde: confermato
     *          giallo: da confermare
     *          grigio: già passato
     */
    private JLabel createLabelState(Assignment a){
        //da controllare il funzionamento

        JLabel label = new JLabel();
        Date todayDate= new Date(System.currentTimeMillis());

        ImageIcon green = transformImage(new ImageIcon("images/Green_square.svg.png"), 30,30);
        ImageIcon gray = transformImage(new ImageIcon("images/gray_square.png"), 30,30);
        ImageIcon yellow = transformImage(new ImageIcon("images/yellow.jpg"), 30, 30);

        Boolean state = a.getState();

        if(a.getDateEnd().before(todayDate)){
            label = new JLabel(gray);

        }
        else if (state == Boolean.TRUE){
            label = new JLabel(green);
        }
        else {
            label = new JLabel(yellow);
        }

        return label;

    }

    /**
     *
     * @param icon immagine da modificare
     * @param width nuova larghezza
     * @param height nuova altezza
     * @return
     */

    private ImageIcon transformImage(ImageIcon icon, int width, int height){
        Image imageTransform = icon.getImage();
        Image newImage = imageTransform.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImage);

        return icon;
    }







}
