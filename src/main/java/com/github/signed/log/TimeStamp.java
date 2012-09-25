package com.github.signed.log;

import org.joda.time.DateTime;

public class TimeStamp extends LogPart {

    private final DateTime time;

    public TimeStamp(DateTime time){
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