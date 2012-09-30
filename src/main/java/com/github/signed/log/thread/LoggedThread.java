package com.github.signed.log.thread;

import com.github.signed.log.core.Descriptor;
import com.github.signed.log.core.Identification;
import com.github.signed.log.core.StringLogPart;

public class LoggedThread extends StringLogPart {

    public static final Identification LoggedThreadIdentification = new Identification("thread");

    public LoggedThread(String threadName) {
        super(new Descriptor(LoggedThreadIdentification, "thread", true), threadName);
    }
}
