package com.github.signed.log.extractors.loglevel;

import com.github.signed.log.core.Descriptor;
import com.github.signed.log.core.Extractor;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.core.StringLogPart;
import com.github.signed.log.extractors.SpaceSplitter;

public class LogLevelExtractor extends Extractor {
    private final Descriptor descriptor;
    private final SpaceSplitter splitter;

    public LogLevelExtractor(Descriptor descriptor, String text) {
        splitter = new SpaceSplitter(text);
        this.descriptor = descriptor;
    }

    @Override
    protected LogPart extract() {
        String warn = splitter.at(3);
        return new StringLogPart(descriptor, warn);
    }
}
