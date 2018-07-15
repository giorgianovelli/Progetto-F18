package client.gui;

import client.Calendar;
import client.proxy.CustomerProxy;
import server.Assignment;
import server.Review;
import enumeration.CalendarState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class GUIListAssignments extends JFrame{

    protected int assignmentNumber, reviewNumber;

    final int WIDTH = 512;
    final int HEIGHT = 512;
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );

    protected GridLayout gridLayout = new GridLayout(1,1);

    protected JPanel contentPanel = new JPanel(); //pannello esterno
    protected JPanel panelOut = new JPanel();
    protected JScrollPane scrollPanel = new JScrollPane(panelOut);


    protected JLabel labelState[];

    protected JLabel[] labelDescription; //non va a capo, trovare un alternativa ok
    protected JButton[] buttonAction;
    protected JPanel[] infoPanel;  //infopanel[i] contiene una label e un bottone
    protected JPanel panelLabel;
    protected JPanel panelButtons;

    protected HashMap<Integer, Assignment> listAssignment;
    protected HashMap<Integer, Review> listReview;
    protected HashMap<CalendarState,String> strLabel;
    private CustomerProxy proxy;
    protected String email;

    private GUIListAssignments guiListAssignments; // necessario per disattivazione @Riccardo

    /**
     * costruttore
     * @param cs idenfica il menu da cui viene richiamata questa interfaccia
     * @param email riferimento all'utente
     * @param guiCustomer identifica l'interfaccia da cui viene richiamata
     */

    public GUIListAssignments(CalendarState cs, HashMap<Integer, Assignment> listAssignment,  String email, GUIHome guiCustomer){
        setTitle("Your assignments");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        guiListAssignments = this;

        this.listAssignment = listAssignment;
        this.email = email;

        initComponents(cs, guiCustomer);
    }

    /**
     * inzializza le componenti dell'interfaccia
     * @param cs idenfica il menu da cui viene richiamata questa interfaccia
     * @param guiCustomer identifica l'interfaccia da cui viene richiamata
     */
    protected void initComponents(CalendarState cs, GUIHome guiCustomer){

        UIManager.put("OptionPane.noButtonText", "No");
        UIManager.put("OptionPane.yesButtonText", "Yes");

        this.proxy = new CustomerProxy(email);
        this.listReview = proxy.getReviewList();


        assignmentNumber = listAssignment.size();
        reviewNumber = listReview.size();

        initArray(cs);

        contentPanel.setLayout(gridLayout);
        panelOut.setLayout(new BorderLayout());

        if (cs.equals(CalendarState.REVIEWING)){
            setTitle("Write a review");

            HashMap<Integer, Assignment> newListAssignment = assignmentBeforeToday(); //nuova lista degli appuntamente precedenti alla data attuale

            int j = 0;
            int review = 0;
            boolean haveAReview = false;//Fa vedere solo gli appuntamenti che non hanno ancora una recensione

            for(Integer i : newListAssignment.keySet()){
                Assignment a = null;

                a = listAssignment.get(i);
                haveAReview = false;

                for (Integer k : listReview.keySet()){
                    Review r = null;
                    r = listReview.get(k);
                    if(a.getCode()== r.getCode()){
                        haveAReview = true;
                        review++;
                    }
                }

                if(!haveAReview){

                    setComponents(setLabelString(cs, a, null), "Write a review", j);

                    buttonAction[j].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            GUIWriteReview writeReview = new GUIWriteReview(listAssignment.get(i), email);
                            writeReview.setVisible(true);
                            dispose();

                        }

                    });

                    createPanelReview(j);
                    gridLayout.setRows(gridLayout.getRows() + 1);


                }


                j++;
            }

            noAssignmentToReview(review,newListAssignment);

        }
        else if (cs.equals(CalendarState.DELETING_REVIEW)){
            setTitle("Your reviews");

            if(reviewNumber == 0){
                createLabelInfo("There aren't reviews to show!");
            }
            else {

                int j = 0;
                for (Integer i : listReview.keySet()) {
                    Review r = null;
                    r = listReview.get(i);

                    setComponents(setLabelString(cs, null, r), "Delete review", j);
                    buttonAction[j].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //proxy.removeReview((listAssignment.get(i)).getCode());
                            int action = (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?", "Confirm Delete Review", JOptionPane.YES_NO_OPTION));
                            if (action == JOptionPane.YES_OPTION) {
                                proxy.removeReview((listAssignment.get(i)).getCode()); //TODO controllare!!
                            }
                            dispose();

                        }

                    });

                    createPanelReview(j);
                    gridLayout.setRows(gridLayout.getRows() + 1);
                    j++;

                }
            }


        }
        else if (cs.equals(CalendarState.SHOW_REVIEWS)){ //DA controllare!!!!!
            setTitle("Your reviews");
            if(reviewNumber == 0){
                createLabelInfo("There aren't reviews to show!");
            }
            else
            {
                int j = 0;
                for(Integer i: listReview.keySet()){
                    Review r = null;
                    r = listReview.get(i);

                    setComponents(setLabelString(cs, null, r), "Show more", j);

                    buttonAction[j].addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            GUIShowReview showReview = new GUIShowReview(listReview.get(i), guiListAssignments);
                            showReview.setVisible(true);
                        }

                    });

                    createPanelReview(j);
                    gridLayout.setRows(gridLayout.getRows() + 1);
                    j++;

                }
            }

        } else {
            if(assignmentNumber == 0){
                createLabelInfo("There aren't assignments to show!");
            }
            {
                int j = 0;
                for(Integer i : listAssignment.keySet()){
                    Assignment a = null;

                    a = listAssignment.get(i);

                    ActionListener showInfo = new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            GUIAssignmentInformationCustomer assignmentInfo = new GUIAssignmentInformationCustomer(listAssignment.get(i), email, guiListAssignments);
                            assignmentInfo.setVisible(true);

                        }
                    };

                    setComponents(setLabelString(cs, a, null), "Info", j);
                    buttonAction[j].addActionListener(showInfo);
                    createPanelAssignment(a,j);
                    gridLayout.setRows(gridLayout.getRows() + 1);
                    j++;

                }
            }

        }

        panelOut.add(contentPanel, BorderLayout.NORTH);
        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPanel);

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
    protected void createPanelAssignment(Assignment a, int i){
        infoPanel[i] = new JPanel();
        panelLabel = new JPanel();

        panelButtons = new JPanel();
        panelButtons.setLayout(new GridLayout(1,1));
        panelButtons.setBorder(BorderFactory.createEmptyBorder(10,0,15, 10));

        infoPanel[i].setLayout(new BorderLayout());
        infoPanel[i].setMaximumSize(new Dimension(450,100));

        labelState[i] = createLabelState(a);

        panelLabel.add(labelState[i], BorderLayout.WEST);
        panelLabel.add(labelDescription[i], BorderLayout.CENTER);
        panelButtons.add(buttonAction[i]);
        infoPanel[i].add(panelLabel, BorderLayout.WEST);
        infoPanel[i].add(panelButtons, BorderLayout.EAST);

        infoPanel[i].setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        infoPanel[i].setBorder(BorderFactory.createTitledBorder(""));

        contentPanel.add(infoPanel[i]);

    }

    /**
     * crea un JPanel con la descrizione degli appuntamenti per la visualizzazione delle recensioni
     * @param i indice del JPanel
     */
    protected void createPanelReview (int i){
        infoPanel[i] = new JPanel();
        panelLabel = new JPanel();

        panelButtons = new JPanel();
        panelButtons.setLayout(new GridLayout(1,1));
        panelButtons.setBorder(BorderFactory.createEmptyBorder(30,0,20, 10));

        infoPanel[i].setLayout(new BorderLayout());
        labelDescription[i].setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 50));

        panelLabel.add(labelDescription[i], BorderLayout.CENTER);
        panelButtons.add(buttonAction[i]);
        infoPanel[i].add(panelLabel, BorderLayout.WEST);
        infoPanel[i].add(panelButtons, BorderLayout.EAST);

        infoPanel[i].setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        infoPanel[i].setBorder(BorderFactory.createTitledBorder(""));

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
    protected JLabel createLabelState(Assignment a){
        //da controllare il funzionamento

        JLabel label;


        ImageIcon green = transformImage(new ImageIcon("images/Green_square.svg.png"), 30,30);
        ImageIcon gray = transformImage(new ImageIcon("images/gray_square.png"), 30,30);
        ImageIcon yellow = transformImage(new ImageIcon("images/yellow.jpg"), 30, 30);

        Boolean state = a.getState();

        if(dateBeforeToday(a.getDateEnd())){
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
     * @return icona modificata in altezza e larghezza
     */

    protected ImageIcon transformImage(ImageIcon icon, int width, int height){
        Image imageTransform = icon.getImage();
        Image newImage = imageTransform.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImage);

        return icon;
    }


    /**
     *
     * @param date data da confrontare
     * @return restituisce true se il paramentro passato è una data inferiore a quella di oggi
     */

    protected boolean dateBeforeToday(Date date){
        Date todayDate = new Date(System.currentTimeMillis());

        if(date.before(todayDate)){
            return true;
        }

        return false;
    }

    /**
     * inizializza gli array delle componenti dell'interfaccia
     * @param cs idenfica il menu da cui viene richiamata questa interfaccia
     */

    protected void initArray(CalendarState cs){
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

    }

    /**
     * confronta la data degli appuntamenti con quella di oggi
     * @return lista di appuntamenti con data inferiore a quella di oggi
     */

    protected HashMap<Integer, Assignment> assignmentBeforeToday(){
        HashMap<Integer, Assignment> newListAssignment = new HashMap<>();
        for(Integer i : listAssignment.keySet()){
            Assignment a = null;
            a = listAssignment.get(i);
            if(dateBeforeToday(a.getDateEnd())){
                newListAssignment.put(a.getCode(), a);
            }

        }

        return newListAssignment;

    }

    /**
     * controlla il caso particolare in cui non ci sono appuntamenti da recensire
     * @param number numero di appuntamenti recensiti
     * @param listAssignment lista degli appuntamenti
     */
    protected void noAssignmentToReview(int number, HashMap<Integer, Assignment> listAssignment){
        if(number == listAssignment.size()){
            //System.out.println("Non ci sono recensioni");
            JLabel noReviewLabel = new JLabel("There aren't assignments to review!");
            setSize(WIDTH, 200);
            panelOut.setLayout(new BorderLayout());
            contentPanel.setBorder(BorderFactory.createEmptyBorder(60,100,10,30));
            contentPanel.add(noReviewLabel, BorderLayout.CENTER);
        }

    }


    /**
     * inizializza la mappa di stringhe predefinite per le label di descrizione
     * @param a appuntamento
     * @param r rensione
     */
    protected void setLabelStringMap(Assignment a, Review r){
        strLabel = new HashMap<>();

        String nameDogSitter = "";
        String surnameDogSitter = "";

        String dateStringReview ="";
        String dateStringStartAssigment = "";

        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        date.setLenient(false);
        if(r!=null){
            Date reviewDate = r.getDate();
            dateStringReview = date.format(reviewDate);
            strLabel.put(CalendarState.SHOW_REVIEWS,"<html>" + "Assignment with " + proxy.getDogSitterNameOfAssignment(r.getCode()) + " " + proxy.getDogSitterSurnameOfAssignment(r.getCode()) +"<br/>"+ dateStringReview +"<br/>" + r.getTitle() +"<br/>" + "Vote: " + r.starsRating() + "</html>" );
            strLabel.put(CalendarState.DELETING_REVIEW, "<html>" + "Assignment with " + proxy.getDogSitterNameOfAssignment(r.getCode()) + " " + proxy.getDogSitterSurnameOfAssignment(r.getCode()) +"<br/>"+ dateStringReview +"<br/>" + r.getTitle() +"<br/>" + "Vote: " + r.starsRating() + "</html>");
        }


        if(a!=null){
            Date startAssignment = a.getDateStart();
            dateStringStartAssigment = date.format(startAssignment);
            nameDogSitter = proxy.getDogSitterNameOfAssignment(a.getCode());
            surnameDogSitter = proxy.getDogSitterSurnameOfAssignment(a.getCode());
            strLabel.put(CalendarState.NORMAL, "Assignment with " + nameDogSitter + " " + surnameDogSitter);
            strLabel.put(CalendarState.REVIEWING, "<html>" + "Assignment with " + nameDogSitter + " " + surnameDogSitter + "<br/>" + dateStringStartAssigment +  "</html>");
        }

    }

    /**
     * seleziona la stinga dalla mappadi stringhe predefinite per la lebel di descrizione
     * @param calendarState idenfica il menu da cui viene richiamata questa interfaccia
     * @param a riferimento all'appuntamento
     * @param r riferimento alla recensione
     * @return stringa richiesta in base al CalendarState
     */
    protected String setLabelString(CalendarState calendarState, Assignment a, Review r){
        setLabelStringMap(a, r);
        return strLabel.get(calendarState);

    }

    /**
     * inizializza le componenti di ogni infoPanel
     * @param strLabel stringa per la label di descrizione
     * @param strButton stringa per il bottone
     * @param i indice dell'elemento dell'array
     */
    protected void setComponents( String strLabel, String strButton, int i){
        labelDescription[i]= new JLabel(strLabel);
        buttonAction[i]= new JButton(strButton);


    }

    /**
     * crea una finestra con una stringa predefinita per il caso in cui non ci siano recensioni o appuntamenti
     * @param stringLabel stringa predefinita per la finestra
     */
    protected void createLabelInfo(String stringLabel){
        JLabel noReviewLabel = new JLabel(stringLabel);
        setSize(WIDTH, 200);
        panelOut.setLayout(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(60,100,10,30));
        contentPanel.add(noReviewLabel, BorderLayout.CENTER);

    }

}
