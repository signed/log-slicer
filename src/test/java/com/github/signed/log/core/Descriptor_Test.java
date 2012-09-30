package com.github.signed.log.core;

import org.hamcrest.Matchers;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Descriptor_Test {

    @Test
    public void determineEqualityBasedOnIdentification() throws Exception {
        Descriptor one = new Descriptor(new Identification("one"), "one", true, false);
        Descriptor anotherOne = new Descriptor(new Identification("one"), "other", false, true);

        assertThat(one, is(Matchers.equalTo(anotherOne)));
    }
}
