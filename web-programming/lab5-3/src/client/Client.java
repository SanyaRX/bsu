package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import log_event.recipient.LogEventDecoder;
import log_event.recipient.LogEventHandler;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import server.Server;

import java.io.*;
import java.net.BindException;
import java.net.InetSocketAddress;
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
     * client.Client's name
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

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ClientHandler());
                }
            });

            ChannelFuture f = b.connect(ipAddress, connectionPort).sync();

            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler( new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel channel)
                                throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();
                            pipeline.addLast(new LogEventDecoder());
                            pipeline.addLast(new LogEventHandler());
                        }
                    } )
                    .localAddress(new InetSocketAddress(Server.LOG_PORT));


            try {
                Channel channel = bootstrap.bind().sync().channel();
                //channel.closeFuture().sync();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            clientLogger.info(e.getMessage());
        } finally {
            workerGroup.shutdownGracefully();
        }

        clientLogger.info("Client " + name + " finished working");
    }

    public static void main(String[] args) {
        Client client = new Client("Client " + (new Random()).nextInt(3), "localhost", Server.DEFAULT_PORT);
        client.run();
    }
}
