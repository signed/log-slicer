package com.github.signed.log.thread;

import com.github.signed.log.core.BaseLogPart;

public class LoggedThread extends BaseLogPart implements Comparable<LoggedThread> {
    private final String threadName;

    public LoggedThread(String threadName) {
        super("thread");
        this.threadName = threadName;
    }

    @Override
    public String toString() {
        return threadName;
    }

    @Override
    public void dumpInto(StringBuilder builder) {
        builder.append(threadName);
    }

    @Override
    public int compareTo(LoggedThread o) {
        return threadName.compareTo(o.threadName);
    }
}
