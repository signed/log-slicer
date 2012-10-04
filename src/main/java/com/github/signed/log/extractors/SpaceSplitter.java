package com.github.signed.log.extractors;

import com.google.common.base.Joiner;
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

    public String allAfter(int oneBasedIndex) {
        Iterable<String> skip = Iterables.skip(spaceSplit(), oneBasedIndex );
        return Joiner.on(" ").join(skip);
    }

    private String elementAtPosition(int index) {
        try {
            Iterable<String> iterable = spaceSplit();
            return Iterables.get(iterable, index - 1);
        } catch (Exception e) {
            throw new NoSuchElementException("no " + index + "`th element");
        }
    }

}
