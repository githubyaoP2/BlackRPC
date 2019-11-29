package com.raft.consensus.remote;

import com.raft.consensus.remote.message.Message;
import lombok.Data;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@Data
public class Response {

    Logger logger = Logger.getLogger(Response.class.getName());

    private Message message;
    private CountDownLatch resultLatch = new CountDownLatch(1);

    public Message get(){
        try {
            resultLatch.await(3, TimeUnit.SECONDS);
        }catch (InterruptedException e){
            logger.log(Level.WARNING,"");
        }
        return message;
    }

    public void set(Message message){
        this.message = message;
        resultLatch.countDown();
    }
}
