package com.raft.consensus.entity;

import lombok.Data;

@Data
public class LogEntry {
    private long index;
    private int term;
    private String command;

}
