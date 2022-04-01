package ClientSide;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Arrays;

import static ServerSide.EchoServer3.users;

public class GUI implements ActionListener
{
    static EchoClient2 myEchoClient;

    JFrame loginFr = new JFrame("Login Screen");
    JFrame mainFr = new JFrame("Welcome User");

    JTextField uNameField = new JTextField(30);
    JPasswordField pWordField = new JPasswordField(30);
    public JTextField messTextBox = new JTextField(30);
    public JTextArea txtArea = new JTextArea(10, 30);

    JLabel uNameLabel = new JLabel("Username: ");
    JLabel pWordLabel = new JLabel("Password: ");
    JLabel messLabel = new JLabel("Message Text");

    FlowLayout flow = new FlowLayout();

    JButton loginBtn = new JButton("Login");
    JButton sendBtn = new JButton("Send Message");
    JButton downloadBtn = new JButton("Download all Messages");
    JButton logoutBtn = new JButton("Logout");

    JScrollPane scrollPane = new JScrollPane(txtArea);

    public static void main(String[] args)
    {
        GUI ui = new GUI();

        myEchoClient = new EchoClient2(ui);

        users.add("Ben");
        users.add("Miguel");
    }

    public GUI()
    {
        loginFr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        loginFr.setLayout(flow);
        loginFr.setSize(400,200);

        loginBtn.addActionListener(this);

        loginFr.add(uNameLabel);
        loginFr.add(uNameField);
        loginFr.add(pWordLabel);
        loginFr.add(pWordField);
        loginFr.add(loginBtn);

        loginFr.setVisible(true);
    }

    public void showMainGUI()
    {
        mainFr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFr.setLayout(flow);
        mainFr.setSize(500, 300);

        sendBtn.addActionListener(this);
        downloadBtn.addActionListener(this);
        logoutBtn.addActionListener(this);

        mainFr.add(messLabel);
        mainFr.add(messTextBox);
        mainFr.add(sendBtn);
        mainFr.add(downloadBtn);
        mainFr.add(logoutBtn);
        mainFr.add(scrollPane);

        mainFr.setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == loginBtn)
        {
            System.out.println("Login button clicked");

            loginFr.setVisible(false);

            char[] pass = pWordField.getPassword();
            myEchoClient.userLogin("100 " + uNameField.getText() + " " + Arrays.toString(pass));
        }

        //Not working, causes NullPointerException
        if(e.getSource() == sendBtn)
        {
            System.out.println("Send button clicked");
            myEchoClient.uploadMessage("200 " + messTextBox.getText());
            messTextBox.setText("");
        }

        if(e.getSource() == downloadBtn)
        {
            System.out.println("Download button clicked");
            txtArea.setText("");
            myEchoClient.downloadMessage("300 ");
        }

        if(e.getSource() == logoutBtn)
        {
            System.out.println("Logout button clicked");
            myEchoClient.userLogout("400 ");
            mainFr.dispatchEvent(new WindowEvent(mainFr, WindowEvent.WINDOW_CLOSING));
        }
    }

    public void closeGui()
    {
        loginFr.setVisible(false);
    }
}
