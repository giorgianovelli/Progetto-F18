package client.gui;

import client.Calendar;
import client.proxy.CustomerProxy;
import client.proxy.DogSitterProxy;
import enumeration.CalendarState;
import server.Assignment;
import server.Review;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class GUIShowDogsitterAssignment extends GUIListAssignments {

    private DogSitterProxy dogSitterProxy;
    //public HashMap<Integer, Review> listReviewDogsitter;
    public  GUIShowDogsitterAssignment guiShowDogsitterAssignment; // serve per disattivare la finestra madre @Riccardo


    public GUIShowDogsitterAssignment(CalendarState cs, HashMap<Integer, Assignment> listAssignment, String email, GUIHome guiDogsitter){
        super(cs,listAssignment, email, guiDogsitter);
        guiShowDogsitterAssignment = this;

        dogSitterProxy = new DogSitterProxy(email);
         //TODO non riesco ad aggiungere la lista delle recensioni
        //System.out.println(this.listReviewDogsitter.size() + " a");


        initComponents(cs, guiDogsitter);



    }


    @Override
    protected void initComponents(CalendarState cs, GUIHome guiDogsitter) {


        dogSitterProxy = new DogSitterProxy(email);
        gridLayout = new GridLayout(1,1);
        if(cs.equals(CalendarState.NORMAL)){
            assignmentNumber = listAssignment.size();
            infoPanel = new JPanel[assignmentNumber];
            labelDescription = new JLabel[assignmentNumber];
            buttonAction = new JButton[assignmentNumber];
            labelState = new JLabel[assignmentNumber];
        }


        gridLayout = new GridLayout(1,1);
        contentPanel = new JPanel();
        panelOut = new JPanel();
        scrollPanel = new JScrollPane(panelOut);



        contentPanel.setLayout(gridLayout);
        panelOut.setLayout(new BorderLayout());

        if(cs.equals(CalendarState.NORMAL)){
            int j = 0;

            for(Integer i : listAssignment.keySet()){
                Assignment a = null;

                a = listAssignment.get(i);

                String nameCustomer = dogSitterProxy.getCustomerNameOfAssignment(a.getCode());
                String surnameCustomer = dogSitterProxy.getCustomerSurnameOfAssignment(a.getCode());

                ActionListener showInfo = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        GUIAssignmentInformationCustomer assignmentInfo = new GUIAssignmentInformationCustomer(listAssignment.get(i), email, guiShowDogsitterAssignment);
                        assignmentInfo.setVisible(true);

                    }
                };




                labelDescription[j]= new JLabel("Assignment with " + nameCustomer + " " + surnameCustomer);
                buttonAction[j]= new JButton("Info");
                buttonAction[j].addActionListener(showInfo);


                createPanelAssignment(a,j);

                gridLayout.setRows(gridLayout.getRows() + 1);
                j++;

            }
        }
        /*else if(cs.equals(CalendarState.SHOW_REVIEWS)){
            setTitle("Your reviews");

            int j = 0;
            for(Integer i: listReviewDogsitter.keySet()){
                Review r = null;
                String labelString;
                r = listReviewDogsitter.get(i);

                SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                date.setLenient(false);
                Date reviewDate = r.getDate();
                String dateStringReview = date.format(reviewDate);

                labelString = "<html>" + "Customer: " + dogSitterProxy.getCustomerNameOfAssignment(r.getCode()) + " " + dogSitterProxy.getCustomerSurnameOfAssignment(r.getCode()) +"<br/>"+ dateStringReview +"<br/>" + r.getTitle() +"<br/>" + "Vote: " + r.starsRating() + "</html>";
                labelDescription[j]= new JLabel(labelString);
                buttonAction[j]= new JButton("Show more");
                buttonAction[j].addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        GUIShowReview showReview = new GUIShowReview(listReviewDogsitter.get(i));
                        showReview.setVisible(true);
                    }

                });

                createPanelReview(j);
                gridLayout.setRows(gridLayout.getRows() + 1);
                j++;

            }
        }*/





        panelOut.add(contentPanel, BorderLayout.NORTH);
        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPanel);



    }

}
