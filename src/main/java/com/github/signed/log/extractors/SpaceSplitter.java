package com.github.signed.log.extractors;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

import java.util.NoSuchElementException;

public class SpaceSplitter {
    private final String text;

    public SpaceSplitter(String text) {
        this.text = text;
    }

    private Iterable<String> spaceSplit() {
        return Splitter.on(" ").omitEmptyStrings().split(text);
    }

    public String first() {
        return elementAtPosition(1);
    }

    public String second() {
        return elementAtPosition(2);
    }

    public String at(int oneBasedIndex) {
        return elementAtPosition(oneBasedIndex);
    }

    private String elementAtPosition(int index) {
        try {
            return Iterables.get(spaceSplit(), index -1);
        } catch (Exception e) {
            throw new NoSuchElementException("no " + index + "`th element");
        }
    }
}
