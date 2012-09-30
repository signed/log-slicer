package com.github.signed.log.thread;

import com.github.signed.log.core.Descriptor;
import com.github.signed.log.core.Extractor;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.core.StringLogPart;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoggedThreadExtractor extends Extractor {
    private final String text;
    private final Descriptor descriptor;

    public LoggedThreadExtractor(Descriptor descriptor, String text) {
        this.text = text;
        this.descriptor = descriptor;
    }

    @Override
    protected LogPart extract() {
        Pattern compile = Pattern.compile("\\(([^\\)]+)\\)");
        Matcher matcher = compile.matcher(text);
        matcher.find();
        return new StringLogPart(descriptor, matcher.group(1));
    }
}