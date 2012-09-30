package com.github.signed.log.thread;

import com.github.signed.log.core.Descriptor;
import com.github.signed.log.core.Extractor;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.core.StringLogPart;
import com.github.signed.log.core.parser.LogEntryParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoggedThreadExtractor extends Extractor {
    private final String text;

    public LoggedThreadExtractor(String text) {
        this.text = text;
    }

    @Override
    protected LogPart extract() {
        Pattern compile = Pattern.compile("\\(([^\\)]+)\\)");
        Matcher matcher = compile.matcher(text);
        matcher.find();
        return new StringLogPart(new Descriptor(LogEntryParser.LoggedThreadIdentification, "thread", true), matcher.group(1));
    }
}