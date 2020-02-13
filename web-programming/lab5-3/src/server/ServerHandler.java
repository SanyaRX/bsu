package server;

import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;

import java.util.*;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * Client's channels
     */
    private ChannelGroup clients;


    public ServerHandler(ChannelGroup clients){
        this.clients = clients;
    }


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        clients.add(ctx.channel());

        Server.serverLogger.info("Client registered " + ctx.channel().remoteAddress());
        System.out.println("Client registered " + ctx.channel().remoteAddress());
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        Server.serverLogger.info("Client unregistered " + ctx.channel().remoteAddress());
        System.out.println("Client unregistered " + ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println(cause.getMessage());
        ctx.close();
    }



}
