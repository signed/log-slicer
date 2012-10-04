package com.github.signed.log.extractors.loglevel;

import com.github.signed.log.core.Descriptor;
import com.github.signed.log.core.Extractor;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.core.StringLogPart;
import com.github.signed.log.extractors.SpaceSplitter;

public class LogLevelExtractor extends Extractor {
    private final Descriptor descriptor;

    public LogLevelExtractor(Descriptor descriptor) {
        this.descriptor = descriptor;
    }

    @Override
    protected LogPart extract(String raw) {
        SpaceSplitter splitter = new SpaceSplitter(raw);
        String warn = splitter.at(3);
        return new StringLogPart(descriptor, warn);
    }
}
