package com.github.signed.log.core;

import java.util.Collection;

public abstract class Extractor {

    public void passLogPartTo(String raw, Collection<LogPart> bucket) {
        try {
            bucket.add(extract(raw));
        } catch (Exception e) {
            //nothing to do...
        }
    }

    protected abstract LogPart extract(String raw);
}
