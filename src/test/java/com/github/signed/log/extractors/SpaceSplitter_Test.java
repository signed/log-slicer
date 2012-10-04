package com.github.signed.log.extractors;

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

    @Test
    public void joinEverythingAfterTheNTheElement() throws Exception {
        assertThat(spaceSplitter.allAfter(5), is("six"));
    }

    @Test
    public void afterTheLastSplitGroup() throws Exception {
        assertThat(spaceSplitter.allAfter(6), is(""));
    }

    @Test
    public void beyondTheLastSplitGroup() throws Exception {
        assertThat(spaceSplitter.allAfter(34), is(""));
    }

    @Test
    public void joinEverythingAfterTheNTheElementJoinWithSpace() throws Exception {
        assertThat(spaceSplitter.allAfter(4), is("five six"));
    }

    @Test(expected = NoSuchElementException.class)
    public void retrieveElementThatIsNotAvailable() throws Exception {
        spaceSplitter.at(43);
    }
}
