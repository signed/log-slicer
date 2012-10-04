package com.github.signed.log.filter;

import com.github.signed.log.core.Authority;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.list.LogModel;
import lang.ArgumentClosure;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LogPartFilterModel_Test {
    private final LogModel wrapped = mock(LogModel.class);
    private final LogPartFilterModel model = new LogPartFilterModel(wrapped);

    @Test
    public void forwardTheAuthorityToTheWrappedModel() throws Exception {
        Authority authority = mock(Authority.class);
        model.describeTo(authority);

        verify(wrapped).describeTo(authority);
    }

    @Test
    public void passTheElementsAsSelected() throws Exception {
        LogPart first = mock(LogPart.class);
        model.matches(first);

        assertThat(theWitheListedParts(), hasItems(first));
    }

    @SuppressWarnings("unchecked")
    private List<LogPart> theWitheListedParts() {
        ArgumentClosure<List<LogPart>> whiteListedLogParts = mock(ArgumentClosure.class);
        ArgumentCaptor<List> captor = ArgumentCaptor.forClass(List.class);
        model.provideSelectedThreadsTo(whiteListedLogParts);
        verify(whiteListedLogParts).excecute(captor.capture());

        return captor.getValue();
    }
}
