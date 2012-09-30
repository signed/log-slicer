package com.github.signed.log.thread;

import com.github.signed.log.core.Descriptor;
import com.github.signed.log.core.Identification;
import com.github.signed.log.core.StringLogPart;

public class LoggedThread extends StringLogPart {

    public static final Identification LoggedThreadIdentification = new Identification("thread");

    private final String threadName;

    public LoggedThread(String threadName) {
        super(new Descriptor(LoggedThreadIdentification, "thread", true));
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
