package ServerSide;
import ClientSide.CheckMessages;
import ClientSide.GUI;

import java.net.Socket;

import static ServerSide.EchoServer3.allMessages;
import static ServerSide.EchoServer3.users;

/**
 * This module is to be used with a concurrent Echo server.
 * Its run method carries out the logic of a client session.
 * @author M. L. Liu
 */

public class EchoServerThread extends Thread {

    MyStreamSocket myDataSocket;
    public GUI ui;

    public EchoServerThread(Socket myDataSocket)
    {
        this.myDataSocket = (MyStreamSocket) myDataSocket;
        ui = new GUI();
        ui.closeGui();
    }

    public void run( )
    {
        boolean done = false;
        String message, code = "", trimmedMessage, uName = "", pWord = "", sendText = "";
        String[] split;
        boolean uNameExist = false;
        
        System.out.println("User Connected");

        try
        {
            while (!done)
            {
                message = myDataSocket.receiveMessage( ) ;
                System.out.println("message received: "+ message);
                code = message.substring(0,3);
                //System.out.print("Code " + code);

                if(code.equals("100"))
                {
                    //split up message into parts
                    System.out.println("message received: "+ message);
                    trimmedMessage = message.trim();
                    split = trimmedMessage.split("\\s+");

                    if(split.length <= 4)
                    {
                        //user name or pWord word is blank
                        myDataSocket.sendMessage("103 Error. Username or Password is blank");


                    }

                    else
                    {
                        //continue
                        uName = split[1];
                        pWord = split[2];

                        System.out.println(uName + "uName");
                        System.out.print(pWord + "pWord");


                        for (Object user : users) {
                            if (user == uName) {
                                //uName found get out of loop
                                uNameExist = true;
                                break;
                            }
                        }//end of for loop

                        if(uNameExist)
                        {
                            //uName exists
                            //User logged in
                            myDataSocket.sendMessage("101 Login Successful");
                        }

                        else
                        {
                            //uName does not exist
                            //User not logged in
                            users.add(uName);
                            myDataSocket.sendMessage("102 Login Successful. User added.");
                        }

                        ui.showActionsGUI();
                    }
                }

                if(code.equals("200"))
                {
                    System.out.println("201 Message Sent");
                    trimmedMessage = message.trim();
                    split = trimmedMessage.split("\\s+");

                    System.out.println(split[1]);

                    if(split.length <= 4)
                    {
                        System.out.println("202 No message in text box");
                    }

                    else
                    {
                        sendText = split[1];
                        allMessages.add(sendText);
                    }
                }

                if(code.equals("300"))
                {
                    String currentMessage = "";

                    for (String allMessage : allMessages)
                    {
                        myDataSocket.sendMessage("  " + allMessage);
                    }

                    myDataSocket.sendMessage(CheckMessages.isComplete);
                }

                if(code.equals("400"))
                {
                    myDataSocket.sendMessage("401 Logged Out");
                    myDataSocket.close();
                }
            }
        }
        catch (Exception ex)
        {
            System.out.println("Exception caught in thread: " + ex);
        }
    }
}

