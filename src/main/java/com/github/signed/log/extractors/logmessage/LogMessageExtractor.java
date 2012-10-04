package com.github.signed.log.extractors.logmessage;

import com.github.signed.log.core.Descriptor;
import com.github.signed.log.core.Extractor;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.core.StringLogPart;
import com.github.signed.log.extractors.SpaceSplitter;

public class LogMessageExtractor extends Extractor {
    private final Descriptor descriptor;

    public LogMessageExtractor(Descriptor descriptor) {
        this.descriptor = descriptor;

    }

    @Override
    protected LogPart extract(String raw) {
        SpaceSplitter splitter = new SpaceSplitter(raw);
        return new StringLogPart(descriptor, splitter.allAfter(5));
    }
}
