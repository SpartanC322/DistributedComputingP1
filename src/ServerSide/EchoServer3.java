package ServerSide;

import javax.net.ssl.SSLServerSocketFactory;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * This module contains the application logic of an echo server
 * which uses a stream-mode socket for interprocess communication.
 * Unlike EchoServer2, this server services clients concurrently.
 * A command-line argument is required to specify the server port.
 * @author M. L. Liu
 */

public class EchoServer3
{
    public static ArrayList<String> users = new ArrayList<String>();
    public static ArrayList<String> messages = new ArrayList<String>();

    public static void main(String[] args) throws IOException
    {
        int servPort = 42;
        String message;

        if (args.length == 1 )
        {
            servPort = Integer.parseInt(args[0]);
        }

        System.setProperty("javax.net.ssl.trustStore", "st.store");
        System.setProperty("javax.net.ssl.keyStorePassword", "password");
        ServerSocket serverSocket = (SSLServerSocketFactory.getDefault()).createServerSocket(4444);
        System.out.println("Server up and ready");

        try
        {
            ServerSocket myConnectionSocket = new ServerSocket(servPort);
            System.out.println("Echo server ready.");

            while(true)
            {
                System.out.println("Waiting for a connection.");
                MyStreamSocket myDataSocketSSL = new MyStreamSocket(myConnectionSocket.accept( ));

                System.out.println("Connection accepted");

                Thread theThreadSSL = new Thread(new EchoServerThread(myDataSocketSSL));
                theThreadSSL.start();
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace( );
        }
    }
}

