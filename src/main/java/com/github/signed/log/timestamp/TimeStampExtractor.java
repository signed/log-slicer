package com.github.signed.log.timestamp;

import com.github.signed.log.core.DateTimeLogPart;
import com.github.signed.log.core.Descriptor;
import com.github.signed.log.core.Extractor;
import com.github.signed.log.core.LogPart;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class TimeStampExtractor extends Extractor {
    private final static DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss,SSS");
    private final SpaceSplitter splitter;
    private final Descriptor descriptor;

    public TimeStampExtractor(Descriptor descriptor, String text) {
        this.splitter = new SpaceSplitter(text);
        this.descriptor = descriptor;
    }

    @Override
    protected LogPart extract() {
        String date = splitter.first();
        String timeStamp = splitter.second();
        DateTime dateTime = formatter.parseDateTime(date + " " + timeStamp);
        return new DateTimeLogPart(descriptor, dateTime);
    }
}
