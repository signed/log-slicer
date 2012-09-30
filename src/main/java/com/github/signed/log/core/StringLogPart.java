package com.github.signed.log.core;

public class StringLogPart extends LogPartBase {
    private final String text;

    public StringLogPart(Descriptor descriptor, String text) {
        super(descriptor);
        this.text = text;
    }

    @Override
    public void dumpInto(StringBuilder builder) {
        builder.append(text);
    }

    @Override
    public String toString() {
        return text;
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