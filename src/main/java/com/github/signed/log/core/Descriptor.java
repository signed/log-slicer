package com.github.signed.log.core;

import com.google.common.base.Objects;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Descriptor {
    public final Identification identification;
    public final String name;
    public final Class<? extends LogPart> type;
    public final boolean display;

    public Descriptor(String name, Class<? extends LogPart> type, boolean display) {
        this.identification = new Identification(name);
        this.name = name;
        this.type = type;
        this.display = display;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Descriptor) {
            final Descriptor other = (Descriptor) obj;
            return Objects.equal(this.name, other.name)
                && Objects.equal(this.type, other.type);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, type);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
