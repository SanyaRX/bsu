package server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ServerSender implements Runnable {
    static Logger serverSenderLogger = LogManager.getLogger(Server.class.getName());

    /**
     * Time to send message after
     */
    private Instant deadline;

    /**
     * Delay in seconds
     */
    private int delay = 10;

    /**
     * client.Client's channels
     */
    private ChannelGroup clients;
    /**
     * Clients to send message to
     */
    private List<Channel> clientsToSend = new LinkedList<>();


    public ServerSender(ChannelGroup clients){
        this.clients = clients;
        refreshDeadline();
    }
    @Override
    public void run() {

        serverSenderLogger.info("Sender started");
        while(true){

            while(System.currentTimeMillis() < deadline.toEpochMilli());

            if(clientsToSend.isEmpty() && clients.size() > 1)
            {
                refreshDeadline();
                clientsToSend = addRandomSocketAddresses(clients.size(), 2);
            }

            for (Channel channel : clientsToSend){
                serverSenderLogger.info("Message sanded");
                final ByteBuf time = channel.alloc().buffer(4);
                time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
                channel.writeAndFlush(time);
            }
            clientsToSend.removeAll(clientsToSend);
        }
    }

    /**
     * Returns list with random sockets addresses
     * @param range - number of registered clients
     * @param numberChannels - number of clients to return
     */
    private List<Channel> addRandomSocketAddresses(int range, int numberChannels)
    {
        List<Channel> channels = new LinkedList<>();

        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < range; i++) {
            list.add(new Integer(i));
        }

        Collections.shuffle(list);
        for (int i = 0; i < numberChannels; i++) {
            channels.add((Channel)clients.toArray()[list.get(i)]);
        }

        return channels;
    }


    /**
     * Changes deadline according to delay
     */
    private void refreshDeadline(){
        deadline = Instant.now().plus(delay, ChronoUnit.SECONDS);
    }

}
