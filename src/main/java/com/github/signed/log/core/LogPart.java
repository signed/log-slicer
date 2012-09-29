package com.github.signed.log.core;

public interface LogPart {
    void dumpInto(StringBuilder builder);

    void describeTo(Authority authority);
}
