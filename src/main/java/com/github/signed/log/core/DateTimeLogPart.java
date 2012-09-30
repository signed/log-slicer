package com.github.signed.log.core;

import org.joda.time.DateTime;

public class DateTimeLogPart extends LogPartBase{

    private final DateTime dateTime;

    public DateTimeLogPart(Descriptor descriptor, DateTime dateTime) {
        super(descriptor);
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return dateTime.toString();
    }

    @Override
    public void dumpInto(StringBuilder builder) {
        builder.append(dateTime.toString());
    }


    @Override
    public int compareTo(LogPart o) {
        if (o instanceof DateTimeLogPart) {
            DateTimeLogPart other = (DateTimeLogPart) o;
            return this.dateTime.compareTo(other.dateTime);
        }else {
            return -1;
        }
    }
}
