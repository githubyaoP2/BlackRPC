package com.raft.consensus.remote;

import com.raft.consensus.remote.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

public class DefaultEncoder extends MessageToByteEncoder<Message> {
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, ByteBuf byteBuf) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(message);
        byteBuf.writeBytes(byteArrayOutputStream.toByteArray());
        byteBuf.writeBytes("$$$".getBytes());
    }
}
