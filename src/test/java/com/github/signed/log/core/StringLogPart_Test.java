package com.github.signed.log.core;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class StringLogPart_Test {

    @Test
    public void writeThePlainStringToTheBuilder() throws Exception {
        StringBuilder builder = new StringBuilder();
        StringLogPart text = new StringLogPart(null, "text");
        text.dumpInto(builder);
        assertThat(builder.toString(), is("text"));
    }
}
