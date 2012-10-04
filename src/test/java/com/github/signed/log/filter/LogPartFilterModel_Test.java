package com.github.signed.log.filter;

import com.github.signed.log.core.Authority;
import com.github.signed.log.list.LogModel;
import org.junit.Test;

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


}
