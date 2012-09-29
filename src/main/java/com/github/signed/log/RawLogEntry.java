package com.github.signed.log;

import com.github.signed.log.core.BaseLogPart;
import com.github.signed.log.core.Identification;
import com.github.signed.log.core.LogPart;

public class RawLogEntry extends BaseLogPart {
    public static LogPart RawLog(String completeLine) {
        return new RawLogEntry(completeLine);
    }

    public static final Identification RawLogIdentification = new Identification("Complete Line");
    private final String line;

    public RawLogEntry(String line) {
        super("Complete Line");
        this.line = line;
    }

    @Override
    public void dumpInto(StringBuilder builder) {
        builder.append(line);
    }

    @Override
    protected boolean visible() {
        return false;
    }
}
