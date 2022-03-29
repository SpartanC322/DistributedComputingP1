package ClientSide;

import Server.EchoServer3;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;

import static Server.EchoServer3.users;

public class GUI extends JFrame{

    JFrame login = new JFrame("Login");
    JFrame ActionListener = new JFrame("Hello");
    ButtonEventHandler btnHandler;
    EchoClient2 ecClient2;

    JTextField uNameField= new JTextField(50);
    JLabel uNameLabel = new JLabel("Username: ");
    JLabel pWordLabel = new JLabel("Password: ");
    JPasswordField passwordField = new JPasswordField(50);
    FlowLayout fl = new FlowLayout();
    JButton btnLogin = new JButton("Login");

    //ActionGUI pieces
    JLabel mLabel = new JLabel("Message Text");
    public JTextField mTextBox = new JTextField(50);
    JButton sendMBtn = new JButton("Send Message");
    JButton downloadMBtn = new JButton("Download all Messages");
    JButton logOutBtn = new JButton("Logout");

    public JTextArea txtArea = new JTextArea(10, 50);

    JScrollPane scrollPane = new JScrollPane(txtArea);

    public static void main(String[] args)
    {
        UI ui = new UI();

        ui.ecClient2 = new EchoClient2(ui);

        users.add("Ben");
        users.add("Conor");
        users.add("Miguel");
    }

    public UI()
    {
        login.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        login.setLayout(fl);
        login.setSize(600,200);

        btnHandler = new ButtonEventHandler();

        btnLogin.addActionListener(btnHandler);

        //add components to JFrame
        login.add(uNameLabel);
        login.add(uNameField);
        login.add(pWordLabel);
        login.add(pa: sswordField);
        login.add(btnLogin);

        login.setVisible(true);


    }

    public void showActionsGUI()
    {
        ActionListener.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ActionListener.setLayout(fl);
        ActionListener.setSize(600, 500);

        sendMBtn.addActionListener(btnHandler);
        downloadMBtn.addActionListener(btnHandler);
        logOutBtn.addActionListener(btnHandler);

        ActionListener.add(mLabel);
        ActionListener.add(mTextBox);
        ActionListener.add(sendMBtn);
        ActionListener.add(downloadMBtn);
        ActionListener.add(logOutBtn);
        ActionListener.add(scrollPane);

        ActionListener.setVisible(true);

    }

    private class ButtonEventHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == btnLogin)
            {
                System.out.println("Clicked Login");

                login.setVisible(false);

                char pass[] = passwordField.getPassword();
                ecClient2.userLogin("100 " + uNameField.getText() + " " + pass);

            }

            if(e.getSource() == sendMBtn)
            {
                ecClient2.uploadMessage("200 " + mTextBox.getText());
                mTextBox.setText("");
            }

            if(e.getSource() == downloadMBtn)
            {
                txtArea.setText("");
                ecClient2.downloadMessage("300 ");
            }

            if(e.getSource() == logOutBtn)
            {
                ecClient2.userLogout("400 ");
                ActionListener.dispatchEvent(new WindowEvent(ActionListener, WindowEvent.WINDOW_CLOSING));
            }
        }
    }
}
