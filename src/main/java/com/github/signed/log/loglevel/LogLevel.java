package com.github.signed.log.loglevel;

import com.github.signed.log.core.Descriptor;
import com.github.signed.log.core.Identification;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.core.StringLogPart;

public class LogLevel extends StringLogPart {
    private final String text;

    public static LogPart LogLevelOf(String warning) {
        return new LogLevel(warning);
    }

    public LogLevel(String text) {
        super(new Descriptor(new Identification("level"), "level", true));
        this.text = text;
    }

    @Override
    public void dumpInto(StringBuilder builder) {
        builder.append(text);
    }
}
