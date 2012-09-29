package com.github.signed.log.core;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class BaseLogPart implements LogPart{
    private String description;

    public BaseLogPart(String description) {
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

    @Override
    public void dumpInto(StringBuilder builder) {
        builder.append(this.toString());
    }

    @Override
    public void describeTo(Authority authority) {
        authority.accept(new Descriptor(description, visible()));
    }

    protected boolean visible(){
        return true;
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