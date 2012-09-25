package com.github.signed.log.thread;

import com.github.signed.log.core.LogPart;

public class LoggedThread extends LogPart implements Comparable<LoggedThread> {
    public static final LoggedThread Null = new LoggedThread(null){
        @Override
        public String toString() {
            return "NullLoggedThread";
        }

        @Override
        public void dumpInto(StringBuilder builder) {
            //do nothing
        }
    };

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

    @Override
    public int compareTo(LoggedThread o) {
        return threadName.compareTo(o.threadName);
    }
}
