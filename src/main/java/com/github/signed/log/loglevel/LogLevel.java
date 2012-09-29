package com.github.signed.log.loglevel;

import com.github.signed.log.core.LogPart;

public class LogLevel extends LogPart {
    private final String text;

    public static LogPart LogLevelOf(String warning) {
        return new LogLevel(warning);
    }

    public LogLevel(String text) {
        super("level");
        this.text = text;
    }

    @Override
    public void dumpInto(StringBuilder builder) {
        builder.append(text);
    }
}
