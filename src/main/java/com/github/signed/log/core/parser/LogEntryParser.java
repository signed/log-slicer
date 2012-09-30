package com.github.signed.log.core.parser;

import com.github.signed.log.core.Descriptor;
import com.github.signed.log.core.Identification;
import com.github.signed.log.core.LogEntry;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.core.StringLogPart;
import com.github.signed.log.loglevel.LogLevelExtractor;
import com.github.signed.log.thread.LoggedThreadExtractor;
import com.github.signed.log.timestamp.TimeStampExtractor;
import com.google.common.collect.Lists;

import java.util.Collection;

public class LogEntryParser {

    public static final Identification RawLogIdentification = new Identification("Complete Line");
    public static final Identification TimeStampIdentification = new Identification("timestamp");
    public static final Identification LoggedThreadIdentification = new Identification("thread");
    public static final Identification LogLevelIdentification = new Identification("level");

    public LogEntry parse(String text) {
        Collection<LogPart> bucket = Lists.newArrayList();
        bucket.add(new StringLogPart(new Descriptor(LogEntryParser.RawLogIdentification, "Complete Line", false),text));
        new TimeStampExtractor(text, new Descriptor(TimeStampIdentification, "timestamp", true)).passLogPartTo(bucket);
        new LoggedThreadExtractor(text, new Descriptor(LoggedThreadIdentification, "thread", true)).passLogPartTo(bucket);
        new LogLevelExtractor(text, new Descriptor(LogLevelIdentification, "level", true)).passLogPartTo(bucket);
        return LogEntry.Create(bucket);
    }
}
