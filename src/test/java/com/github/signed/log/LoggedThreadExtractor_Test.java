package com.github.signed.log;

import com.github.signed.log.core.LogPart;
import com.github.signed.log.core.StringLogPart;
import com.github.signed.log.extractors.thread.LoggedThreadExtractor;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class LoggedThreadExtractor_Test {
    @SuppressWarnings("unchecked")
    private final Collection<LogPart> bucket = mock(Collection.class);

    @Test
    public void threadEndsAtTheFirstClosingBracket() throws Exception {
        String text = "stuff (thread name) a message that contains a closing braket)";
        LoggedThreadExtractor extractor = new LoggedThreadExtractor(null);
        extractor.passLogPartTo(text, bucket);

        assertThat(theExtractedThreadName(), is("thread name"));
    }

    @Test
    public void passTheRetrievedThreadInformationToTheBucket() throws Exception {
        String text = "(ThreadName)";
        LoggedThreadExtractor extractor = new LoggedThreadExtractor(null);
        extractor.passLogPartTo(text, bucket);

        assertThat(theExtractedThreadName(), is("ThreadName"));
    }

    @Test
    public void doNotInteractWithTheBucketIfThePatternCouldNotBeFoundInTheSourceString() throws Exception {
        String text = "no thread information";
        LoggedThreadExtractor extractor = new LoggedThreadExtractor(null);
        extractor.passLogPartTo(text, bucket);

        verifyZeroInteractions(bucket);
    }

    private String theExtractedThreadName() {
        ArgumentCaptor<LogPart> captor = ArgumentCaptor.forClass(LogPart.class);
        verify(bucket).add(captor.capture());
        StringLogPart value = (StringLogPart) captor.getValue();
        return value.text;
    }
}