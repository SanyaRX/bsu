import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Class that represents server
 * @author Alexander Rai
 * @version 1.0
 */
public class Server implements Runnable {
    static Logger serverLogger = LogManager.getLogger(Server.class.getName());
    /**
     * Port to connect to the server 
     */
    public static final int PORT = 8192;
    
    /**
     * List to save sockets
     */
    private static Map<SocketChannel, SocketAddress> clients = new HashMap<>();

    /**
     * Selector for channels
     */
    private Selector selector;

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }


    @Override
    public void run() {
        serverLogger.info("Server started working...");

        try {
            this.selector = Selector.open();
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.socket().bind( new InetSocketAddress("localhost", Server.PORT));
            serverChannel.register(this.selector, SelectionKey.OP_ACCEPT, SelectionKey.OP_WRITE);

            List<SocketAddress> clientsToSend = new LinkedList<>();
            Instant oddDeadline = Instant.now();

            while (true) {

                this.selector.select();
                Iterator keys = this.selector.selectedKeys().iterator();

                if(clientsToSend.isEmpty() && clients.size() > 1)
                {
                    oddDeadline = Instant.now().plus(10, ChronoUnit.SECONDS);
                    clientsToSend = addRandomSocketAddresses(clients.size(), 2);
                }

                while (keys.hasNext()) {
                    SelectionKey key = (SelectionKey) keys.next();

                    keys.remove();

                    if (!key.isValid()) {
                        continue;
                    }

                    if (key.isAcceptable()) {
                        registerClient(key);
                    }
                    else if (key.isWritable()) {
                        if (clientsToSend.contains(clients.get(key.channel()))
                                && System.currentTimeMillis() >= oddDeadline.toEpochMilli()) {
                            sendMessage(key, oddDeadline.toEpochMilli() + " : " + System.currentTimeMillis());
                            clientsToSend.remove(clients.get(key.channel()));
                        }
                    }
                }
            }
        } catch (IOException e) {
            serverLogger.info(e.getMessage());
        }
        serverLogger.info("Server finished working...");
    }


    /**
     * Registers clients in the server
     * @param key - SelectionKey for registration
     * @throws IOException
     */
    private void registerClient(SelectionKey key) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel channel = serverChannel.accept();
        channel.configureBlocking(false);
        Socket socket = channel.socket();
        SocketAddress remoteSocketAddress = socket.getRemoteSocketAddress();
        clients.put(channel, remoteSocketAddress);
        channel.register(this.selector, SelectionKey.OP_WRITE);

        System.out.println("Registered client with address: " + remoteSocketAddress);
        serverLogger.info("Registered client with address: " + remoteSocketAddress);
    }

    /**
     * Sends message to a clients
     * @param key - Selection key for sending
     * @param message - message to send
     * @throws IOException
     */
    private void sendMessage(SelectionKey key, String message) throws IOException {
        SocketChannel channel = (SocketChannel)key.channel();
        ByteBuffer buf = ByteBuffer.wrap(message.getBytes());
        channel.write(buf);
        serverLogger.info("Send message to " + clients.get(key.channel()));
    }

    /**
     * Returns list with random sockets addresses
     * @param range - number of registered clients
     * @param numberAddresses - number of clients to return
     */
    private List<SocketAddress> addRandomSocketAddresses(int range, int numberAddresses)
    {
        List<SocketAddress> socketAddresses = new LinkedList<>();

        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < range; i++) {
            list.add(new Integer(i));
        }

        Collections.shuffle(list);
        for (int i = 0; i < numberAddresses; i++) {
            socketAddresses.add((SocketAddress)clients.values().toArray()[list.get(i)]);
        }

        return socketAddresses;
    }
}
