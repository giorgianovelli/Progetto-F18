package customerClient.gui;

import server.Review;

import javax.swing.*;
import java.awt.*;

public class GUIShowReview extends JFrame {

    final int WIDTH = 512;
    final int HEIGHT = 512;
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
    private Review review;

    private JPanel panelReview;
    private JPanel panelReply;

    private JLabel labelTitle;
    private JLabel labelDescription;
    private JLabel labelVote;
    private JLabel labelReply;

    private JTextArea textTitle;
    private JTextArea textDescrption;
    private JTextArea textReply;


    public GUIShowReview(Review review){
        setTitle("Show review");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        this.review = review;

        initComponent();

    }

    private void initComponent(){

        JPanel panelReview = new JPanel();
        JPanel panelReply = new JPanel();

        JLabel labelTitle = new JLabel("Title: ");
        JLabel labelDescription = new JLabel("Description: ");
        JLabel labelVote = new JLabel("Vote: " + Integer.toString(review.getRating()));
        JLabel labelReply = new JLabel("Dogsitter reply: ");

        JTextArea textTitle = new JTextArea(review.getTitle());
        textTitle.setEditable(false);
        textTitle.setLineWrap(true);
        JTextArea textDescription = new JTextArea(review.getComment());
        textDescription.setEditable(false);
        textDescription.setLineWrap(true);
        JTextArea textReply = new JTextArea();




        panelReview.add(labelTitle);
        panelReview.add(textTitle);

        panelReview.add(labelVote);

        panelReview.add(labelDescription);
        panelReview.add(textDescription);


        add(panelReview, BorderLayout.CENTER);

        if(review.getReply()!= null){
            textReply.setLineWrap(true);
            textReply.setEditable(false);
            textReply.setText(review.getReply());
            panelReply.add(labelReply);
            panelReply.add(textReply);
            add(panelReply, BorderLayout.SOUTH);
        }



    }




}
