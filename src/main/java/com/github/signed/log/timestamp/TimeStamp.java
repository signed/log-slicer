package com.github.signed.log.timestamp;

import com.github.signed.log.core.DateTimeLogPart;
import com.github.signed.log.core.Descriptor;
import com.github.signed.log.core.Identification;
import org.joda.time.DateTime;

public class TimeStamp extends DateTimeLogPart {
    public TimeStamp(DateTime time){
        super(new Descriptor(new Identification("timestamp"), "timestamp", true), time);
    }
}