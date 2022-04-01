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
    private MyStreamSocket sock;
    private InetAddress sHost;
    private int sPort;
    GUI myGui;

    EchoClientHelper2(String hostName, String portNum, GUI myGui) throws SocketException, UnknownHostException, IOException
    {
        this.myGui = myGui;
        this.sHost = InetAddress.getByName(hostName);
        this.sPort = Integer.parseInt(portNum);
        this.sock = new MyStreamSocket(this.sHost, this.sPort);
        System.out.println("Connection request made");
    }


    public String login( String message) throws SocketException, IOException
    {
        String echo = "";
        sock.sendMessage( message);

        echo = sock.receiveMessage();
        return echo;
    }

    public void sendMessage( String message) throws SocketException, IOException
    {
        String echo = "";
        sock.sendMessage( message);
    }

    public String downloadMessage( String message) throws SocketException, IOException
    {
        String echo = "";
        sock.sendMessage( message);

        echo = sock.receiveMessage();

        while(!echo.equals(CheckMessages.isComplete))
        {
            myGui.txtArea.append(echo);
            echo = sock.receiveMessage();
        }

        return echo;
    }

    public String logoff( String message) throws SocketException, IOException
    {
        String echo = "";
        sock.sendMessage( message);
        echo = sock.receiveMessage();
        return echo;
    }

    public void done( ) throws SocketException, IOException
    {
        sock.sendMessage(endMessage);
        sock.close( );
    }

}
