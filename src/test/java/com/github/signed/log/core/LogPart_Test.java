package com.github.signed.log.core;

import com.github.signed.log.DummyLogPart;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LogPart_Test {
    @SuppressWarnings("unchecked")
    private final Authority authority = mock(Authority.class);

    @Test
    public void handOverTheDescriptorToTheBucket() throws Exception {
        DummyLogPart you = new DummyLogPart("you");
        you.describeTo(authority);

        verify(authority).accept(new Descriptor("Dummy", DummyLogPart.class, true));
    }
}
