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
    private final MyStreamSocket socket;
    GUI ui;

    EchoClientHelper2(String hostName, String portNum, GUI myGui) throws SocketException, UnknownHostException, IOException
    {
        this.ui = myGui;
        InetAddress sHost = InetAddress.getByName(hostName);
        int sPort = Integer.parseInt(portNum);
        this.socket = new MyStreamSocket(sHost, sPort);
        System.out.println("Connection request made");
    }

    public String login( String message) throws SocketException, IOException
    {
        String echo = "";
        socket.sendMessage( message);

        echo = socket.receiveMessage();
        return echo;
    }

    public void sendMessage( String message) throws SocketException, IOException
    {
        String echo = "";
        socket.sendMessage( message);
    }

    public String downloadMessage( String message) throws SocketException, IOException
    {
        String echo = "";
        socket.sendMessage( message);

        echo = socket.receiveMessage();

        while(!echo.equals(CheckMessages.isComplete))
        {
            ui.txtArea.append(echo);
            echo = socket.receiveMessage();
        }

        return echo;
    }

    public String logoff( String message) throws SocketException, IOException
    {
        String echo = "";
        socket.sendMessage( message);
        echo = socket.receiveMessage();
        return echo;
    }

    public void done( ) throws SocketException, IOException
    {
        socket.sendMessage(endMessage);
        socket.close( );
    }

}
