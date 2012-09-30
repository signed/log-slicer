package com.github.signed.log.thread;

import com.github.signed.log.core.Descriptor;
import com.github.signed.log.core.StringLogPart;
import com.github.signed.log.core.parser.LogEntryParser;

public class LoggedThread extends StringLogPart {

    public LoggedThread(String threadName) {
        super(new Descriptor(LogEntryParser.LoggedThreadIdentification, "thread", true), threadName);
    }
}
