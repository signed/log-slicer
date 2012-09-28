package com.github.signed.log;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RawLogEntry_Test {
    @Test
    public void testName() throws Exception {
        RawLogEntry entry = new RawLogEntry("more or less useful information");
        StringBuilder stringBuilder = new StringBuilder();
        entry.dumpInto(stringBuilder);

        assertThat(stringBuilder.toString(), is("more or less useful information"));
    }
}
