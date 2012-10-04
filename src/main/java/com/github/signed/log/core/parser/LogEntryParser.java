package com.github.signed.log.core.parser;

import com.github.signed.log.core.Identification;
import com.github.signed.log.core.LogEntry;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.core.StringLogPart;
import com.github.signed.log.loglevel.LogLevelExtractor;
import com.github.signed.log.loglocation.LogLocationExtractor;
import com.github.signed.log.thread.LoggedThreadExtractor;
import com.github.signed.log.timestamp.TimeStampExtractor;
import com.google.common.collect.Lists;

import java.util.Collection;

import static com.github.signed.log.core.DescriptorBuilder.DescriptorFor;

public class LogEntryParser {

    public static final Identification RawLogIdentification = new Identification("Complete Line");
    public static final Identification TimeStampIdentification = new Identification("timestamp");
    public static final Identification LoggedThreadIdentification = new Identification("thread");
    public static final Identification LogLevelIdentification = new Identification("level");
    public static final Identification LoggerNameIdentification = new Identification("logger name");

    public LogEntry parse(String text) {
        Collection<LogPart> bucket = Lists.newArrayList();
        bucket.add(new StringLogPart(DescriptorFor(RawLogIdentification).thatIsNotDisplayed().canNotBeFilteredBy().build(), text));
        new TimeStampExtractor(DescriptorFor(TimeStampIdentification).thatIsDisplayedAs("timestamp").canNotBeFilteredBy().build(), text).passLogPartTo(bucket);
        new LoggedThreadExtractor(DescriptorFor(LoggedThreadIdentification).thatIsDisplayedAs("thread").isFilterable().build(), text).passLogPartTo(bucket);
        new LogLevelExtractor(DescriptorFor(LogLevelIdentification).thatIsDisplayedAs("level").isFilterable().build(), text).passLogPartTo(bucket);
        new LogLocationExtractor(DescriptorFor(LoggerNameIdentification).thatIsDisplayedAs("class").isFilterable().build(), text).passLogPartTo(bucket);

        return LogEntry.Create(bucket);
    }
}
