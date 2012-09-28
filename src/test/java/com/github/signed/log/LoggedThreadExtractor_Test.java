package com.github.signed.log;

import com.github.signed.log.core.LogPart;
import com.github.signed.log.thread.LoggedThread;
import com.github.signed.log.thread.LoggedThreadExtractor;
import org.junit.Test;

import java.util.Collection;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class LoggedThreadExtractor_Test {
    @SuppressWarnings("unchecked")
    private final Collection<LogPart> bucket = mock(Collection.class);

    @Test
    public void threadEndsAtTheFirstClosingBracket() throws Exception {
        LoggedThreadExtractor extractor = new LoggedThreadExtractor("stuff (thread name) a message that contains a closing braket)");
        extractor.passLogPartTo(bucket);

        verify(bucket).add(new LoggedThread("thread name"));
    }

    @Test
    public void passTheRetrievedThreadInformationToTheBucket() throws Exception {
        LoggedThreadExtractor extractor = new LoggedThreadExtractor("(ThreadName)");
        extractor.passLogPartTo(bucket);

        verify(bucket).add(new LoggedThread("ThreadName"));
    }

    @Test
    public void doNotInteractWithTheBucketIfThePatternCouldNotBeFoundInTheSourceString() throws Exception {
        LoggedThreadExtractor extractor = new LoggedThreadExtractor("no thread information");
        extractor.passLogPartTo(bucket);

        verifyZeroInteractions(bucket);
    }
}