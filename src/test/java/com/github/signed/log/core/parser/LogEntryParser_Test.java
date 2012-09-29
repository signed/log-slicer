package com.github.signed.log.core.parser;

import com.github.signed.log.RawLogEntry;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LogEntryParser_Test {

    @Test
    public void retrievesRawLineLine() throws Exception {
        assertThat(new LogEntryParser().parse("the raw line").getPart(RawLogEntry.RawLogIdentification), is(RawLogEntry.RawLog("the raw line")));
    }
}
