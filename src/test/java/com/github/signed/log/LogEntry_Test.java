package com.github.signed.log;

import com.github.signed.log.core.LogEntry;
import com.github.signed.log.thread.LoggedThread;
import com.github.signed.log.timestamp.TimeStamp;
import com.google.common.collect.ImmutableList;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LogEntry_Test {


    @Test
    public void retrievesRawLineLine() throws Exception {
        assertThat(LogEntry.createLogEntry("the raw line").getPart(RawLogEntry.class), is(new RawLogEntry("the raw line")));
    }

    @Test
    public void retrieveLogPartsByClassName() throws Exception {
        DateTime time = new DateTime();

        final TimeStamp timestamp = new TimeStamp(time);
        final LoggedThread thread = new LoggedThread("");
        LogEntry entry = new LogEntry("", ImmutableList.of(timestamp, thread));
        assertThat(entry.getPart(TimeStamp.class), is(new TimeStamp(time)));
    }

    @Test
    public void retrieveDifferentLogPartsByClassName() throws Exception {
        final TimeStamp timestamp = new TimeStamp(null);
        final LoggedThread name = new LoggedThread("name");
        LogEntry entry = new LogEntry("", ImmutableList.of(timestamp, name));
        assertThat(entry.getPart(LoggedThread.class), is(new LoggedThread("name")));
    }
}
