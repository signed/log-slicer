package com.github.signed.log.loglevel;

import com.github.signed.log.core.LogPart;
import org.junit.Test;

import java.util.Collection;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LogLevelExtractor_Test {

    @SuppressWarnings("unchecked")
    private Collection<LogPart> bucket = mock(Collection.class);

    @Test
    public void takeThe3edElementFromASpaceSplitAsTheLogLevel() throws Exception {
        String warn = "2012-09-18 20:14:58,532 WARN other stuff";
        new LogLevelExtractor(warn).passLogPartTo(bucket);
        verify(bucket).add(new LogLevel("WARN"));

    }
}
