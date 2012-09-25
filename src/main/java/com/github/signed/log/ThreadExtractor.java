package com.github.signed.log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThreadExtractor {

    private final String text;

    public ThreadExtractor(String text) {
        this.text = text;
    }

    public LoggedThread extract() {
        Pattern compile = Pattern.compile("\\(([^\\)]+)\\)");
        Matcher matcher = compile.matcher(text);
        matcher.find();
        return new LoggedThread(matcher.group(1));
    }
}
