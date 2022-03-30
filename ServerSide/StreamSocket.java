package ServerSide;

import java.net.*;
import java.io.*;

/**
 * A wrapper class of Socket which contains
 * methods for sending and receiving messages
 * @author M. L. Liu
 */
public class StreamSocket extends Socket {
    private Socket sock;
    private BufferedReader input;
    private PrintWriter output;

    StreamSocket(InetAddress host, int port ) throws SocketException, IOException
    {
        sock = new Socket(host, port );
        setStreams( );

    }

    StreamSocket(Socket sock)  throws IOException
    {
        this.sock = sock;
        setStreams( );
    }

    private void setStreams( ) throws IOException
    {
        InputStream inStream = sock.getInputStream();
        input = new BufferedReader(new InputStreamReader(inStream));
        OutputStream outStream = sock.getOutputStream();
        output = new PrintWriter(new OutputStreamWriter(outStream));
    }

    public void sendMessage(String message) throws IOException
    {
        output.print(message + "\n");
        output.flush();
    }

    public String receiveMessage( ) throws IOException
    {
        String message = input.readLine( );
        return message;
    }

    public void close( ) throws IOException
    {
        sock.close( );
    }
}

