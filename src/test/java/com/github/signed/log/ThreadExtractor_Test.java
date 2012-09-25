package com.github.signed.log;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ThreadExtractor_Test {

    @Test
    public void threadEndsAtTheFirstClosingBracket() throws Exception {
        LoggedThreadExtractor extractor = new LoggedThreadExtractor("stuff (thread name) a message that contains a closing braket)");
        assertThat(extractor.extract(), is(new LoggedThread("thread name")));
    }
}
