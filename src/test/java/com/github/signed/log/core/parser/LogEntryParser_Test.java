package com.github.signed.log.core.parser;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LogEntryParser_Test {

    @Test
    public void retrievesRawLineLine() throws Exception {
        StringBuilder parsed = new StringBuilder();
        new LogEntryParser().parse("the raw line").getPart(LogEntryParser.RawLogIdentification).dumpInto(parsed);

        assertThat(parsed.toString(),is("the raw line"));
    }
}
