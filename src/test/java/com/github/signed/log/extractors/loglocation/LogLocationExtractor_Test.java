package com.github.signed.log.extractors.loglocation;

import com.github.signed.log.core.LogPart;
import com.github.signed.log.core.StringLogPart;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LogLocationExtractor_Test {

    @SuppressWarnings("unchecked")
    private Collection<LogPart> bucket = mock(Collection.class);

    @Test
    public void takeTheFourthElementAndRemoveEnclosingBrackets() throws Exception {
        String rawLogLine = "2012-09-18 20:14:58,531 INFO  [org.jboss.ejb3.proxy.impl.jndiregistrar.JndiSessionRegistrarBase] (Thread-2)";
        new LogLocationExtractor(null, rawLogLine).passLogPartTo(bucket);

        assertThat(theExtractedLogLocation(), is("org.jboss.ejb3.proxy.impl.jndiregistrar.JndiSessionRegistrarBase"));
    }

    private String theExtractedLogLocation() {
        ArgumentCaptor<LogPart> captor = ArgumentCaptor.forClass(LogPart.class);
        verify(bucket).add(captor.capture());
        StringLogPart part = (StringLogPart) captor.getValue();
        return part.text;
    }

}
