package com.github.signed.log.core.parser;

import com.github.signed.log.core.Extractor;
import com.github.signed.log.core.Identification;
import com.github.signed.log.core.LogEntry;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.core.StringLogPart;
import com.github.signed.log.extractors.loglevel.LogLevelExtractor;
import com.github.signed.log.extractors.loglocation.LogLocationExtractor;
import com.github.signed.log.extractors.logmessage.LogMessageExtractor;
import com.github.signed.log.extractors.thread.LoggedThreadExtractor;
import com.github.signed.log.extractors.timestamp.TimeStampExtractor;
import com.google.common.collect.Lists;

import java.util.Collection;

import static com.github.signed.log.core.DescriptorBuilder.DescriptorFor;

public class LogEntryParser {

    public static final Identification RawLogIdentification = new Identification("Complete Line");
    public static final Identification TimeStampIdentification = new Identification("timestamp");
    public static final Identification LoggedThreadIdentification = new Identification("thread");
    public static final Identification LogLevelIdentification = new Identification("level");
    public static final Identification LoggerNameIdentification = new Identification("location");
    public static final Identification LogMessageIdentifcation = new Identification("message");


    public static LogEntryParser Create() {
        Collection<Extractor> extractors = Lists.newArrayList();
        return new LogEntryParser(extractors);
    }


    private final Collection<Extractor> extractors;

    public LogEntryParser(Collection<Extractor> extractors) {

        this.extractors = extractors;
    }

    public LogEntry parse(String text) {
        Collection<LogPart> bucket = Lists.newArrayList();
        bucket.add(new StringLogPart(DescriptorFor(RawLogIdentification).thatIsNotDisplayed().canNotBeFilteredBy().build(), text));
        new TimeStampExtractor(DescriptorFor(TimeStampIdentification).thatIsDisplayedAs("timestamp").thatIsNotDisplayed().canNotBeFilteredBy().build()).passLogPartTo(text, bucket);
        new LoggedThreadExtractor(DescriptorFor(LoggedThreadIdentification).thatIsDisplayedAs("thread").isFilterable().build()).passLogPartTo(text, bucket);
        new LogLevelExtractor(DescriptorFor(LogLevelIdentification).thatIsDisplayedAs("level").isFilterable().build()).passLogPartTo(text, bucket);
        new LogLocationExtractor(DescriptorFor(LoggerNameIdentification).thatIsDisplayedAs("class").canNotBeFilteredBy().build()).passLogPartTo(text, bucket);
        new LogMessageExtractor(DescriptorFor(LogMessageIdentifcation).thatIsDisplayedAs("message").canNotBeFilteredBy().build()).passLogPartTo(text, bucket);
        return LogEntry.Create(bucket);
    }
}
