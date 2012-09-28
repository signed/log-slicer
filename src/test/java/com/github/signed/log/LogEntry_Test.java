package com.github.signed.log;

import com.github.signed.log.core.LogEntry;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.thread.LoggedThread;
import com.github.signed.log.timestamp.TimeStamp;
import com.google.common.collect.ImmutableList;
import lang.ArgumentClosure;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Collections;

import static com.github.signed.log.DummyLogPart.Dummy;
import static com.github.signed.log.core.NullLogPart.TheNullLogPart;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LogEntry_Test {
    @SuppressWarnings("unchecked")
    private final ArgumentClosure<String> closure = mock(ArgumentClosure.class);

    @Test
    public void retrievesRawLineLine() throws Exception {
        assertThat(LogEntry.createLogEntry("the raw line").getDerivedPart(RawLogEntry.class), is(new RawLogEntry("the raw line")));
    }

    @Test
    public void retrieveLogPartsByClassName() throws Exception {
        DateTime time = new DateTime();

        final TimeStamp timestamp = new TimeStamp(time);
        final LoggedThread thread = new LoggedThread("");
        LogEntry entry = new LogEntry(ImmutableList.of(timestamp, thread));
        assertThat(entry.getDerivedPart(TimeStamp.class), is(new TimeStamp(time)));
    }

    @Test
    public void retrieveDifferentLogPartsByClassName() throws Exception {
        final TimeStamp timestamp = new TimeStamp(null);
        final LoggedThread name = new LoggedThread("name");
        LogEntry entry = new LogEntry(ImmutableList.of(timestamp, name));
        assertThat(entry.getDerivedPart(LoggedThread.class), is(new LoggedThread("name")));
    }

    @Test
    public void passTheRawStringInformationToTheClosure() throws Exception {
        LogEntry entry = new LogEntry(Collections.singleton((LogPart) new LoggedThread("uno")));
        entry.dumpPartInto(LoggedThread.class, closure);

        verify(closure).excecute("uno");
    }

    @Test
    public void ifTheRequestedLogPartIsNotAvailablePassAnEmptyStringToTheClosure() throws Exception {
        LogEntry entry = new LogEntry(Collections.<LogPart>emptyList());
        entry.dumpPartInto(LoggedThread.class, closure);

        verify(closure).excecute("");
    }

    @Test
    public void returnAsPlainLogPart() throws Exception {
        LogEntry entry = new LogEntry(Collections.singleton(Dummy("hans")));
        LogPart actual = entry.getPart(DummyLogPart.class);
        assertThat(actual, is(Dummy("hans")));
    }

    @Test
    public void ifNoLogPartOfTheRequestedTypeIsAvailableReturnANullPart() throws Exception {
        LogEntry entry = new LogEntry(Collections.<LogPart>emptyList());
        LogPart actual = entry.getPart(DummyLogPart.class);
        assertThat(actual, is(sameInstance(TheNullLogPart)));
    }



}
