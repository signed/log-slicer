package com.github.signed.log;

import com.github.signed.log.core.Authority;
import com.github.signed.log.core.LogEntry;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.thread.LoggedThread;
import com.github.signed.log.timestamp.TimeStamp;
import lang.ArgumentClosure;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Collections;

import static com.github.signed.log.DummyLogPart.Dummy;
import static com.github.signed.log.LogEntryBuilder.ofParts;
import static com.github.signed.log.LogEntryBuilder.withNoParts;
import static com.github.signed.log.core.NullLogPart.TheNullLogPart;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LogEntry_Test {
    @SuppressWarnings("unchecked")
    private final ArgumentClosure<String> closure = mock(ArgumentClosure.class);
    @SuppressWarnings("unchecked")
    private final Authority authority = mock(Authority.class);

    @Test
    public void retrievesRawLineLine() throws Exception {
        assertThat(LogEntry.createLogEntry("the raw line").getDerivedPart(RawLogEntry.class), is(new RawLogEntry("the raw line")));
    }

    @Test
    public void retrieveLogPartsByClassName() throws Exception {
        DateTime time = new DateTime();
        LogEntry entry = LogEntryBuilder.ofParts(new TimeStamp(time), new LoggedThread("")).build();
        assertThat(entry.getDerivedPart(TimeStamp.class), is(new TimeStamp(time)));
    }

    @Test
    public void retrieveDifferentLogPartsByClassName() throws Exception {
        LogEntry entry = LogEntryBuilder.ofParts(new TimeStamp(null), new LoggedThread("name")).build();
        assertThat(entry.getDerivedPart(LoggedThread.class), is(new LoggedThread("name")));
    }

    @Test
    public void passTheRawStringInformationToTheClosure() throws Exception {
        LogEntry entry = LogEntryBuilder.ofParts(new LoggedThread("uno")).build();
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
        LogEntry entry = ofParts(Dummy("hans")).build();
        LogPart actual = entry.getPart(DummyLogPart.class);
        assertThat(actual, is(Dummy("hans")));
    }

    @Test
    public void ifNoLogPartOfTheRequestedTypeIsAvailableReturnANullPart() throws Exception {
        LogEntry entry = withNoParts().build();
        LogPart actual = entry.getPart(DummyLogPart.class);
        assertThat(actual, is(sameInstance(TheNullLogPart)));
    }


    public static interface PartOne extends LogPart{

    }

    @Test
    public void collectTheDescriptorsFromAllLogParts() throws Exception {


        LogPart one = mock(PartOne.class);
        LogPart two = mock(LogPart.class);

        LogEntry entry = LogEntryBuilder.ofParts(one, two).build();
        entry.describeTo(authority);

        verify(one).describeTo(authority);
        verify(two).describeTo(authority);
    }
}