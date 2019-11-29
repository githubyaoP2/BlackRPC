package com.raft.consensus.remote;

import com.raft.consensus.remote.message.Message;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class NettyClient {
    Bootstrap bootstrap;
    NioEventLoopGroup workerGroup;
    DefaultEventExecutorGroup eventExecutorGroup;
    String address;
    private Map<String, List<Channel>> chanelMap;
    private Map<String,Response> responseMap = new ConcurrentHashMap<>();


    public NettyClient(){
        bootstrap = new Bootstrap();
        workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());
        eventExecutorGroup = new DefaultEventExecutorGroup(2);
    }

    public void start(){
        bootstrap.channel(NioSocketChannel.class).group(workerGroup)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(eventExecutorGroup,  new DefaultDecoder<Message>(),new DefaultEncoder(),new DefaultMessageHandler());
                    }
                });
        connect();
//        try {
//            channelFuture = this.bootstrap.connect("127.0.0.1",8080).sync();
//
//        } catch (InterruptedException e1) {
//            throw new RuntimeException("this.serverBootstrap.bind().sync() InterruptedException", e1);
//        }
    }

    private void connect(){
        chanelMap = new HashMap<>();
        String[] addrs = address.split(",");
        for(final String addr:addrs){
            for(int i=0; i<10; i++){
                ChannelFuture channelFuture = this.bootstrap.connect(addr,8080);
                channelFuture.addListener(new ChannelFutureListener() {
                    public void operationComplete(ChannelFuture future) throws Exception {
                        List<Channel> channelList = chanelMap.get(addr);
                        if(channelList == null){
                            channelList = new ArrayList<>();
                        }
                        channelList.add(future.channel());
                    }
                });
            }
        }
    }

    public Message sendMessageSync(String des,Message message){
        Response response = new Response();
        responseMap.put(message.getUniqueID(),response);
        Channel channel = chanelMap.get(des).get(ThreadLocalRandom.current().nextInt(10));
        channel.writeAndFlush(message);
        return response.get();
    }

    public Response sendMessageAsync(String des,Message message){
        Response response = new Response();
        responseMap.put(message.getUniqueID(),response);
        Channel channel = chanelMap.get(des).get(ThreadLocalRandom.current().nextInt(10));
        channel.writeAndFlush(message);
        return response;
    }
}
