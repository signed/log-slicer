package com.github.signed.log.core;

public class NullLogPart extends LogPart{
    public static LogPart TheNullLogPart = new NullLogPart();

    private NullLogPart() {
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
}
