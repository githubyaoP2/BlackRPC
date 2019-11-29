package com.raft.consensus.entity;

import com.raft.consensus.core.LeaderElection;
import com.raft.consensus.core.LogReplication;
import com.raft.consensus.remote.NettyClient;
import lombok.Data;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 服务器节点
 */
@Data
public class Node {

    public static final String STATE_LEADER = "leader";
    public static final String STATE_FOLLOWER = "follower";
    public static final String STATE_CANDIDATE = "candidate";

    //所有服务器上持久存在的
    private int currentTerm;//服务器最后一次知道的任期号（初始化为 0，持续递增）
    private int votedFor;//在当前获得选票的候选人的 Id
    private LogEntry[] logEntries;//日志条目集；每一个条目包含一个用户状态机执行的指令，和收到时的任期号

    //所有服务器上经常变的
    private long commitIndex;//已知的最大的已经被提交的日志条目的索引值
    private long lastApplied;//最后被应用到状态机的日志条目索引值（初始化为 0，持续递增）

    //在领导人里经常改变的 （选举后重新初始化）
    private long[] nextIndex;//对于每一个服务器，需要发送给他的下一个日志条目的索引值（初始化为领导人最后索引值加一）
    private long[] matchIndex;//对于每一个服务器，已经复制给他的日志的最高索引值

    private LeaderElection leaderElection;
    private LogReplication logReplication;
    private NettyClient nettyClient;

    private String[] peers;//集群
    private volatile String state = STATE_CANDIDATE;
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors()*2);

    public void startUp(){
        scheduledExecutorService.scheduleAtFixedRate(new ElectionTask(),0,100, TimeUnit.MILLISECONDS);
        scheduledExecutorService.scheduleAtFixedRate(new HeartBeatTask(),1,1,TimeUnit.SECONDS);
    }

    //心跳任务
    class HeartBeatTask implements Runnable{

        Node node;

        public HeartBeatTask(Node node){
            this.node = node;
        }

        @Override
        public void run() {
            switch (node.state){
                case STATE_LEADER:
                case STATE_FOLLOWER:
                    break;
                case STATE_CANDIDATE:

            }
        }
    }

    //选举任务
    class ElectionTask implements Runnable{

        Node node;

        public ElectionTask(Node node){
            this.node = node;
        }

        @Override
        public void run() {
            switch (node.state){
                case STATE_LEADER:
                case STATE_FOLLOWER:
                    break;
                case STATE_CANDIDATE:

            }
        }
    }

    public void putLog(LogEntry logEntry){

    }
}
