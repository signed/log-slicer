package com.github.signed.log.thread;

import com.github.signed.log.core.LogPart;

public class LoggedThread extends LogPart {

    private final String threadName;

    public LoggedThread(String threadName) {
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
}
