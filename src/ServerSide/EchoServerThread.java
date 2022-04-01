package ServerSide;
import ClientSide.CheckMessages;
import ClientSide.GUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static ServerSide.EchoServer3.allMessages;
import static ServerSide.EchoServer3.users;

/**
 * This module is to be used with a concurrent Echo server.
 * Its run method carries out the logic of a client session.
 * @author M. L. Liu
 */

class EchoServerThread extends Thread {

    MyStreamSocket myDataSocket;
    GUI myGui;

    EchoServerThread(Socket myDataSocket)
    {
        this.myDataSocket = (MyStreamSocket) myDataSocket;
        myGui = new GUI();
    }

    public void run( )
    {
        boolean done = false;
        String message;
        String code = "";
        String trimmed;
        String[] splitString;
        String username = "";
        String pass = "";
        String sendMessageText = "";
        boolean usernameExists = false;


        // PrintWriter printWriter = new PrintWriter(myDataSocket.getOutputStream(), true) ;
        // BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(myDataSocket.getInputStream()));
        System.out.println("User is" + "now connected to the server");
        //while(true) System.out.println(bufferedReader.readLine() + "echo");

        try
        {
            while (done != true)
            {
                message = myDataSocket.receiveMessage( ) ;
                System.out.println("message received: "+ message);
                code = message.substring(0,3);
                //System.out.print("Code " + code);

                if(code.equals("100"))
                {
                    //split up message into parts
                    System.out.println("message received: "+ message);
                    trimmed = message.trim();
                    splitString = trimmed.split("\\s+");

                    if(splitString.length <= 4)
                    {
                        //user name or pass word is blank
                        myDataSocket.sendMessage("103 Error. Username or Password is blank");


                    }

                    else
                    {
                        //continue
                        username = splitString[1];
                        pass = splitString[2];

                        System.out.println(username + "username");
                        System.out.print(pass + "pass");


                        for(int i = 0; i < users.size(); i++)
                        {
                            if(users.get(i) == username)
                            {
                                //username found get out of loop
                                usernameExists = true;
                                break;
                            }
                        }//end of for loop

                        if(usernameExists)
                        {
                            //username exists. Just login
                            myDataSocket.sendMessage("101 Login Successful");
                        }

                        else
                        {
                            //username doesn't exist. Add to list
                            users.add(username);
                            myDataSocket.sendMessage("102 Login Successful. User added.");

                        }

                        myGui.showActionsGUI();
                    }//end else

                }//if code == 100

                if(code.equals("200"))
                {
                    //store message
                    System.out.println("201 Message Sent");
                    trimmed = message.trim();
                    splitString = trimmed.split("\\s+");

                    System.out.println(splitString[1]);

                    if(splitString.length <= 4)
                    {
                        //no message was sent
                        System.out.println("202 No message in text box");
                    }

                    else
                    {
                        sendMessageText = splitString[1];
                        allMessages.add(sendMessageText);
                    }

                    //make new os
                    // message stored

                }//if code == 200

                if(code.equals("300"))
                {
                    // get messages
                    String currentMessage = "";

                    for(int i = 0; i< allMessages.size(); i++)
                    {
                        //loop through messages and add to text area
                        myDataSocket.sendMessage("  " + allMessages.get(i));
                        //GUI.getMessages(currentMessage);
                    }

                    myDataSocket.sendMessage(CheckMessages.isComplete);

                    // make another os
                    // little more thinking

                } //if code == 300

                if(code.equals("400"))
                {
                    myDataSocket.sendMessage("401 Logged Out");
                    myDataSocket.close();
                    // take 5 min break
                    // logout function

                }//if code == 400

            } //end while !done
        }// end try

        catch (Exception ex)
        {
            System.out.println("Exception caught in thread: " + ex);
        } // end catch
    } //end run
} //end class

