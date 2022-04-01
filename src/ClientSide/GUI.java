package ClientSide;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Arrays;

import static ServerSide.EchoServer3.users;

public class GUI extends JFrame{

    static EchoClient2 myEchoClient;

    JFrame loginGUI = new JFrame("Login Screen");
    JFrame actionsGUI = new JFrame("Welcome User");

    ButtonEventHandler btnHandler;

    JTextField usernameField= new JTextField(50);
    JPasswordField passwordField = new JPasswordField(50);
    public JTextField messageTextBox = new JTextField(50);

    JLabel usernameLabel = new JLabel("Username: ");
    JLabel passwordLabel = new JLabel("Password");
    JLabel messageLabel = new JLabel("Message Text");

    FlowLayout flow = new FlowLayout();

    JButton loginBtn = new JButton("Login");
    JButton sendMessageBtn = new JButton("Send Message");
    JButton downloadMessagesBtn = new JButton("Download all Messages");
    JButton logoffBtn = new JButton("Logout");


    public JTextArea txtArea = new JTextArea(10, 50);

    JScrollPane myScrollPane = new JScrollPane(txtArea);

    public static void main(String[] args)
    {
        GUI gui = new GUI();

        myEchoClient = new EchoClient2(gui);

        users.add("Ryan");
        users.add("Jaster");
        users.add("Zegram");
        users.add("Kisala");
    }

    public GUI()
    {
        loginGUI.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        loginGUI.setLayout(flow);
        loginGUI.setSize(600,200);

        btnHandler = new ButtonEventHandler();

        loginBtn.addActionListener(btnHandler);

        //add components to JFrame
        loginGUI.add(usernameLabel);
        loginGUI.add(usernameField);
        loginGUI.add(passwordLabel);
        loginGUI.add(passwordField);
        loginGUI.add(loginBtn);

        loginGUI.setVisible(true);


    }

    public void showActionsGUI()
    {
        actionsGUI.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        actionsGUI.setLayout(flow);
        actionsGUI.setSize(600, 500);

        sendMessageBtn.addActionListener(btnHandler);
        downloadMessagesBtn.addActionListener(btnHandler);
        logoffBtn.addActionListener(btnHandler);

        actionsGUI.add(messageLabel);
        actionsGUI.add(messageTextBox);
        actionsGUI.add(sendMessageBtn);
        actionsGUI.add(downloadMessagesBtn);
        actionsGUI.add(logoffBtn);
        actionsGUI.add(myScrollPane);

        actionsGUI.setVisible(true);

    }

    private class ButtonEventHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == loginBtn)
            {
                //do code for clicking login
                System.out.println("Clicked Login");

                //everything is fine, proceed.
                //Hide login gui until its needed.
                loginGUI.setVisible(false);

                char[] pass = passwordField.getPassword();
                myEchoClient.userLogin("100 " + usernameField.getText() + " " + Arrays.toString(pass));
                //showActionsGUI();
                //System.out.print("Password" + pass);

            }//if clicking on login button

            if(e.getSource() == sendMessageBtn)
            {
                myEchoClient.uploadMessage("200 " + messageTextBox.getText());
                messageTextBox.setText("");


            }//end of send message button click

            if(e.getSource() == downloadMessagesBtn)
            {
                txtArea.setText("");
                myEchoClient.downloadMessage("300 ");
            }//end of click on download message button

            if(e.getSource() == logoffBtn)
            {
                myEchoClient.userLogout("400 ");
                actionsGUI.dispatchEvent(new WindowEvent(actionsGUI, WindowEvent.WINDOW_CLOSING));

            }//end of click on logoff button
        }

    }//end of button event handler



}
