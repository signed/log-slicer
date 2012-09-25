package com.github.signed.log;

import org.hamcrest.MatcherAssert;
import org.joda.time.LocalDate;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class TimeStampTest {

    @Test
    public void twoTimeStampsThatShareTheSameInstantAreEqual() throws Exception {
        MatcherAssert.assertThat(new TimeStamp(new LocalDate(2012, 9,25).toDateTimeAtStartOfDay()), is(equalTo(new TimeStamp(new LocalDate(2012, 9,25).toDateTimeAtStartOfDay()))));
    }
}
