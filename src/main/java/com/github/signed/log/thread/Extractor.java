package com.github.signed.log.thread;

import com.github.signed.log.core.LogPart;

import java.util.Collection;

public abstract class Extractor {

    public void passLogPartTo(Collection<LogPart> bucket) {
        try {
            bucket.add(extract());
        } catch (Exception e) {
            //nothing to do...
        }
    }

    protected abstract LogPart extract();
}
