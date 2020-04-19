package com.sturdy.springbatch.appleciation.job.reader.jdbc;


import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PayTest {

    @Test
    public void getId() {
        Pay pay = new Pay();
        pay.setTxName("dd");
        Assertions.assertThat(pay).isNotNull();
    }
}