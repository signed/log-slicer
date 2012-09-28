package com.github.signed.log;

import com.github.signed.log.core.LogPart;

public class RawLogEntry extends LogPart {
    private final String line;

    public RawLogEntry(String line) {
        this.line = line;
    }

    @Override
    public void dumpInto(StringBuilder builder) {
        builder.append(line);
    }
}
