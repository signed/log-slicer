package com.github.signed.log.filter;

import com.github.signed.log.DummyLogPart;
import com.github.signed.log.core.Identification;
import com.github.signed.log.core.LogEntry;
import com.github.signed.log.list.SimpleLogModel;
import lang.ArgumentClosure;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LogPartFilterModel_ApplyFilterIntegrationTest {
    private final SimpleLogModel simpleLogModel = new SimpleLogModel();
    private final LogPartFilterModel model = new LogPartFilterModel(simpleLogModel);
    private final LogEntry singleLogEntry = new LogEntry();

    @Before
    public void addLogEntryToModel() throws Exception {
        simpleLogModel.addEntriesFrom(Collections.singletonList(singleLogEntry));
    }

    @Test
    public void replaceAllLogEntriesThatDoNotMatchAllTheAppliedFilterRulesWithANullLogEntry() throws Exception {
        addLogPartWithIdentificationAndProperty("first", "no match");
        addLogPartWithIdentificationAndProperty("second", "match");

        filterLogPartWithIdentificationByProperty("first", "thats not what the LogEntry has ...");
        filterLogPartWithIdentificationByProperty("second", "match");

        assertThat(afterTheSingleLogEntryIsProcessedByTheFilters(model), sameInstance(LogEntry.Null));
    }

    @Test
    public void forwardLogEntryIfAllConfiguredFilterMatch() throws Exception {
        addLogPartWithIdentificationAndProperty("first", "match made in heaven");
        addLogPartWithIdentificationAndProperty("second", "match");

        filterLogPartWithIdentificationByProperty("first", "match made in heaven");
        filterLogPartWithIdentificationByProperty("second", "match");

        assertThat(afterTheSingleLogEntryIsProcessedByTheFilters(model), sameInstance(singleLogEntry));
    }

    @Test
    public void forwardAllLogEntriesIfNoFiltersAreSet() throws Exception {
        addLogPartWithIdentificationAndProperty("first", "no match");
        addLogPartWithIdentificationAndProperty("second", "match");

        assertThat(afterTheSingleLogEntryIsProcessedByTheFilters(model), sameInstance(singleLogEntry));
    }

    private void filterLogPartWithIdentificationByProperty(String identification, String property) {
        model.matches(new Identification(identification), new DummyLogPart(property));
    }

    private void addLogPartWithIdentificationAndProperty(String identifier, String property) {
        singleLogEntry.addPart(new Identification(identifier), new DummyLogPart(property));
    }

    private LogEntry afterTheSingleLogEntryIsProcessedByTheFilters(LogPartFilterModel model) {
        ArgumentCaptor<List> captor = ArgumentCaptor.forClass(List.class);
        ArgumentClosure<List<LogEntry>> filteredLogEntries = mock(ArgumentClosure.class);
        model.provideElementsTo(filteredLogEntries);
        verify(filteredLogEntries).execute(captor.capture());
        List<LogEntry> entries = captor.getValue();
        return entries.get(0);
    }
}
