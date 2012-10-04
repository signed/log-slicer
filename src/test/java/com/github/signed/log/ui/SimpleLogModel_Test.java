package com.github.signed.log.ui;

import com.github.signed.log.DummyLogPart;
import com.github.signed.log.core.Authority;
import com.github.signed.log.core.Descriptor;
import com.github.signed.log.core.LogEntry;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.list.SimpleLogModel;
import lang.ArgumentClosure;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.internal.verification.Times;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.github.signed.log.LogEntryBuilder.ofParts;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SimpleLogModel_Test {
    private final SimpleLogModel logModel = new SimpleLogModel();

    @Test
    public void retrieveTheAvailableThreads() throws Exception {
        logDummyLogPart("one");

        assertThat(theDummyLogPartsKnownByTheModel(), containsDummyLogPartWithName("one"));
    }

    @Test
    public void removeDuplicatedThreadsWhileRetrieving() throws Exception {
        logDummyLogPart("one");
        logDummyLogPart("one");
        assertThat(theDummyLogPartsKnownByTheModel(), containsDummyLogPartWithName("one"));
    }

    @Test
    public void retrieveTheAvailableDescriptors() throws Exception {
        logDummyPart();
        Authority authority = mock(Authority.class);
        logModel.describeTo(authority);

        verify(authority).accept(new Descriptor(DummyLogPart.DummyLogPartIdentification, "Dummy", true, true));
    }

    private void logDummyPart() {
        logDummyLogPart("do no care");
    }

    @Test
    public void filterDuplicatedDescriptors() throws Exception {
        logDummyPart();
        logDummyPart();

        Authority authority = mock(Authority.class);
        logModel.describeTo(authority);
        verify(authority, new Times(1)).accept(new Descriptor(DummyLogPart.DummyLogPartIdentification, "Dummy", true, true));
    }

    private void logDummyLogPart(String identifier) {
        LogEntry logEntry = ofParts( new DummyLogPart(identifier)).build();
        logModel.addEntriesFrom(Collections.singletonList(logEntry));
    }

    private Matcher<Iterable<? extends DummyLogPart>> containsDummyLogPartWithName(String... names) {
        List<DummyLogPart> dummy = new ArrayList<>();
        for (String name : names) {
            dummy.add(new DummyLogPart(name));
        }
        return Matchers.contains(dummy.toArray(new DummyLogPart[dummy.size()]));
    }

    @SuppressWarnings("unchecked")
    private List<DummyLogPart> theDummyLogPartsKnownByTheModel() {
        ArgumentClosure<List<LogPart>> closure = mock(ArgumentClosure.class);
        logModel.provideRemainingChoicesTo(DummyLogPart.DummyLogPartIdentification, closure);

        return availableThreads(closure);
    }

    @SuppressWarnings("unchecked")
    private List<DummyLogPart> availableThreads(ArgumentClosure<List<LogPart>> closure) {
        ArgumentCaptor<List> captor = ArgumentCaptor.forClass(List.class);
        verify(closure).execute(captor.capture());
        return captor.getValue();
    }

}
