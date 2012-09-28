package com.github.signed.log.timestamp.ui;

import com.github.signed.log.core.LogEntry;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.list.LogPartProvider;
import com.github.signed.log.timestamp.TimeStamp;

public class TimeStampProvider implements LogPartProvider {
    @Override
    public LogPart from(LogEntry entry) {
        return entry.getPart(TimeStamp.class);
    }
}
