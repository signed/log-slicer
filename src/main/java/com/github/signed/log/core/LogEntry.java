package com.github.signed.log.core;

import com.github.signed.log.RawLogEntry;
import com.github.signed.log.thread.LoggedThread;
import com.github.signed.log.thread.LoggedThreadExtractor;
import com.github.signed.log.timestamp.TimeStamp;
import com.github.signed.log.timestamp.TimeStampExtractor;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lang.ArgumentClosure;

import java.util.Collection;
import java.util.Map;

public class LogEntry {

    public static LogEntry createLogEntry(String text) {
        Collection<LogPart> bucket = Lists.newArrayList();
        bucket.add(new RawLogEntry(text));
        new TimeStampExtractor(text).passLogPartTo(bucket);
        new LoggedThreadExtractor(text).passLogPartTo(bucket);
        return new LogEntry(bucket);
    }

    public static final LogEntry Null = new LogEntry(ImmutableList.of(TimeStamp.Null, LoggedThread.Null));
    private final Map<Class<? extends LogPart>, LogPart> parts = Maps.newHashMap();

    public LogEntry(Collection<LogPart> availableParts) {
        for (LogPart availablePart : availableParts) {
            parts.put(availablePart.getClass(), availablePart);
        }
    }

    @SuppressWarnings("unchecked")
    public<T extends LogPart> T getDerivedPart(Class<T> type) {
        return (T) getPart(type);
    }

    public<T extends LogPart> LogPart getPart(Class<T> type) {
        if(parts.containsKey(type)){
            return parts.get(type);
        }
        return NullLogPart.TheNullLogPart;
    }

    public <T extends LogPart> void  dumpPartInto(Class<T> type, ArgumentClosure<String> closure) {
        LogPart part = getPart(type);
        StringBuilder builder = new StringBuilder();
        if(null != part){
            part.dumpInto(builder);
            closure.excecute(builder.toString());
        }
    }
}
