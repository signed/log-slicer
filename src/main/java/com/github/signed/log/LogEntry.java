package com.github.signed.log;

import org.joda.time.DateTime;

public class LogEntry {

    public static LogEntry createLogEntry(String text) {
        DateTime timestamp = new TimeStampExtractor(text).extract();
        LoggedThread thread = new ThreadExtractor(text).extract();

        return new LogEntry(text, timestamp, thread);
    }


    public final String text;
    private final DateTime timestamp;
    private final LoggedThread thread;

    public LogEntry(String text, DateTime timestamp, LoggedThread thread) {
        this.text = text;
        this.timestamp = timestamp;
        this.thread = thread;
    }

    public DateTime taken() {
        return timestamp;
    }

    public LoggedThread thread() {
        return thread;
    }
}
