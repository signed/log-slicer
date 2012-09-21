package com.github.signed.log;

import com.google.common.base.Joiner;
import com.google.common.collect.Iterators;
import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RawLog_Test {

    @Test
    public void splitRawLogByTimeStampAtStartOfLine() throws Exception {
        String firstLine = "2012-09-18 20:14:58,518 stuff";
        RawLog rawLog = new RawLog(firstLine);

        assertThat(rawLog.iterator().next().text, is(firstLine));
    }


    @Test
    public void removeTrailingNewLineFromLogLine() throws Exception {
        String firstLine = "2012-09-18 20:14:58,518 stuff";
        RawLog rawLog = new RawLog(firstLine+"\n");

        assertThat(rawLog.iterator().next().text, is(firstLine));
    }

    @Test
    public void mergeFollowingLineIntoRawLingIfItDoesNotStartWithATimeStamp() throws Exception {
        String firstLine = "2012-09-18 20:14:58,518 stuff";
        String secondLine = "just some additional logging information on the next line";
        RawLog rawLog = new RawLog(Joiner.on("\n").join(firstLine, secondLine));

        assertThat(rawLog.iterator().next().text, is(firstLine+"\n"+secondLine));
    }

    @Test
    public void splitMultiLine() throws Exception {
        String firstLine = "2012-09-18 20:14:58,518 first";
        String secondLine = "2012-09-18 20:14:58,518 second";
        RawLog rawLog = new RawLog(Joiner.on("\n").join(firstLine, secondLine));

        assertThat(Iterators.size(rawLog.iterator()), is(2));
    }


    @Test
    public void returnLinesInOrder() throws Exception {
        String firstLine = "2012-09-18 20:14:58,518 first";
        String secondLine = "2012-09-18 20:14:58,518 second";
        RawLog rawLog = new RawLog(Joiner.on("\n").join(firstLine, secondLine));

        Iterator<LogEntry> iterator = rawLog.iterator();
        iterator.next();

        assertThat(iterator.next().text, is(secondLine));
    }
}
