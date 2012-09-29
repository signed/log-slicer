package com.github.signed.log.core;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class Identification_Test {

    @Test
    public void twoIdentifierWithTheSameStringAreEqual() throws Exception {
        Identification identification = new Identification("its you");
        Identification same = new Identification("its you");

        assertThat(identification, is(equalTo(same)));
    }
}