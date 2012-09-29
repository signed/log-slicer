package com.github.signed.log.thread;

import com.github.signed.log.core.Extractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoggedThreadExtractor extends Extractor {
    private final String text;

    public LoggedThreadExtractor(String text) {
        this.text = text;
    }

    @Override
    protected LoggedThread extract() {
        Pattern compile = Pattern.compile("\\(([^\\)]+)\\)");
        Matcher matcher = compile.matcher(text);
        matcher.find();
        return new LoggedThread(matcher.group(1));
    }
}
