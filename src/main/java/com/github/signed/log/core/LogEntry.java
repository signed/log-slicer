package com.github.signed.log.core;

import com.github.signed.log.thread.LoggedThread;
import com.github.signed.log.thread.LoggedThreadExtractor;
import com.github.signed.log.timestamp.TimeStamp;
import com.github.signed.log.timestamp.TimeStampExtractor;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.Map;

public class LogEntry {

    public static LogEntry createLogEntry(String text) {
        Collection<LogPart> bucket = Lists.newArrayList();
        new TimeStampExtractor(text).passLogPartTo(bucket);
        new LoggedThreadExtractor(text).passLogPartTo(bucket);
        return new LogEntry(text, bucket);
    }

    public static final LogEntry Null = new LogEntry("", ImmutableList.of(TimeStamp.Null, LoggedThread.Null));


    public final String text;
    private final Map<Class<? extends LogPart>, Object> parts = Maps.newHashMap();

    public LogEntry(String text, Collection<LogPart> availableParts) {
        this.text = text;
        for (LogPart availablePart : availableParts) {
            parts.put(availablePart.getClass(), availablePart);
        }
    }

    public TimeStamp taken() {
        return getPart(TimeStamp.class);
    }

    public LoggedThread thread() {
        return getPart(LoggedThread.class);
    }

    @SuppressWarnings("unchecked")
    public<T extends LogPart> T getPart(Class<T> timeStampClass) {
        return (T) parts.get(timeStampClass);
    }
}
