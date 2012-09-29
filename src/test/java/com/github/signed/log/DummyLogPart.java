package com.github.signed.log;

import com.github.signed.log.core.BaseLogPart;
import com.github.signed.log.core.Identification;
import com.github.signed.log.core.LogPart;

public class DummyLogPart extends BaseLogPart {

    public static Identification DummyLogPartIdentification = new Identification("Dummy");

    public static LogPart Dummy(String id) {
        return new DummyLogPart(id);
    }
    private final String property;

    public DummyLogPart(String property) {
        super("Dummy");
        this.property = property;
    }

    @Override
    public void dumpInto(StringBuilder builder) {
        builder.append(property);
    }
}
