package com.github.signed.log;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class LogEntry {
    private final DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss,SSS");
    public final String text;

    public LogEntry(String text) {
        this.text = text;
    }

    public DateTime taken() {
        String date = Iterables.get(spaceSplit(), 0);
        String timeStamp = Iterables.get(spaceSplit(), 1);


        return formatter.parseDateTime(date+" "+timeStamp);
    }

    private Iterable<String> spaceSplit() {
        return Splitter.on(" ").split(text);
    }
}
