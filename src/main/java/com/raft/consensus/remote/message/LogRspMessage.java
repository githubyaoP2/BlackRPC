package com.raft.consensus.remote.message;

import lombok.Data;

import java.io.Serializable;

@Data
public class LogRspMessage extends Message implements Serializable {
    public int term;//当前的任期号，用于领导人去更新自己
    public boolean success;//跟随者包含了匹配上 prevLogIndex 和 prevLogTerm 的日志时为真

    @Override
    protected String getMsgType() {
        return MSG_VOTE_REQ;
    }
}
