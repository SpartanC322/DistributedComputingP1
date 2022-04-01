package ServerSide;

import java.net.*;
/**
 * A class to use with MyServerDatagramSocket for
 * returning a message and the sender's address
 * @author M. L. Liu
 */

public class DatagramMessage
{
    private String message;
    private InetAddress sendAddress;
    private int sendPort;
    public void putVal(String message, InetAddress address, int port)
    {
        this.message = message;
        this.sendAddress = address;
        this.sendPort = port;
    }

    public String getMessage( )
    {
        return this.message;
    }

    public InetAddress getAddress( )
    {
        return this.sendAddress;
    }

    public int getPort( )
    {
        return this.sendPort;
    }
}

