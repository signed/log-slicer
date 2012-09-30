package com.github.signed.log.core;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public abstract class LogPartBase implements LogPart {
    protected final Descriptor descriptor;

    public LogPartBase(Descriptor descriptor) {
        this.descriptor = descriptor;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public void dumpInto(StringBuilder builder) {
        builder.append(this.toString());
    }

    @Override
    public void describeTo(Authority authority) {
        authority.accept(descriptor);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
