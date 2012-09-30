package com.github.signed.log;

import com.github.signed.log.core.Descriptor;
import com.github.signed.log.core.Identification;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.core.LogPartBase;

public class DummyLogPart extends LogPartBase {

    public static Identification DummyLogPartIdentification = new Identification("Dummy");
    public final String property;

    public static LogPart Dummy(String id) {
        return new DummyLogPart(id);
    }

    public DummyLogPart(String property) {
        super(new Descriptor(DummyLogPartIdentification, "Dummy", false));
        this.property = property;
    }

    @Override
    public int compareTo(LogPart o) {
        throw new UnsupportedOperationException();
    }
}
