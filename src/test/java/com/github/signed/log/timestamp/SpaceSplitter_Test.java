package com.github.signed.log.timestamp;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SpaceSplitter_Test {
    private final SpaceSplitter spaceSplitter = new SpaceSplitter("one two three four five  six");

    @Test
    public void retrieveTheFirstElementOfTheSplit() throws Exception {
        assertThat(spaceSplitter.first(), is("one"));
    }

    @Test
    public void retrieveTheSecondElementOfTheSplit() throws Exception {
        assertThat(spaceSplitter.second(), is("two"));
    }

    @Test
    public void retrieveNthElement() throws Exception {
        assertThat(spaceSplitter.at(5), is("five"));
    }

    @Test
    public void omitEmptyStrings() throws Exception {
        assertThat(spaceSplitter.at(6), is("six"));
    }

    @Test(expected = NoSuchElementException.class)
    public void retrieveElementThatIsNotAvailable() throws Exception {
        spaceSplitter.at(43);
    }
}
