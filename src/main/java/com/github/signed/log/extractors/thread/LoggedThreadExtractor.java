package com.github.signed.log.extractors.thread;

import com.github.signed.log.core.Descriptor;
import com.github.signed.log.core.Extractor;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.core.StringLogPart;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoggedThreadExtractor extends Extractor {
    private final Descriptor descriptor;

    public LoggedThreadExtractor(Descriptor descriptor) {
        this.descriptor = descriptor;
    }

    @Override
    protected LogPart extract(String raw) {
        Pattern compile = Pattern.compile("\\(([^\\)]+)\\)");
        Matcher matcher = compile.matcher(raw);
        matcher.find();
        return new StringLogPart(descriptor, matcher.group(1));
    }
}