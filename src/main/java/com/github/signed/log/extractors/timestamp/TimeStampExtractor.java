package com.github.signed.log.extractors.timestamp;

import com.github.signed.log.core.DateTimeLogPart;
import com.github.signed.log.core.Descriptor;
import com.github.signed.log.core.Extractor;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.extractors.SpaceSplitter;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class TimeStampExtractor extends Extractor {
    private final static DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss,SSS");
    private final Descriptor descriptor;

    public TimeStampExtractor(Descriptor descriptor) {
        this.descriptor = descriptor;
    }

    @Override
    protected LogPart extract(String raw) {
        SpaceSplitter splitter = new SpaceSplitter(raw);
        String date = splitter.first();
        String timeStamp = splitter.second();
        DateTime dateTime = formatter.parseDateTime(date + " " + timeStamp);
        return new DateTimeLogPart(descriptor, dateTime);
    }
}
