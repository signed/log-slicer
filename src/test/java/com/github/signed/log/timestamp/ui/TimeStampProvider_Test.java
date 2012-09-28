package com.github.signed.log.timestamp.ui;

import com.github.signed.log.core.LogEntry;
import com.github.signed.log.core.LogPart;
import com.github.signed.log.timestamp.TimeStamp;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TimeStampProvider_Test {

    @Test
    public void testName() throws Exception {
        LogPart timeStamp = new TimeStamp(null);
        LogPart from = new TimeStampProvider().from(new LogEntry("", Collections.singleton(timeStamp)));
        assertThat(from, is((LogPart)new TimeStamp(null)));
    }
}
