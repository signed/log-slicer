package com.github.signed.log;

import com.github.signed.log.core.Descriptor;
import com.github.signed.log.core.Identification;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.core.StringLogPart;

public class DummyLogPart extends StringLogPart {

    public static Identification DummyLogPartIdentification = new Identification("Dummy");

    public static LogPart Dummy(String id) {
        return new DummyLogPart(id);
    }
    private final String property;

    public DummyLogPart(String property) {
        super(new Descriptor(DummyLogPartIdentification, "Dummy", false));
        this.property = property;
    }

    @Override
    public void dumpInto(StringBuilder builder) {
        builder.append(property);
    }
}
