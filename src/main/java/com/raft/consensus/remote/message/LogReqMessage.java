package com.raft.consensus.remote.message;

import com.raft.consensus.entity.LogEntry;
import lombok.Data;

import java.io.Serializable;

@Data
public class LogReqMessage extends Message implements Serializable {

    public int term;//领导人的任期号
    public int leaderId;//领导人的 Id，以便于跟随者重定向请求
    public long prevLogIndex;//新的日志条目紧随之前的索引值
    public int prevLogTerm;//prevLogIndex 条目的任期号
    public LogEntry[] entries;//准备存储的日志条目（表示心跳时为空；一次性发送多个是为了提高效率）
    public long leaderCommit;//领导人已经提交的日志的索引值

    @Override
    protected String getMsgType() {
        return MSG_VOTE_REQ;
    }
}
