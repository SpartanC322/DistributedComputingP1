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
    static String echo;
    String hName = "localhost";
    String pNum = "42";

    EchoClientHelper2 help;

    public EchoClient2()
    {
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
    }
}