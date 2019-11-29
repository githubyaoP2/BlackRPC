package com.raft.consensus.remote;

import com.raft.consensus.remote.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledHeapByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

//
public class DefaultDecoder<T> extends DelimiterBasedFrameDecoder {

    static int maxLength = 1000;

    public DefaultDecoder() {
        super(maxLength, Unpooled.copiedBuffer("$$$".getBytes()));
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
        ByteBuf byteBuf = (ByteBuf) super.decode(ctx,buffer);
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
        T message = (T) ois.readObject();
        return message;
    }
}
