package com.github.signed.log.timestamp;

import com.github.signed.log.core.Descriptor;
import com.github.signed.log.core.Identification;
import com.github.signed.log.core.StringLogPart;
import org.joda.time.DateTime;

public class TimeStamp extends StringLogPart {
    private final DateTime time;

    public TimeStamp(DateTime time){
        super(new Descriptor(new Identification("timestamp"), "timestamp", true));
        this.time = time;
    }

    @Override
    public String toString() {
        return time.toString();
    }

    @Override
    public void dumpInto(StringBuilder builder) {
        builder.append(time.toString());
    }
}