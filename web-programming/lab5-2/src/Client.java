import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.security.InvalidParameterException;
import java.util.Random;

/**
 * Class that represents client
 * @author Alexander Rai
 * @version 1.0
 */
public class Client extends Thread{
    static Logger clientLogger = LogManager.getLogger(Client.class.getName());

    /**
     * SocketChannel to connect to the server
     */
    private SocketChannel socketChannel;

    /**
     * Client's name
     */
    private String name;

    /**
     * Ip address
     */
    private String ipAddress;

    /**
     * Connection port
     */
    private int connectionPort;

    /**
     * Creates new client class object
     * @param name - client's name
     * @param ipAddress - ip address
     * @param connectionPort - connection port
     */
    public Client(String name, String ipAddress, int connectionPort){
        if(connectionPort < 0)
            throw new InvalidParameterException("Invalid port number(<0).");

        this.name = name;
        this.ipAddress = ipAddress;
        this.connectionPort = connectionPort;
    }

    @Override
    public void run() {
        clientLogger.info("Client " + name + " started working");

        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress(ipAddress, connectionPort));
            while(! socketChannel.finishConnect() );
            while (true) {
                String serverMessage;

                if((serverMessage = readMessage()) == null)
                    continue;

                if (serverMessage.equals("stop")) {
                    socketChannel.close();
                    break;
                }
                System.out.println("Received server's message: " + serverMessage);
                clientLogger.info("Client: " + name + " received message: " + serverMessage);
            }
        } catch (IOException e) {
            clientLogger.error(e.getMessage());
            e.printStackTrace();
        }

        clientLogger.info("Client " + name + " finished working");
    }

    /**
     * Reads message from server
     * @return - message
     * @throws IOException
     */
    private String readMessage() throws IOException{
        ByteBuffer buf = ByteBuffer.allocate(128);
        int bytesRead = socketChannel.read(buf);

        if(bytesRead == 0 || bytesRead == -1)
            return null;

        return new String(buf.array());
    }

    public static void main(String[] args) {
        Client client = new Client("Client " + (new Random()).nextInt(3), "localhost", Server.PORT);
        client.run();
    }
}
