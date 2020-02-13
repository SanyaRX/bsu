package model.server;

import model.client.ServerClient;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Class that represents server
 * @author Alexander Rai
 * @version 1.0
 */
public class Server {
    static Logger serverLogger = LogManager.getLogger(Server.class.getName());
    /**
     * Port to connect to the server 
     */
    public static final int PORT = 8192;
    
    /**
     * List to save sockets
     */
    private static ArrayList<ServerClient> clients = new ArrayList<ServerClient>();


    public static void main(String[] args) {
        serverLogger.info("Server started working...");
        ServerSender serverSender = new ServerSender(clients);
        serverSender.start();

        try (ServerSocket server = new ServerSocket(PORT)){
            while (true) {
                Socket socket = server.accept();

                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

                String clientName = input.readLine();

                if(isClientExists(clientName)) {
                    writer.println("Client with name " + clientName + " is already exists");
                    writer.println("stop");
                } else {
                    System.out.println("Client " + clientName + " connected...");
                    clients.add(new ServerClient(clientName, writer, input));
                }
            }
        } catch (IOException e) {
            serverLogger.error(e.getMessage());
        }
        serverLogger.info("Server finished working...");
    }

    private static Boolean isClientExists(String name){
        for (ServerClient client : clients){
            if(client.getName().equals(name))
                return true;
        }

        return false;
    }
}
