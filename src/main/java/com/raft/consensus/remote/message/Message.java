package com.raft.consensus.remote.message;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class Message implements Serializable {

    public static final String MSG_UNKNOWN = "unknow";
    public static final String MSG_VOTE_REQ = "voteRequest";
    public static final String MSG_VOTE_RSP = "voteResponse";
    public static final String MSG_LOG_REQ = "logRequest";
    public static final String MSG_LOG_RSP = "logResponse";
    private String uniqueID;

    private String generateUniqueID(){
        return UUID.randomUUID().toString();
    }

    public String getMsgType(){
        return MSG_UNKNOWN;
    }
}

