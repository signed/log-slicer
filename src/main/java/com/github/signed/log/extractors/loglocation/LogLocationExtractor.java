package com.github.signed.log.extractors.loglocation;

import com.github.signed.log.core.Descriptor;
import com.github.signed.log.core.Extractor;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.core.StringLogPart;
import com.github.signed.log.extractors.SpaceSplitter;

public class LogLocationExtractor extends Extractor{

    private final Descriptor descriptor;
    private final SpaceSplitter spaceSplitter;

    public LogLocationExtractor(Descriptor descriptor, String rawLogLine) {
        spaceSplitter = new SpaceSplitter(rawLogLine);
        this.descriptor = descriptor;
    }

    @Override
    protected LogPart extract() {
        String rawName = spaceSplitter.at(4);
        return new StringLogPart(descriptor, rawName.substring(1, rawName.length() - 1));
    }
}
