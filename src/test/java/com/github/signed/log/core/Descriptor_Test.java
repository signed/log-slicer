package com.github.signed.log.core;

import com.github.signed.log.DummyLogPart;
import org.hamcrest.Matchers;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Descriptor_Test {

    @Test
    public void determineEqualityBasedOnClassNameAndName() throws Exception {
        Descriptor one = new Descriptor("one", DummyLogPart.class);
        Descriptor anotherOne = new Descriptor("one", DummyLogPart.class);

        assertThat(one, is(Matchers.equalTo(anotherOne)));
    }
}
