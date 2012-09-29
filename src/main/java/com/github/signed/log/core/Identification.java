package com.github.signed.log.core;

import com.google.common.base.Objects;

public class Identification {
    private final String identification;

    public Identification(String identification) {
        this.identification = identification;
    }

    @Override
    public String toString() {
        return identification;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(identification);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Identification) {
            Identification other = (Identification) obj;
            return Objects.equal(this.identification, other.identification);
        }else{
            return false;
        }
    }
}
