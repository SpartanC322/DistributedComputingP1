package ClientSide;

import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;

/**
 * This module contains the presentaton logic of an Echo Client.
 * @author M. L. Liu
 */

public class EchoClient2 {

    static String ec;
    String hName = "localHost";
    String pNum = "42";

    EchoClientHelper2 ecHelper;
    public GUI ui;

    public EchoClient2(GUI ui)
    {
        this.ui = ui;
        System.setProperty("javax.net.ssl.trustStore", "st.store");

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
            ecHelper = new EchoClientHelper2(hName, pNum, ui);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public void userLogin(String message)
    {
        try
        {
            ec = ecHelper.login(message);
            System.out.print(ec);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public void uploadMessage(String message)
    {
        try
        {
            ecHelper.sendMessage(message);
            System.out.println("Message Received");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        };

    }

    public void downloadMessage(String message)
    {
        try
        {
            ec = ecHelper.downloadMessage(message);
            System.out.print("\nDownload message echo: " + ec);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void userLogout(String message)
    {
        try
        {
            ec = ecHelper.logoff(message);
            System.out.print(ec);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
