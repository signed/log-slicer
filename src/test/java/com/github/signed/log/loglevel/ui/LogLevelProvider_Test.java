package com.github.signed.log.loglevel.ui;

import com.github.signed.log.core.LogEntry;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.loglevel.LogLevel;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class LogLevelProvider_Test {

    @Test
    public void retrieveTheLogLevelPartFromTheLogEntry() throws Exception {
        LogPart actual = new LogLevelProvider().from(new LogEntry(Collections.singleton(LogLevel.LogLevelOf("Warning"))));
        assertThat(actual, is(LogLevel.LogLevelOf("Warning")));
    }
}