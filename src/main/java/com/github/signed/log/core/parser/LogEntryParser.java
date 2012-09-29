package com.github.signed.log.core.parser;

import com.github.signed.log.RawLogEntry;
import com.github.signed.log.core.LogEntry;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.loglevel.LogLevelExtractor;
import com.github.signed.log.thread.LoggedThreadExtractor;
import com.github.signed.log.timestamp.TimeStampExtractor;
import com.google.common.collect.Lists;

import java.util.Collection;

public class LogEntryParser {

    public LogEntry parse(String text) {
        Collection<LogPart> bucket = Lists.newArrayList();
        bucket.add(new RawLogEntry(text));
        new TimeStampExtractor(text).passLogPartTo(bucket);
        new LoggedThreadExtractor(text).passLogPartTo(bucket);
        new LogLevelExtractor(text).passLogPartTo(bucket);
        return LogEntry.Create(bucket);
    }
}
