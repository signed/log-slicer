package com.github.signed.log.core;

import java.util.Collection;

public interface LogPart {
    void dumpInto(StringBuilder builder);

    void describeTo(Collection<Descriptor> authority);
}
