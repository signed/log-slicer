package com.github.signed.log.extractors.logmessage;

import com.github.signed.log.core.Descriptor;
import com.github.signed.log.core.Extractor;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.core.StringLogPart;
import com.github.signed.log.extractors.SpaceSplitter;

public class LogMessageExtractor extends Extractor {
    private final Descriptor descriptor;
    private final SpaceSplitter splitter;

    public LogMessageExtractor(Descriptor descriptor, String rawLogLine) {
        this.descriptor = descriptor;
        splitter = new SpaceSplitter(rawLogLine);
    }

    @Override
    protected LogPart extract(String raw) {
        return new StringLogPart(descriptor, splitter.allAfter(5));
    }
}
