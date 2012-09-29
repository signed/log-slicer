package com.github.signed.log.core.ui;

import com.github.signed.log.core.LogEntry;
import com.github.signed.log.core.LogPart;

public class KeyBasedLogPartProvider implements LogPartProvider{
    private final Class<? extends LogPart> key;

    public KeyBasedLogPartProvider(Class<? extends LogPart> key) {
        this.key = key;
    }

    @Override
    public LogPart from(LogEntry entry) {
        return entry.getPart(key);
    }
}
