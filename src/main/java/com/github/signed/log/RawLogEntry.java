package com.github.signed.log;

import com.github.signed.log.core.Descriptor;
import com.github.signed.log.core.Identification;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.core.StringLogPart;

public class RawLogEntry extends StringLogPart {
    public static LogPart RawLog(String completeLine) {
        return new RawLogEntry(completeLine);
    }

    public static final Identification RawLogIdentification = new Identification("Complete Line");
    private final String line;

    public RawLogEntry(String line) {
        super(new Descriptor(RawLogIdentification, "Complete Line", false));
        this.line = line;
    }

    @Override
    public void dumpInto(StringBuilder builder) {
        builder.append(line);
    }
}
