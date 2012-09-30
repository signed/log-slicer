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

    public LogEntry parse(String text) {
        Collection<LogPart> bucket = Lists.newArrayList();
        bucket.add(new StringLogPart(new Descriptor(LogEntryParser.RawLogIdentification, "Complete Line", false),text));
        new TimeStampExtractor(text).passLogPartTo(bucket);
        new LoggedThreadExtractor(text).passLogPartTo(bucket);
        new LogLevelExtractor(text).passLogPartTo(bucket);
        return LogEntry.Create(bucket);
    }
}
