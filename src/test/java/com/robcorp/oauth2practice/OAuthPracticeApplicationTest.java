package com.robcorp.oauth2practice;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OAuthPracticeApplicationTest {

    @Autowired WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void things() {
        assertThat("foo").isEqualTo("foo");
    }
}