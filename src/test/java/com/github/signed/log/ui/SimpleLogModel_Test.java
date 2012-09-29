package com.github.signed.log.ui;

import com.github.signed.log.DummyLogPart;
import com.github.signed.log.core.Authority;
import com.github.signed.log.core.Descriptor;
import com.github.signed.log.core.LogEntry;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.list.SimpleLogModel;
import com.github.signed.log.thread.LoggedThread;
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
        logEntryOnThread("one");

        assertThat(theThreadsKnownByTheModel(), containsThreadsWithName("one"));
    }

    @Test
    public void removeDuplicatedThreadsWhileRetrieving() throws Exception {
        logEntryOnThread("one");
        logEntryOnThread("one");
        assertThat(theThreadsKnownByTheModel(), containsThreadsWithName("one"));
    }

    @Test
    public void retrieveTheAvailableDescriptors() throws Exception {
        logDummyLodPart();
        Authority authority = mock(Authority.class);
        logModel.describeTo(authority);

        verify(authority).accept(new Descriptor(DummyLogPart.DummyLogPartIdentification, "Dummy", true));
    }


    @Test
    public void filterDuplicatedDescriptors() throws Exception {
        logDummyLodPart();
        logDummyLodPart();

        Authority authority = mock(Authority.class);
        logModel.describeTo(authority);
        verify(authority, new Times(1)).accept(new Descriptor(DummyLogPart.DummyLogPartIdentification, "Dummy", true));
    }

    private void logDummyLodPart() {
        LogEntry logEntry = ofParts( new DummyLogPart("do no care")).build();
        logModel.addEntriesFrom(Collections.singletonList(logEntry));
    }

    private void logOnThread(LoggedThread thread) {
        LogEntry logEntry = ofParts(thread).build();
        logModel.addEntriesFrom(Collections.singletonList(logEntry));
    }

    private Matcher<Iterable<? extends LoggedThread>> containsThreadsWithName(String... names) {
        List<LoggedThread> threads = new ArrayList<>();
        for (String name : names) {
            threads.add(new LoggedThread(name));
        }
        return Matchers.contains(threads.toArray(new LoggedThread[threads.size()]));
    }

    @SuppressWarnings("unchecked")
    private List<LoggedThread> theThreadsKnownByTheModel() {
        ArgumentClosure<List<LogPart>> closure = mock(ArgumentClosure.class);
        logModel.provideThreadChoicesTo(closure, LoggedThread.LoggedThreadIdentification);

        return availableThreads(closure);
    }

    @SuppressWarnings("unchecked")
    private List<LoggedThread> availableThreads(ArgumentClosure<List<LogPart>> closure) {
        ArgumentCaptor<List> captor = ArgumentCaptor.forClass(List.class);
        verify(closure).excecute(captor.capture());
        return captor.getValue();
    }

    private void logEntryOnThread(String thread) {
        logOnThread(new LoggedThread(thread));
    }
}
