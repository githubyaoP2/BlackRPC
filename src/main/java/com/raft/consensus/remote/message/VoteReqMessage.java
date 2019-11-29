package com.raft.consensus.remote.message;

import lombok.Data;

import java.io.Serializable;

@Data
public class VoteReqMessage extends Message implements Serializable {
    int term;//候选人的任期号
    int candidateId;//请求选票的候选人的 Id
    long lastLogIndex;//候选人的最后日志条目的索引值
    int lastLogTerm;//候选人最后日志条目的任期号

    @Override
    protected String getMsgType() {
        return MSG_VOTE_REQ;
    }
}
