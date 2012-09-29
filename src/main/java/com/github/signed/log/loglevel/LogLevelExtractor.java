package com.github.signed.log.loglevel;

import com.github.signed.log.core.Extractor;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.timestamp.SpaceSplitter;

public class LogLevelExtractor extends Extractor {
    private SpaceSplitter splitter;

    public LogLevelExtractor(String text) {
        splitter = new SpaceSplitter(text);
    }

    @Override
    protected LogPart extract() {
        String warn = splitter.at(3);
        return new LogLevel(warn);
    }
}
