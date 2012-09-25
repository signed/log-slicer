package com.github.signed.log.core;

import com.github.signed.log.thread.LoggedThread;
import com.github.signed.log.thread.LoggedThreadExtractor;
import com.github.signed.log.timestamp.TimeStamp;
import com.github.signed.log.timestamp.TimeStampExtractor;

public class LogEntry {

    public static LogEntry createLogEntry(String text) {
        TimeStamp timestamp = new TimeStampExtractor(text).extract();
        LoggedThread thread = new LoggedThreadExtractor(text).extract();

        return new LogEntry(text, timestamp, thread);
    }


    public final String text;
    private final TimeStamp timestamp;
    private final LoggedThread thread;

    public LogEntry(String text, TimeStamp timestamp, LoggedThread thread) {
        this.text = text;
        this.timestamp = timestamp;
        this.thread = thread;
    }

    public TimeStamp taken() {
        return timestamp;
    }

    public LoggedThread thread() {
        return thread;
    }
}