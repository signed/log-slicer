package com.github.signed.log.core;

import org.junit.Test;

import java.util.Collection;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;

public class NullLogPart_Test {

    @SuppressWarnings("unchecked")
    private Collection<Descriptor> authority = mock(Collection.class);

    @Test
    public void doNotHandOverADescriptor() throws Exception {
        LogPart theNullLogPart = NullLogPart.TheNullLogPart;
        theNullLogPart.describeTo(authority);

        verifyZeroInteractions(authority);
    }
}
