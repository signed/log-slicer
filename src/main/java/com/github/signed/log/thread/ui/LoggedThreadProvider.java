package com.github.signed.log.thread.ui;

import com.github.signed.log.core.LogEntry;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.list.LogPartProvider;
import com.github.signed.log.thread.LoggedThread;

public class LoggedThreadProvider implements LogPartProvider {
    @Override
    public LogPart from(LogEntry entry) {
        return entry.getPart(LoggedThread.class);
    }
}
