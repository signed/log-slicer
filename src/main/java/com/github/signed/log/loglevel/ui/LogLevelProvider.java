package com.github.signed.log.loglevel.ui;

import com.github.signed.log.core.LogEntry;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.core.ui.LogPartProvider;
import com.github.signed.log.loglevel.LogLevel;

public class LogLevelProvider implements LogPartProvider{
    @Override
    public LogPart from(LogEntry entry) {
        return entry.getPart(LogLevel.class);
    }
}
