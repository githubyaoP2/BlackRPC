package com.raft.consensus.remote;

import com.raft.consensus.remote.message.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class DefaultMessageHandler extends SimpleChannelInboundHandler<Message> {


    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {

        switch (message.getMsgType()){
            case Message.MSG_VOTE_REQ:
                break;
            case Message.MSG_VOTE_RSP:
                break;
            case Message.MSG_LOG_REQ:
                break;
            case Message.MSG_LOG_RSP:
                break;
            case Message.MSG_UNKNOWN:
            default:
                break;
        }

        System.out.println(message);
    }
}
