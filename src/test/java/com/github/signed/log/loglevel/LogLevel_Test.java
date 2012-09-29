package com.github.signed.log.loglevel;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LogLevel_Test {

    @Test
    public void writeThePlainStringToTheBuilder() throws Exception {
        StringBuilder builder = new StringBuilder();
        new LogLevel("TRACE").dumpInto(builder);
        assertThat(builder.toString(), is("TRACE"));
    }
}
