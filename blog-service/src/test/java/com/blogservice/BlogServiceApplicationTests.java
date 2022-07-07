package com.blogservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(locations = "/application-TEST.yaml")
@SpringBootTest
class BlogServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
