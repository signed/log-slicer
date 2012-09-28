package com.github.signed.log.timestamp;

import com.github.signed.log.core.LogPart;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Collection;

public class TimeStampExtractor {
    private final static DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss,SSS");
    private final String text;

    public TimeStampExtractor(String text) {
        this.text = text;
    }

    public TimeStamp extract() {
        String date = Iterables.get(spaceSplit(), 0);
        String timeStamp = Iterables.get(spaceSplit(), 1);
        return new TimeStamp(formatter.parseDateTime(date + " " + timeStamp));
    }

    private Iterable<String> spaceSplit() {
        return Splitter.on(" ").split(text);
    }

    public void passExtractedInto(Collection<LogPart> bucket) {
        try {
            bucket.add(extract());
        } catch (Exception e) {
            //nothing to do...
        }
    }
}
