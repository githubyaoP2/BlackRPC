package com.raft.consensus.core;

public class LeaderElection {

    private long lastVoteTime;
    private long heartTime;
    private int heartCount;


    public void startUp(){

    }

    private class ElectionTask implements Runnable{
        @Override
        public void run() {

        }
    }

    private class HeartTask implements Runnable{
        @Override
        public void run() {

        }
    }
}
