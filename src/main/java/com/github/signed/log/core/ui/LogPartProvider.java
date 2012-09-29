package com.github.signed.log.core.ui;

import com.github.signed.log.core.LogEntry;
import com.github.signed.log.core.LogPart;

public interface LogPartProvider {
    LogPart from(LogEntry entry);
}
