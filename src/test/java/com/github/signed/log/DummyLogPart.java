package com.github.signed.log;

import com.github.signed.log.core.BaseLogPart;
import com.github.signed.log.core.LogPart;

public class DummyLogPart extends BaseLogPart {

    public static LogPart Dummy(String id) {
        return new DummyLogPart(id);
    }
    private final String id;

    public DummyLogPart(String id) {
        super("Dummy");
        this.id = id;
    }

    @Override
    public void dumpInto(StringBuilder builder) {
        builder.append(id);
    }
}
