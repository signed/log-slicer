package com.github.signed.log.core;

import com.google.common.base.Functions;
import com.google.common.collect.Maps;
import lang.ArgumentClosure;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class LogEntry {
    public static final LogEntry Null = Create(Collections.<LogPart>emptyList());


    public static LogEntry Create(Collection<LogPart> availableParts) {
        final LogEntry logEntry = new LogEntry();
        for (final LogPart availablePart : availableParts) {
            availablePart.describeTo(new Authority() {
                @Override
                public void accept(Descriptor descriptor) {
                    logEntry.addPart(descriptor.identification, availablePart);
                }
            });
        }
        return logEntry;
    }

    public void addPart(Identification identification, LogPart availablePart) {
        this.parts.put(identification, availablePart);
    }

    private final Map<Identification, LogPart> parts = Maps.newHashMap();

    public LogPart getPart(Identification identification) {
        return Functions.forMap(parts, NullLogPart.TheNullLogPart).apply(identification);
    }

    public void dumpPartInto(Identification identification, ArgumentClosure<String> closure) {
        LogPart part = getPart(identification);
        StringBuilder builder = new StringBuilder();
        if (null != part) {
            part.dumpInto(builder);
            closure.execute(builder.toString());
        }
    }

    public void describeTo(Authority authority) {
        for (LogPart logPart : parts.values()) {
            logPart.describeTo(authority);
        }
    }
}