package ClientSide;

import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;


/**
 * This module contains the presentaton logic of an Echo Client.
 * @author M. L. Liu
 */
public class EchoClient2 {

    static String echo;
    String hostName = "localHost";
    String portNum = "7";

    EchoClientHelper2 helper;
    public GUI myGui;

    public EchoClient2(GUI myGui)
    {

        this.myGui = myGui;
        System.setProperty("javax.net.ssl.trustStore", "za.store");

        try
        {
            Socket socket = ((SSLSocketFactory) SSLSocketFactory.getDefault()).createSocket("localhost", 4444);
            BufferedReader socketBufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader commandPropertyBufferReader = new BufferedReader(new InputStreamReader(System.in));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        try
        {
            helper = new EchoClientHelper2(hostName, portNum, myGui);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    //method for user to login
    public void userLogin(String messageReceived)
    {
        try
        {
            echo = helper.login(messageReceived);
            System.out.print(echo);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    //method for user to send a message
    public void uploadMessage(String messageReceived)
    {
        try
        {
            helper.sendMessage(messageReceived);
            System.out.println("Message Received");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        };

    }

    public void downloadMessage(String messageReceived)
    {
        try
        {
            echo = helper.downloadMessage(messageReceived);
            System.out.print("\nDownload message echo: " + echo);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void userLogout(String messageReceived)
    {
        try
        {
            echo = helper.logoff(messageReceived);
            System.out.print(echo);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


} // end class
