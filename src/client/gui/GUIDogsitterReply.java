package client.gui;

import client.proxy.CustomerProxy;
import client.proxy.DogSitterProxy;
import server.Review;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class GUIDogsitterReply extends JFrame {
    final int WIDTH = 400;
    final int HEIGHT = 300;

    private Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
    private String email;
    private DogSitterProxy proxy;
    private Review review;


    private JPanel contentPanel;
    private JPanel buttonPanel;
    private JPanel outPanel;

    private JScrollPane textScroll;

    private JTextArea textReply;

    private JButton sendButton;
    private JButton cancelButton;

    public GUIDogsitterReply(Review review, String email){
        this.review = review;
        this.email = email;
        proxy = new DogSitterProxy(email);
        initComponent();
    }

    private void initComponent(){
        setTitle("Write a reply");
        setSize(WIDTH, HEIGHT);
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        contentPanel = new JPanel();
        buttonPanel = new JPanel();
        outPanel = new JPanel();


        textReply = new JTextArea();
        textReply.setWrapStyleWord(true);
        textReply.setLineWrap(true);

        textScroll = new JScrollPane(textReply);

        contentPanel.setBorder(BorderFactory.createTitledBorder("Reply: "));
        contentPanel.setLayout(new GridLayout(1,1));
        contentPanel.add(textScroll);


        ActionListener send = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean strError = false;
                String error = "";
                String reply = textReply.getText();
                if(reply.equals("")){
                    strError = true;
                    error = "Please fill in all fields!";
                }
                if(reply.contains("#")){
                    strError = true;
                    error = "# invalid character";
                }

                if(strError){
                    JOptionPane.showMessageDialog(new JFrame(), error, "Error", JOptionPane.ERROR_MESSAGE);

                } else if(proxy.replyToReview(review.getCode(), reply)){ //TODO da provare
                    JOptionPane.showMessageDialog(new JFrame(), "Reply added!", "Reply", JOptionPane.INFORMATION_MESSAGE);
                    dispose();

                    }

            }
        };





        sendButton = new JButton("Send");
        sendButton.addActionListener(send);
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonPanel.setLayout(new GridLayout(1,2,5,5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20,15,5,15));
        buttonPanel.add(sendButton);
        buttonPanel.add(cancelButton);

        outPanel.setLayout(new BorderLayout());
        outPanel.setBorder(BorderFactory.createEmptyBorder(10,10,5,10));
        outPanel.add(contentPanel, BorderLayout.CENTER);
        outPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(outPanel);


    }


}
