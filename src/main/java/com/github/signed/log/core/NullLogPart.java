package com.github.signed.log.core;

public class NullLogPart implements LogPart {
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

    @Override
    public void describeTo(Authority authority) {
        //do not tell them who you are...
    }

    @Override
    public int compareTo(LogPart o) {
        return -1;
    }
}
