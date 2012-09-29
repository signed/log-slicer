package com.github.signed.log.filter;

import com.github.signed.log.core.Authority;
import com.github.signed.log.list.LogModel;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LogPartFilterModel_Test {

    @Test
    public void forwardTheAuthorityToTheWrappedModel() throws Exception {
        Authority authority = mock(Authority.class);
        LogModel wrapped = mock(LogModel.class);
        LogPartFilterModel model = new LogPartFilterModel(wrapped);
        model.describeTo(authority);

        verify(wrapped).describeTo(authority);
    }
}
