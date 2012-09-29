package com.github.signed.log.timestamp;

import com.github.signed.log.core.Extractor;
import com.github.signed.log.core.LogPart;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class TimeStampExtractor extends Extractor{
    private final static DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss,SSS");
    private final SpaceSplitter splitter;

    public TimeStampExtractor(String text) {
        splitter = new SpaceSplitter(text);
    }

    @Override
    protected LogPart extract() {
        String date = splitter.first();
        String timeStamp = splitter.second();
        return new TimeStamp(formatter.parseDateTime(date + " " + timeStamp));
    }

}
