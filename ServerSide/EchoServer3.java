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
    public static ArrayList users = new ArrayList();
    public static ArrayList<String> messages = new ArrayList();

    public static void main(String[] args) throws IOException
    {
        int serverPort = 42;  
        String message;

        if (args.length == 1 )
        {
            serverPort = Integer.parseInt(args[0]);
        }

        System.setProperty("javax.net.ssl.trustStore", "za.store");
        System.setProperty("javax.net.ssl.keyStorePassword", "password");
        ServerSocket serverSock = (SSLServerSocketFactory.getDefault()).createServerSocket(4444);
        System.out.println("Server up and ready");

        try
        {
            ServerSocket ConnectionSock = new ServerSocket(serverPort);
            System.out.println("Echo server ready.");

            while (true)
            {
                StreamSocket DataSockSSL = new StreamSocket(ConnectionSock.accept( ));
                System.out.println("Connected");
                Thread theThreadSSL = new Thread(new EchoServerThread(DataSockSSL));
                theThreadSSL.start();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace( );
        }
    }
}

