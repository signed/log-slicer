package com.github.signed.log;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LogEntry_Test {

    @Test
    public void retrieveTheTimeStampFromTheLog() throws Exception {
        LogEntry entry = new LogEntry("2012-09-18 20:14:58,518 stuff");
        assertThat(entry.taken(), is(new DateTime(2012, 9,18, 20,14, 58, 518)));

    }
}
