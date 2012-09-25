package com.github.signed.log.thread;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoggedThreadExtractor {

    private final String text;

    public LoggedThreadExtractor(String text) {
        this.text = text;
    }

    public LoggedThread extract() {
        Pattern compile = Pattern.compile("\\(([^\\)]+)\\)");
        Matcher matcher = compile.matcher(text);
        matcher.find();
        return new LoggedThread(matcher.group(1));
    }
}