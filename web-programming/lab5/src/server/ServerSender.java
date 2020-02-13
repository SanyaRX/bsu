package model.server;

import model.client.ServerClient;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.PrintWriter;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * Class that represents class that sends messages from server to clients
 * @author Alexander Rai
 * @version 1.0
 */
public class ServerSender extends Thread {
    static Logger serverSenderLogger = LogManager.getLogger(ServerSender.class.getName());

    /**
     * List to save sockets
     */
    private ArrayList<ServerClient> clients;

    /**
     * Creates server sender object
     * @param clients - server client's sockets
     */
    public ServerSender(ArrayList<ServerClient> clients){
        this.clients = clients;
    }


    /**
     * Thread's run method
     */
    @Override
    public void run() {
        serverSenderLogger.info("ServerSender started working");

        Instant oddDeadline = Instant.now().plus(10, ChronoUnit.SECONDS);

        while(System.currentTimeMillis() < oddDeadline.toEpochMilli()){}

        if(clients.size() != 0) {
            for (int i = 0; i < clients.size(); i += 2) {
                clients.get(i).getWriter().println("You are odd client. Deadline time: " + oddDeadline);
                serverSenderLogger.info("Sent message");
            }
        } else {
            serverSenderLogger.info("There are no clients");
        }

        finishClientsWork();

        serverSenderLogger.info("ServerSender finished working");
    }

    /**
     * Sends message 'stop' for clients
     */
    private void finishClientsWork(){
        for (ServerClient client : clients) {
            client.getWriter().println("stop");
        }
    }


}
