package model.client;

import model.server.Server;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
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
     * Socket to connect to the server
     */
    private Socket socket;

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
            socket = new Socket(ipAddress, connectionPort);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            writer.println(name);
            while (true) {
                String serverMessage = input.readLine();

                if (serverMessage.equals("stop")) {
                    socket.close();
                    input.close();
                    break;
                }
                System.out.println("Received server's message: " + serverMessage);
                clientLogger.info("Client: " + name + " received message: " + serverMessage);
            }
        } catch (UnknownHostException e) {
            clientLogger.error(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            clientLogger.error(e.getMessage());
            e.printStackTrace();
        }

        clientLogger.info("Client " + name + " finished working");
    }

    public static void main(String[] args) {
        Client client = new Client("Client " + (new Random()).nextInt(3), "localhost", Server.PORT);
        client.run();
    }
}
