package com.github.signed.log.core;

import com.google.common.base.Objects;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Descriptor {
    public final Identification identification;
    public final String name;
    public final boolean display;
    public final boolean filterable;

    public Descriptor(Identification identification, String name, boolean display, boolean filterable) {
        this.identification = identification;
        this.name = name;
        this.display = display;
        this.filterable = filterable;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Descriptor) {
            final Descriptor other = (Descriptor) obj;
            return Objects.equal(this.identification, other.identification);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(identification);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
