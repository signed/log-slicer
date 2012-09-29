package com.github.signed.log.core.ui;

import com.github.signed.log.core.Identification;
import com.github.signed.log.core.LogEntry;
import com.github.signed.log.core.LogPart;

public class KeyBasedLogPartProvider implements LogPartProvider{
    private final Identification identification;

    public KeyBasedLogPartProvider(Identification identification) {
        this.identification = identification;
    }

    @Override
    public LogPart from(LogEntry entry) {
        return entry.getPart(identification);
    }
}
