package com.github.signed.log.core;

import java.util.Collection;

public class NullLogPart extends LogPart{
    public static LogPart TheNullLogPart = new NullLogPart();

    private NullLogPart() {
        super("I will not describe myself");
        //keep to myself
    }

    @Override
    public String toString() {
        return "NullLogPart";
    }

    @Override
    public void dumpInto(StringBuilder builder) {
        //do nothing
    }

    @Override
    public void describeTo(Collection<Descriptor> authority) {
        //do not tell them who you are...
    }
}
