package log_event.brodcast;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;


public class LogEventBroadcaster implements Runnable {
    private EventLoopGroup group;
    private final Bootstrap bootstrap;
    private final File file;

    public LogEventBroadcaster(
    		InetSocketAddress address, 
    		File file, NioEventLoopGroup group) {

        this.group = group;
        bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioDatagramChannel.class)
             .option(ChannelOption.SO_BROADCAST, true)
             .handler(new LogEventEncoder(address));
        this.file = file;
    }

    public void run(){
        Channel ch = null;
        try {
            ch = bootstrap.bind(0).sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long pointer = 0;
        for (;;) {
        	System.out.println("File sending");
            long len = file.length();
            if (len < pointer) {
                pointer = len;
            } else if (len > pointer) {

                RandomAccessFile raf = null;
                try {
                    raf = new RandomAccessFile(file, "r");
                    raf.seek(pointer);
                    String line;
                    while ((line = raf.readLine()) != null) {
                        ch.writeAndFlush(new LogEvent(null, -1,file.getAbsolutePath(), line));
                    }
                    pointer = raf.getFilePointer();
                    raf.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.interrupted();
                break;
            }
        }
    }

    public void stop() {
        group.shutdownGracefully();
    }


}
