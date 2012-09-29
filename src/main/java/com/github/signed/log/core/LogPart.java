package com.github.signed.log.core;

public interface LogPart extends Comparable<LogPart> {
    void dumpInto(StringBuilder builder);

    void describeTo(Authority authority);
}
