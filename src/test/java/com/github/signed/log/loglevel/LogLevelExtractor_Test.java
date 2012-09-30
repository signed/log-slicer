package com.github.signed.log.loglevel;

import com.github.signed.log.core.LogPart;
import com.github.signed.log.core.StringLogPart;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LogLevelExtractor_Test {

    @SuppressWarnings("unchecked")
    private Collection<LogPart> bucket = mock(Collection.class);

    @Test
    public void takeThe3edElementFromASpaceSplitAsTheLogLevel() throws Exception {
        String warn = "2012-09-18 20:14:58,532 WARN other stuff";
        new LogLevelExtractor(null, warn).passLogPartTo(bucket);

        assertThat(theExtractedLogLevel(), is("WARN"));
    }

    private String theExtractedLogLevel() {
        ArgumentCaptor<LogPart> captor = ArgumentCaptor.forClass(LogPart.class);
        verify(bucket).add(captor.capture());
        StringLogPart part = (StringLogPart) captor.getValue();

        return part.text;
    }
}
