package com.github.signed.log.extractors.logmessage;

import com.github.signed.log.core.LogPart;
import com.github.signed.log.core.StringLogPart;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LogMessageExtractor_Test {

    @Test
    public void everythingAfterTheFifthElementIsTheMessage() throws Exception {
        String rawLogLine = "one two three four (five) this is the multie\nline\nmessage";
        new LogMessageExtractor(null, rawLogLine).passLogPartTo(rawLogLine, bucket);
        assertThat(theExtractedMessage(), is("this is the multie\n" +
                "line\n" +
                "message"));
    }



    @SuppressWarnings("unchecked")
    private Collection<LogPart> bucket = mock(Collection.class);


    private String theExtractedMessage() {
        ArgumentCaptor<LogPart> captor = ArgumentCaptor.forClass(LogPart.class);
        verify(bucket).add(captor.capture());
        StringLogPart part = (StringLogPart) captor.getValue();
        return part.text;
    }
}
