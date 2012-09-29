package com.github.signed.log.core;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.Collection;

public class LogPart {
    private String description;

    public LogPart(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public void dumpInto(StringBuilder builder) {
        builder.append(this.toString());
    }

    public void describeTo(Collection<Descriptor> authority) {
        authority.add(new Descriptor(description, this.getClass()));
    }

}
