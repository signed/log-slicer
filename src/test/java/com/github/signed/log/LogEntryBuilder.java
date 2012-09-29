package com.github.signed.log;

import com.github.signed.log.core.LogEntry;
import com.github.signed.log.core.LogPart;
import com.google.common.collect.Lists;

import java.util.List;

public class LogEntryBuilder {
    public static LogEntryBuilder ofParts(LogPart... parts) {
        LogEntryBuilder builder = new LogEntryBuilder();
        for (LogPart part : parts) {
            builder.with(part);
        }
        return builder;
    }

    public static LogEntryBuilder withNoParts() {
        return ofParts();
    }

    private final List<LogPart> parts = Lists.newArrayList();

    private LogEntryBuilder with(LogPart part) {
        parts.add(part);
        return this;
    }

    public LogEntry build() {
        return LogEntry.Create(parts);
    }
}
