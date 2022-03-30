package ClientSide;

import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;

/**
 * This module contains the presentaton logic of an Echo Client.
 * @author M. L. Liu
 */
public class EchoClient2
{
    static String ec;
    String hName = "localhost";
    String pNum = "42";

    EchoClientHelper2 help;

    public UI ui;

    public EchoClient2(UI ui)
    {
      this.ui = ui;
      System.setProperty("javax.net.ssl.trustStore", "za.store");

      try
      {
         Socket sock = SSLSocketFactory.getDefault().createSocket("localhost", 3333);
         BufferedReader sockBuffRead = new BufferedReader(new InputStreamReader(sock.getInputStream()));
         PrintWriter printWriter = new PrintWriter(sock.getOutputStream(),true);
         BufferedReader buffRead = new BufferedReader(new InputStreamReader(System.in));
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }

      try
      {
         help = new EchoClientHelper2(hName, pNum, ui);
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
         ec = help.login(message);
         System.out.print(ec);
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
         ec = help.logOff(message);
         System.out.print(ec);
      }
      catch (IOException e) {
         e.printStackTrace();
      }
   }

   public void messageUpload(String message)
   {
      try
      {
         help.sendMessage(message);
         System.out.print("Message Received");
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
   }

   public void messageDownload(String message)
   {
      try
      {
         ec = help.downloadMessage(message);
         System.out.print("\nDownload message ec: " + ec);
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
   }
}