package com.github.signed.log;

import com.github.signed.log.thread.LoggedThread;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class LoggedThread_Test {

    @Test
    public void implementsEqualsBasedOnThreadName() throws Exception {
        assertThat(new LoggedThread("name"), is(equalTo(new LoggedThread("name"))));
    }
}
