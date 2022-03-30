package ClientSide;

import java.net.*;
import java.io.*;

/**
 * This class is a module which provides the application logic
 * for an Echo client using stream-mode socket.
 * @author M. L. Liu
*/
 public class EchoClientHelper2
 {
   static final String endMessage = ".";
   private StreamSocket sock;
   private InetAddress serverHost;
   private int serverPort;
   UI ui;

   EchoClientHelper2(String hName, String pNum, UI ui) throws SocketException, UnknownHostException, IOException
   {
      this.ui = ui;
      this.serverHost = InetAddress.getByName(hName);
      this.serverPort = Integer.parseInt(pNum);
      this.sock = new StreamSocket(this.serverHost, this.serverPort);
      System.out.println("Connection request sent");

   }


   public String login(String message) throws SocketException,IOException
   {
      String ec = "";
      sock.sendMessage(message);
      ec = sock.receiveMessage();
      return ec;
   }

   public void sendMessage(String message) throws SocketException, IOException
   {
      String ec = "";
      sock.sendMessage(message);
   }

   public String downloadMessage(String message) throws SocketException,IOException
    {
      String ec = "";
      sock.sendMessage(message);

      ec = sock.receiveMessage();

      while(!ec.equals(CheckMessages.isComplete))
      {
         ui.txtArea.append(ec);
         ec = sock.receiveMessage();
      }

      return ec;
   }

   public String logOff(String message) throws SocketException, IOException
   {
      String ec = "";
      sock.sendMessage(message);
      ec = sock.receiveMessage();
      return ec;
   }

   public void finish() throws SocketException, IOException
   {
      sock.sendMessage(endMessage);
      sock.close();
   }
 }