package com.github.signed.log.thread.ui;

import com.github.signed.log.core.LogEntry;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.ui.list.LogPartProvider;

public class LoggedThreadProvider implements LogPartProvider {
    @Override
    public LogPart from(LogEntry entry) {
        return entry.thread();
    }
}
