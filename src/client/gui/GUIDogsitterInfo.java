package client.gui;

import client.proxy.DogSitterProxy;
import server.Review;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class GUIDogsitterInfo extends JFrame {

    final int WIDTH = 512;
    final int HEIGHT = 512;


    private GUIDogsitterInfo guiDogsitterInfo;


    private JPanel panelOut = new JPanel();
    private JPanel panelInfo = new JPanel();
    private JPanel panelInfoBio  = new JPanel();
    private JPanel panelName = new JPanel();
    private JPanel panelSurname = new JPanel();
    private JPanel panelBirth = new JPanel();
    private JPanel panelAverage = new JPanel();
    private JPanel panelBio = new JPanel();
    private JPanel panelReviews = new JPanel();
    private JPanel panelClose = new JPanel();
    private GridLayout gridLayout = new GridLayout(1,1);

    private JScrollPane scrollPane = new JScrollPane(panelOut);


    private JLabel labelName = new JLabel("Name: ");
    private JLabel labelSurname = new JLabel("Surname: ");
    private JLabel labelBirth = new JLabel("Birth date: ");
    private JLabel labelAverage = new JLabel("Average reviews: ");
    private JLabel labelBio = new JLabel("\nBiography: ");

    private JLabel labelNameToBeFilled = new JLabel();
    private JLabel labelSurnameToBeFilled = new JLabel();
    private JLabel labelBirthToBeFilled = new JLabel();
    private JLabel labelAverageToBeFilled = new JLabel();
    private JTextArea textBiography = new JTextArea();

    private JButton buttonClose = new JButton("Close");


    private DogSitterProxy dogSitterProxy;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");


    /**
     * Constructor of GUIDogsitterInfo's class
     *
     * @param mailDogsitter mail of the dogsitter
     */

     GUIDogsitterInfo(String mailDogsitter, GUIChooseDogsitter guiChooseDogsitter) {
        setTitle("Dogsitter Informations");
        setSize(WIDTH, HEIGHT);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        guiDogsitterInfo = this;

        guiChooseDogsitter.setEnabled(false);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                guiChooseDogsitter.setEnabled(true);
            }
        });




        dogSitterProxy = new DogSitterProxy(mailDogsitter);

        initComponents();
    }


    /**
     * This method implements graphic objects
     */

    public void initComponents() {

        panelOut.setLayout(new BorderLayout());
        panelInfoBio.setLayout(new BorderLayout());
        panelInfoBio.setBorder(BorderFactory.createTitledBorder("Dogsitter: "));
        panelInfo.setLayout(new GridLayout(4,1));
        panelBio.setLayout(new BorderLayout());
        panelName.setLayout(new GridLayout(1,2));
        panelSurname.setLayout(new GridLayout(1,2));
        panelBirth.setLayout(new GridLayout(1,2));
        panelAverage.setLayout(new GridLayout(1,2));
        panelReviews.setLayout(gridLayout);
        panelReviews.setBorder(BorderFactory.createTitledBorder("Reviews: "));
        panelClose.setLayout(new BorderLayout());
        panelClose.setBorder(BorderFactory.createEmptyBorder(20,190,20,190));

        panelOut.add(panelInfoBio, BorderLayout.NORTH);
        panelOut.add(panelReviews, BorderLayout.CENTER);
        panelOut.add(panelClose, BorderLayout.SOUTH);

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

        panelBio.add(labelBio, BorderLayout.NORTH);
        panelBio.add(textBiography, BorderLayout.CENTER);
        textBiography.setEditable(false);
        textBiography.setLineWrap(true);
        textBiography.setWrapStyleWord(true);


        String strDate = simpleDateFormat.format(dogSitterProxy.getDateOfBirth());
        String biography = dogSitterProxy.getBiography();

        labelNameToBeFilled.setText(dogSitterProxy.getName());
        labelSurnameToBeFilled.setText(dogSitterProxy.getSurname());
        labelBirthToBeFilled.setText(strDate);
        textBiography.setText(biography.toUpperCase());

        HashMap<Integer, Review> listReview = dogSitterProxy.getReviewList();

        double average;
        int c = 0;
        double sum = 0;

        for (Map.Entry<Integer, Review> entry: listReview.entrySet()) {
            sum += entry.getValue().getRating();
            c++;
        }
        average = sum / c;
        String format  = String.format("%.2f", average).replace(",",".");
        labelAverageToBeFilled.setText(format);


        // Bottone close

        panelClose.add(buttonClose);

        // Pannello reviews

        for (Map.Entry<Integer, Review> entry: listReview.entrySet()) {

            String customer = dogSitterProxy.getCustomerNameOfAssignment(entry.getKey()) + " " + dogSitterProxy.getCustomerSurnameOfAssignment(entry.getKey());
            String title = entry.getValue().getTitle();
            String date = simpleDateFormat1.format(entry.getValue().getDate());
            String vote = entry.getValue().starsRating();
            JLabel label = new JLabel("<html>" + "Review by: " + customer + "<br>" + title + "<br>" + date + "<br>" + vote + "<br/>");
            JButton buttonShowMore = new JButton("Show More");

            JPanel panelLabel = new JPanel();
            JPanel panelButton = new JPanel();
            panelLabel.add(label);
            panelButton.add(buttonShowMore);
            panelButton.setBorder(BorderFactory.createEmptyBorder(10,0,0,10));



            JPanel panelReview = new JPanel();
            panelReview.setLayout(new BorderLayout());
            panelReview.add(panelLabel, BorderLayout.WEST);
            panelReview.add(panelButton, BorderLayout.EAST);
            panelReviews.add(panelReview);
            gridLayout.setRows(gridLayout.getRows() + 1);

            buttonShowMore.addActionListener(e -> new GUIShowReview(entry.getValue()).setVisible(true));

        }

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);

        buttonClose.addActionListener(e -> guiDogsitterInfo.dispatchEvent(new WindowEvent(guiDogsitterInfo, WindowEvent.WINDOW_CLOSING)));
    }






}
