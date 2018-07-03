package client.gui;

import server.Review;

import javax.swing.*;
import java.awt.*;

public class GUIShowReview extends JFrame {

    final int WIDTH = 512;
    final int HEIGHT = 512;
    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
    private Review review;

    private JPanel outPanel;
    private JPanel contentPanel;
    private JPanel panelReviewTop;
    private JPanel panelReviewDescription;
    private JPanel panelReply;

    private JLabel labelTitle;
    private JLabel labelDescription;
    private JLabel labelVote;
    private JLabel labelGrade;
    //private JLabel labelReply;

    private JTextArea textTitle;
    private JTextArea textDescription;
    private JTextArea textReply;

    //private int numberRow;

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
        //TODO migliorare il layout
        outPanel = new JPanel();
        contentPanel = new JPanel();
        panelReviewTop = new JPanel();
        panelReviewDescription = new JPanel();
        panelReply = new JPanel();

        contentPanel.setBorder(BorderFactory.createTitledBorder("Your Review: "));

        labelTitle = new JLabel("Title: ", SwingConstants.LEFT);
        labelVote = new JLabel("Vote: " , SwingConstants.LEFT);
        labelDescription = new JLabel("Comment: ");

        labelGrade = new JLabel(Integer.toString(review.getRating()));
        //labelReply = new JLabel("Dogsitter reply: ");

        textTitle = new JTextArea(review.getTitle());
        textTitle.setEditable(false);
        textTitle.setLineWrap(true);
        textTitle.setWrapStyleWord(true);

        textDescription = new JTextArea(review.getComment(),7,1);
        textDescription.setEditable(false);
        textDescription.setLineWrap(true);
        textDescription.setWrapStyleWord(true);
        textReply = new JTextArea();

        panelReviewTop.setLayout(new GridLayout(2,2));
        panelReviewTop.add(labelTitle);
        panelReviewTop.add(textTitle);
        panelReviewTop.add(labelVote);
        panelReviewTop.add(labelGrade);



        panelReviewDescription.setLayout(new GridLayout(2,1));
        panelReviewDescription.add(labelDescription);
        panelReviewDescription.add(textDescription);




        /*GridBagLayout gridBag = new GridBagLayout();
        panelReviewTop.setLayout(gridBag);
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        panelReviewTop.add(labelTitle, gridBag, 0);
        c.gridwidth = GridBagConstraints.REMAINDER;
        panelReviewTop.add(textTitle, gridBag, 1);
        c.weightx = 1.0;
        panelReviewTop.add(labelVote, gridBag, 1);
        c.gridwidth = GridBagConstraints.REMAINDER;
        panelReviewTop.add(labelTitle, gridBag, 2);

        c.weightx = 0.0;
        panelReviewTop.add(labelDescription, gridBag, 1);


*/
        
        contentPanel.setLayout(new GridLayout(2, 1));
        contentPanel.add(panelReviewTop);
        contentPanel.add(panelReviewDescription);



        if(!(review.getReply().equals("null"))){
            panelReply.setBorder(BorderFactory.createTitledBorder("Dogsitter Reply: "));
            textReply.setLineWrap(true);
            textReply.setEditable(false);
            textReply.setText(review.getReply());
            panelReply.setLayout(new GridLayout(2,1));
            //panelReply.add(labelReply);
            panelReply.add(textReply);

        }

        outPanel.setLayout(new GridLayout(2,1));

        outPanel.add(contentPanel);
        outPanel.add(panelReply);
        outPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        add(outPanel);

    }




}
