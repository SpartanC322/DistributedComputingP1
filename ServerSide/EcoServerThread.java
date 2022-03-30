package ServerSide;

import ClientSide.CheckMessages;
import ClientSide.UI;
import java.net.Socket;

import static ServerSide.EchoServer3.messages;
import static ServerSide.EchoServer3.users;

/**
 * This module is to be used with a concurrent Echo server.
 * Its run method carries out the logic of a client session.
 * @author M. L. Liu
 */

class EchoServerThread extends Thread 
{

    StreamSocket DataSock;
    UI ui;

    EchoServerThread(Socket myDataSocket)
    {
        this.DataSock = (StreamSocket) DataSock;
        ui = new UI();
    }

    public void run( )
    {
        boolean finished = false;
        String message, trimmed;
        String code = "";
        String uName = "";
        String pWord = "";
        String mText = "";
        String[] splitMessage;
        boolean uNameExist = false;

        System.out.println("User is" + "now connected to the server");

        try
        {
            while (finished != true)
            {
                message = DataSock.receiveMessage( ) ;
                System.out.println("message received: "+ message);
                code = message.substring(0,3);

                if(code.equals("100"))
                {
                    System.out.println("message received: "+ message);
                    trimmed = message.trim();
                    splitMessage = trimmed.split("\\s+");

                    if(splitMessage.length <= 4)
                    {
                        DataSock.sendMessage("103 Error. Username or Password is blank");
                    }
                    else
                        {
                            uName = splitMessage[1];
                            pWord = splitMessage[2];

                            System.out.println(uName + "username");
                            System.out.print(pWord + "pass");


                            for(int i = 0; i < users.size(); i++)
                            {
                                if(users.get(i) == uName)
                                {
                                    uNameExist = true;
                                    break;
                                }
                            }

                            if(uNameExist)
                            {
                                DataSock.sendMessage("101 Login Successful");
                            }
                            else
                            {
                                users.add(uName);
                                DataSock.sendMessage("102 Login Successful. User added.");

                            }

                            ui.showActionsGUI();
                        }
                }

                if(code.equals("200"))
                {
                    System.out.println("201 Message Sent");
                    trimmed = message.trim();
                    splitMessage = trimmed.split("\\s+");

                    System.out.println(splitMessage[1]);

                    if(splitMessage.length <= 4)
                    {
                        System.out.println("202 No message in text box");
                    }

                    else
                    {
                        mText = splitMessage[1];
                        messages.add(mText);
                    }
                }

                if(code.equals("300"))
                {
                    String currentMessage = "";

                    for(int i = 0; i< messages.size(); i++)
                    {
                        DataSock.sendMessage("  " + messages.get(i));
                    }

                    DataSock.sendMessage(CheckMessages.isComplete);
                }

                if(code.equals("400"))
                {
                    DataSock.sendMessage("401 Logged Out");
                    DataSock.close();
                }
            }
        }

        catch (Exception ex)
        {
            System.out.println("Exception caught in thread: " + ex);
        }
    }
}

