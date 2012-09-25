package com.github.signed.log;

public class LoggedThread extends LogPart{

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
