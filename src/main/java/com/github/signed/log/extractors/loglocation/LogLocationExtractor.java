package com.github.signed.log.extractors.loglocation;

import com.github.signed.log.core.Descriptor;
import com.github.signed.log.core.Extractor;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.core.StringLogPart;
import com.github.signed.log.extractors.SpaceSplitter;

public class LogLocationExtractor extends Extractor{

    private final Descriptor descriptor;

    public LogLocationExtractor(Descriptor descriptor) {
        this.descriptor = descriptor;
    }

    @Override
    protected LogPart extract(String raw) {
        SpaceSplitter spaceSplitter =  new SpaceSplitter(raw);
        String rawName = spaceSplitter.at(4);
        return new StringLogPart(descriptor, rawName.substring(1, rawName.length() - 1));
    }
}
