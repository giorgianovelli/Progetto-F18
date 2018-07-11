package client.gui;

import server.Review;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIShowDogsitterReview extends GUIShowReview {

    private JButton replyButton;
    private String email;

    public GUIShowDogsitterReview(Review review, String email){
        super(review);
        this.email = email;

    }

    @Override
    protected void showReply() {
        GridLayout gridLayout = new GridLayout(1,1, 5, 5);
        closeButton = new JButton("Close");
        replyButton = new JButton("Reply");

        ActionListener close = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();
            }
        };

        ActionListener reply = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUIDogsitterReply guiDogsitterReply = new GUIDogsitterReply(review, email);
                guiDogsitterReply.setVisible(true);
                dispose();

            }
        };


        if(!(review.getReply().equals("null"))){
            panelReply.setBorder(BorderFactory.createTitledBorder("Your Reply: "));
            textReply.setLineWrap(true);
            textReply.setEditable(false);
            textReply.setText(review.getReply());
            panelReply.setLayout(new GridLayout(2,1));
            //panelReply.add(labelReply);
            panelReply.add(textReply);
            closeButton.addActionListener(close);
            closePanel.setBorder(BorderFactory.createEmptyBorder(70,20,20,20));
            closePanel.add(closeButton);

        }
        else {

            gridLayout.setColumns(2);
            closePanel.setLayout(gridLayout);
            closePanel.setBorder(BorderFactory.createEmptyBorder(100,20,20,20));
            closeButton.addActionListener(close);
            replyButton.addActionListener(reply);
            closePanel.add(replyButton);
            closePanel.add(closeButton);
        }

    }
}
