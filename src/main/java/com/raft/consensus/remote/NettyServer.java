package com.raft.consensus.remote;

import com.raft.consensus.remote.message.Message;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;

public class NettyServer {

    ServerBootstrap serverBootstrap;
    NioEventLoopGroup bossGroup;
    NioEventLoopGroup workerGroup;
    DefaultEventExecutorGroup eventExecutorGroup;

    public NettyServer(){
        serverBootstrap = new ServerBootstrap();
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());
        eventExecutorGroup = new DefaultEventExecutorGroup(2);
    }

    public void start(){
        serverBootstrap.channel(NioServerSocketChannel.class).group(bossGroup,workerGroup)
                .option(ChannelOption.SO_BACKLOG, 1024)//服务端请求等待队列
                .option(ChannelOption.SO_REUSEADDR, true)//重复使用或共用监听端口
                .option(ChannelOption.SO_KEEPALIVE, false)//心跳
                .childOption(ChannelOption.TCP_NODELAY,true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(eventExecutorGroup,  new DefaultDecoder<Message>(),new DefaultEncoder(),new DefaultMessageHandler());
                    }
                });
        try {
            this.serverBootstrap.bind(new InetSocketAddress(8080)).sync();
        } catch (InterruptedException e1) {
            throw new RuntimeException("this.serverBootstrap.bind().sync() InterruptedException", e1);
        }
    }

    public static void main(String[] args) {
        NettyServer nettyServer = new NettyServer();
        nettyServer.start();
    }
}
