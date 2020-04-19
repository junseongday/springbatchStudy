package com.sturdy.springbatch.appleciation;

import com.sturdy.springbatch.appleciation.job.reader.jdbc.Pay;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

    @Test
    void contextLoads() {
        Pay pay = new Pay();
        Assertions.assertThat(pay).isNotNull();
    }

}
