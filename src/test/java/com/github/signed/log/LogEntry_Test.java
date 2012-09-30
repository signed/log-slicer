package com.github.signed.log;

import com.github.signed.log.core.Authority;
import com.github.signed.log.core.Identification;
import com.github.signed.log.core.LogEntry;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.core.StringLogPart;
import com.github.signed.log.core.parser.LogEntryParser;
import lang.ArgumentClosure;
import org.junit.Test;

import java.util.Collections;

import static com.github.signed.log.DescriptorBuilder.anyDescriptor;
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
    public void retrieveLogPartsByIdentification() throws Exception {
        LogEntry blabla = LogEntryBuilder.ofParts(new DummyLogPart("blabla")).build();
        assertThat(blabla.getPart(DummyLogPart.DummyLogPartIdentification), is(DummyLogPart.Dummy("blabla")));
    }


    @Test
    public void passTheRawStringInformationToTheClosure() throws Exception {
        Identification identification = new Identification("test-me");
        StringLogPart part = new StringLogPart(anyDescriptor().withIdentification(identification).build(), "uno");
        LogEntry entry = LogEntryBuilder.ofParts(part).build();
        entry.dumpPartInto(identification, closure);

        verify(closure).excecute("uno");
    }

    @Test
    public void ifTheRequestedLogPartIsNotAvailablePassAnEmptyStringToTheClosure() throws Exception {
        LogEntry entry = LogEntry.Create(Collections.<LogPart>emptyList());
        entry.dumpPartInto(LogEntryParser.LoggedThreadIdentification, closure);

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

    @Test
    public void collectTheDescriptorsFromAllLogParts() throws Exception {
        LogPart one = mock(LogPart.class);
        LogPart two = mock(LogPart.class);

        LogEntry entry = new LogEntry();
        entry.addPart(new Identification("any"), one);
        entry.addPart(new Identification("other"), two);

        entry.describeTo(authority);

        verify(one).describeTo(authority);
        verify(two).describeTo(authority);
    }
}