package com.github.signed.log.core;

import org.hamcrest.Matchers;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Descriptor_Test {

    @Test
    public void determineEqualityBasedOnName() throws Exception {
        Descriptor one = new Descriptor("one", true);
        Descriptor anotherOne = new Descriptor("one", false);

        assertThat(one, is(Matchers.equalTo(anotherOne)));
    }
}
