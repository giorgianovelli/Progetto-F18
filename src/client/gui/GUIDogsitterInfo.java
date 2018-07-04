package client.gui;

import client.proxy.DogSitterProxy;
import server.Review;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class GUIDogsitterInfo extends JFrame {

    final int WIDTH = 512;
    final int HEIGHT = 512;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();



    private JPanel panelOut = new JPanel();
    private JPanel panelInfo = new JPanel();
    private JPanel panelInfoBio  = new JPanel();
    private JPanel panelName = new JPanel();
    private JPanel panelSurname = new JPanel();
    private JPanel panelBirth = new JPanel();
    private JPanel panelAverage = new JPanel();
    private JPanel panelBio = new JPanel();
    private JPanel panelReviews = new JPanel();

    private JScrollPane scrollPane = new JScrollPane(panelOut);


    private JLabel labelName = new JLabel("Name: ");
    private JLabel labelSurname = new JLabel("Surname: ");
    private JLabel labelBirth = new JLabel("Birth date: ");
    private JLabel labelAverage = new JLabel("Average reviews: ");
    private JLabel labelBio = new JLabel("Biography: ");

    private JLabel labelNameToBeFilled = new JLabel();
    private JLabel labelSurnameToBeFilled = new JLabel();
    private JLabel labelBirthToBeFilled = new JLabel();
    private JLabel labelAverageToBeFilled = new JLabel();
    private JLabel labelBioToBeFilled = new JLabel();


    private HashMap<Integer, Review> listReview;
    private DogSitterProxy dogSitterProxy;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");





    public GUIDogsitterInfo(String mailDogsitter) {
        setTitle("Dogsitter Informations");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());


        dogSitterProxy = new DogSitterProxy(mailDogsitter);

        initComponents();
    }


    public void initComponents() {

        panelOut.setLayout(new BorderLayout());
        panelInfoBio.setLayout(new BorderLayout());
        panelInfoBio.setBorder(BorderFactory.createTitledBorder("Bio:"));
        panelInfo.setLayout(new GridLayout(4,1));
        panelName.setLayout(new GridLayout(1,2));
        panelSurname.setLayout(new GridLayout(1,2));
        panelBirth.setLayout(new GridLayout(1,2));
        panelAverage.setLayout(new GridLayout(1,2));

        panelOut.add(panelInfoBio, BorderLayout.NORTH);

        panelInfoBio.add(panelInfo, BorderLayout.NORTH);
        panelInfoBio.add(panelBio, BorderLayout.CENTER);

        panelInfo.add(panelName);
        panelInfo.add(panelSurname);
        panelInfo.add(panelBirth);
        panelInfo.add(panelAverage);

        panelName.add(labelName);
        panelName.add(labelNameToBeFilled);
        panelSurname.add(labelSurname);
        panelSurname.add(labelSurnameToBeFilled);
        panelBirth.add(labelBirth);
        panelBirth.add(labelBirthToBeFilled);
        panelAverage.add(labelAverage);
        panelAverage.add(labelAverageToBeFilled);

        panelBio.add(labelBio);
        panelBio.add(labelBioToBeFilled);


        String strDate = simpleDateFormat.format(dogSitterProxy.getDateOfBirth());

        labelNameToBeFilled.setText(dogSitterProxy.getName());
        labelSurnameToBeFilled.setText(dogSitterProxy.getSurname());
        labelBirthToBeFilled.setText(strDate);
        labelAverageToBeFilled.setText("3");
        //labelBioToBeFilled.setText(dogSitterProxy.getBiography);

        listReview = dogSitterProxy.getReviewList();

        for (Map.Entry<Integer, Review> entry: listReview.entrySet()) {
            //String nameCustomer = entry.getValue()
            String title = entry.getValue().getTitle();
            String date = simpleDateFormat.format(entry.getValue().getDate());
            String vote = String.valueOf(entry.getValue().getRating());
            String allText;
            
            JLabel labelName = new JLabel("Review by TESTTEST");
            JLabel labelTitle = new JLabel(title);
            JLabel labelDate = new JLabel(date);
            JLabel labelVote = new JLabel(vote);

            JLabel labelReview = new JLabel();
            JButton buttonShowMore = new JButton("Show More");

            JPanel panelReview = new JPanel();
            panelReview.setLayout(new BorderLayout());


        }

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);



    }






}
