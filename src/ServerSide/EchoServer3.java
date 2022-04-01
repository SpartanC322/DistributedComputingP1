package ServerSide;

import javax.net.ssl.SSLServerSocketFactory;
import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

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
    public static ArrayList<String> allMessages = new ArrayList<String>();

    public static void main(String[] args) throws IOException
    {
        int serverPort = 42;    // default port
        String message;

        if (args.length == 1 )
        {
            serverPort = Integer.parseInt(args[0]);
        }

        System.setProperty("javax.net.ssl.trustStore", "za.store");
        System.setProperty("javax.net.ssl.keyStorePassword", "password");
        ServerSocket serverSocket = (SSLServerSocketFactory.getDefault()).createServerSocket(4444);
        System.out.println("Server up and ready");

        try
        {
            // instantiates a stream socket for accepting
            //   connections
            ServerSocket myConnectionSocket = new ServerSocket(serverPort);
            System.out.println("Echo server ready.");

            while(true)
            {  // forever loop
                // wait to accept a connection
                // System.out.println("Waiting for a connection.");
                MyStreamSocket myDataSocketSSL = new MyStreamSocket(myConnectionSocket.accept( ));
                System.out.println("connection accepted");
                // Start a thread to handle this client's session
                Thread theThreadSSL = new Thread(new EchoServerThread(myDataSocketSSL));
                theThreadSSL.start();
                // and go on to the next client
            } //end while forever

        } // end try
        catch(Exception ex)
        {
            ex.printStackTrace( );
        } // end catch
    } //end main
} // end class

