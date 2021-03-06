package com.github.signed.log.core.ui;

import com.github.signed.log.DummyLogPart;
import com.github.signed.log.core.LogEntry;
import com.github.signed.log.core.LogPart;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class KeyBasedLogPartProvider_Test {

    @Test
    public void retrieveTheLogPartWithTheGivenKey() throws Exception {
        LogEntry logEntry = LogEntry.Create(Collections.singleton(DummyLogPart.Dummy("you are")));
        LogPart actual = new KeyBasedLogPartProvider(DummyLogPart.DummyLogPartIdentification).from(logEntry);
        assertThat(actual, is(DummyLogPart.Dummy("you are")));
    }
}
