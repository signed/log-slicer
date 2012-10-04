package com.github.signed.log;

import com.github.signed.log.core.Descriptor;
import com.github.signed.log.core.Extractor;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.core.StringLogPart;
import com.github.signed.log.timestamp.SpaceSplitter;

public class LoggerNameExtractor extends Extractor{

    private final Descriptor descriptor;
    private final SpaceSplitter spaceSplitter;

    public LoggerNameExtractor(Descriptor descriptor, String rawLogLine) {
        spaceSplitter = new SpaceSplitter(rawLogLine);
        this.descriptor = descriptor;
    }

    @Override
    protected LogPart extract() {
        String rawName = spaceSplitter.at(4);
        return new StringLogPart(descriptor, rawName.substring(1, rawName.length() - 1));
    }
}
