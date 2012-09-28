package com.github.signed.log.thread.ui;

import com.github.signed.log.core.LogEntry;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.thread.LoggedThread;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class LoggedThreadProvider_Test {

    @Test
    public void retrieveTheLoggedThreadFromTheLogEntry() throws Exception {
        LogPart actual = new LoggedThreadProvider().from(new LogEntry(Collections.singleton((LogPart) new LoggedThread("eins"))));
        assertThat(actual, is((LogPart)new LoggedThread("eins")));
    }
}
