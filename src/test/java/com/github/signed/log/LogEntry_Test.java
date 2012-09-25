package com.github.signed.log;

import com.github.signed.log.core.LogEntry;
import com.github.signed.log.thread.LoggedThread;
import com.github.signed.log.timestamp.TimeStamp;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LogEntry_Test {

    @Test
    public void retrieveTheTimeStampFromTheLog() throws Exception {
        LogEntry entry = LogEntry.createLogEntry("2012-09-18 20:14:58,518 stuff (ThreadName)");
        assertThat(entry.taken(), is(new TimeStamp(new DateTime(2012, 9,18, 20,14, 58, 518))));
    }

    @Test
    public void retrieveThreadInformationFromLogEntry() throws Exception {
        LogEntry entry = LogEntry.createLogEntry("2012-09-18 20:14:58,518 stuff (ThreadName)");
        assertThat(entry.thread(), is(new LoggedThread("ThreadName")));
    }
}
