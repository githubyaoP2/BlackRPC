package com.raft.consensus.remote.message;

import lombok.Data;

import java.io.Serializable;

@Data
public class VoteRspMessage extends Message implements Serializable {
    int term;//当前任期号，以便于候选人去更新自己的任期号
    boolean voteGranted;//候选人赢得了此张选票时为真

    @Override
    protected String getMsgType() {
        return MSG_VOTE_REQ;
    }
}
