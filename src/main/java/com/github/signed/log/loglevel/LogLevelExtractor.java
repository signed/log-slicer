package com.github.signed.log.loglevel;

import com.github.signed.log.core.Descriptor;
import com.github.signed.log.core.Extractor;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.core.StringLogPart;
import com.github.signed.log.timestamp.SpaceSplitter;

public class LogLevelExtractor extends Extractor {
    private final Descriptor descriptor;
    private final SpaceSplitter splitter;

    public LogLevelExtractor(String text, Descriptor descriptor) {
        splitter = new SpaceSplitter(text);
        this.descriptor = descriptor;
    }

    @Override
    protected LogPart extract() {
        String warn = splitter.at(3);
        return new StringLogPart(descriptor, warn);
    }
}
