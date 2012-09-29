package com.github.signed.log;

import com.github.signed.log.core.BaseLogPart;

public class RawLogEntry extends BaseLogPart {
    private final String line;

    public RawLogEntry(String line) {
        super("Complete Line");
        this.line = line;
    }

    @Override
    public void dumpInto(StringBuilder builder) {
        builder.append(line);
    }
}
