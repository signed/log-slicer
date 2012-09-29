package com.github.signed.log;

import com.github.signed.log.core.Authority;
import com.github.signed.log.core.LogEntry;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.thread.LoggedThread;
import lang.ArgumentClosure;
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
        assertThat(LogEntry.createLogEntry("the raw line").getPart(RawLogEntry.RawLogIdentification), is(RawLogEntry.RawLog("the raw line")));
    }

    @Test
    public void retrieveLogPartsByIdentification() throws Exception {
        LogEntry blabla = LogEntryBuilder.ofParts(new DummyLogPart("blabla")).build();
        assertThat(blabla.getPart(DummyLogPart.DummyLogPartIdentification), is(DummyLogPart.Dummy("blabla")));
    }

    @Test
    public void passTheRawStringInformationToTheClosure() throws Exception {
        LogEntry entry = LogEntryBuilder.ofParts(new LoggedThread("uno")).build();
        entry.dumpPartInto(LoggedThread.LoggedThreadIdentification, closure);

        verify(closure).excecute("uno");
    }

    @Test
    public void ifTheRequestedLogPartIsNotAvailablePassAnEmptyStringToTheClosure() throws Exception {
        LogEntry entry = new LogEntry(Collections.<LogPart>emptyList());
        entry.dumpPartInto(LoggedThread.LoggedThreadIdentification, closure);

        verify(closure).excecute("");
    }

    @Test
    public void returnAsPlainLogPart() throws Exception {
        LogEntry entry = ofParts(Dummy("hans")).build();
        LogPart actual = entry.getPart(DummyLogPart.DummyLogPartIdentification);
        assertThat(actual, is(Dummy("hans")));
    }

    @Test
    public void ifNoLogPartOfTheRequestedTypeIsAvailableReturnANullPart() throws Exception {
        LogEntry entry = withNoParts().build();
        LogPart actual = entry.getPart(DummyLogPart.DummyLogPartIdentification);
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