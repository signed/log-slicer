package com.github.signed.log.filter;

import com.github.signed.log.DummyLogPart;
import com.github.signed.log.core.Identification;
import com.github.signed.log.core.LogEntry;
import com.github.signed.log.list.SimpleLogModel;
import lang.ArgumentClosure;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LogPartFilterModel_ApplyFilterIntegrationTest {
    @Test
    public void replaceAllLogEntriesThatDoNotMatchAllTheAppliedFilterRulesWithANullLogEntry() throws Exception {
        SimpleLogModel simpleLogModel = new SimpleLogModel();
        LogEntry logEntry1 = new LogEntry();
        logEntry1.addPart(new Identification("first"), new DummyLogPart("no match"));
        logEntry1.addPart(new Identification("second"), new DummyLogPart("match"));
        simpleLogModel.addEntriesFrom(Collections.singletonList(logEntry1));

        LogPartFilterModel model = new LogPartFilterModel(simpleLogModel);

        model.matches(new Identification("first"), new DummyLogPart("thats not what the LogEntry has ..."));
        model.matches(new Identification("second"), new DummyLogPart("match"));


        ArgumentCaptor<List> captor = ArgumentCaptor.forClass(List.class);
        ArgumentClosure<List<LogEntry>> filteredLogEntries = mock(ArgumentClosure.class);
        model.provideElementsTo(filteredLogEntries);
        verify(filteredLogEntries).execute(captor.capture());
        List<LogEntry> entries = captor.getValue();
        LogEntry logEntry = entries.get(0);
        assertThat(logEntry, is(sameInstance(LogEntry.Null)));
    }
}
