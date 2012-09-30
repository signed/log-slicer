package com.github.signed.log.core;

public class StringLogPart extends LogPartBase {

    public StringLogPart(Descriptor descriptor) {
        super(descriptor);
    }

    @Override
    public int compareTo(LogPart o) {
        StringBuilder lhs = new StringBuilder();
        this.dumpInto(lhs);
        StringBuilder rhs = new StringBuilder();
        o.dumpInto(rhs);
        return lhs.toString().compareTo(rhs.toString());
    }
}