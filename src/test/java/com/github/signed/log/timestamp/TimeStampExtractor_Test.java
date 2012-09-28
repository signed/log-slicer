package com.github.signed.log.timestamp;

import com.github.signed.log.core.LogPart;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Collection;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class TimeStampExtractor_Test {
    @SuppressWarnings("unchecked")
    private final Collection<LogPart> bucket = mock(Collection.class);

    @Test
    public void putExtractedLogPartIntoTheBucket() throws Exception {
        String input = "2012-09-18 20:14:58,518 stuff (ThreadName)";
        new TimeStampExtractor(input).passLogPartTo(bucket);

        verify(bucket).add(new TimeStamp(new DateTime(2012, 9, 18, 20, 14, 58, 518)));
    }

    @Test
    public void doNotInteractWithTheBucketIfTheLogPartCanNotBeFoundInTheSourceString() throws Exception {
        String input = "stuff";
        new TimeStampExtractor(input).passLogPartTo(bucket);

        verifyZeroInteractions(bucket);
    }

}
