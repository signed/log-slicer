package com.github.signed.log.thread;

import com.github.signed.log.core.BaseLogPart;
import com.github.signed.log.core.Identification;

public class LoggedThread extends BaseLogPart {

    public static final Identification LoggedThreadIdentification = new Identification("thread");

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
    protected Identification identification() {
        return LoggedThreadIdentification;
    }
}
