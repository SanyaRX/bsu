package server;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultEventExecutor;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.GlobalEventExecutor;
import log_event.brodcast.LogEventBroadcaster;
import log_event.brodcast.LogEventEncoder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.Executors;

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
    public static final int DEFAULT_PORT = 8192;
    public static final int LOG_PORT = 9000;
    /**
     * client.Client's channels
     */
    private ChannelGroup clients;


    /**
     * Port for server
     */
    private int port;

    
    public Server (int port){
        clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        this.port = port;
    }

    public static void main(String[] args) {
        Server server = new Server(DEFAULT_PORT);
        server.run();
    }


    @Override
    public void run() {
        serverLogger.info("Server started working...");

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        EventExecutor serverSenderEventExecutor = new DefaultEventExecutor(Executors.defaultThreadFactory());
        EventExecutor broadcastEventExecutor = new DefaultEventExecutor(Executors.defaultThreadFactory());

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new ServerHandler(clients));

                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            serverSenderEventExecutor.submit(new ServerSender(clients));
            broadcastEventExecutor.submit(new LogEventBroadcaster(new InetSocketAddress("255.255.255.255",
                    LOG_PORT), new File("logs/root.log"), (NioEventLoopGroup) workerGroup));

            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            serverLogger.info(e.getMessage());
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
        serverLogger.info("Server finished working...");
    }
}
