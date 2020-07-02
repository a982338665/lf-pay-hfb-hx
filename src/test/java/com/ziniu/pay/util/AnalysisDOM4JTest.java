package com.ziniu.pay.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AnalysisDOM4JTest {

    @Resource
    AnalysisDOM4J analysisDOM4J;

    /*@Test
    public void parserDOM4JV2() {
        analysisDOM4J.parserDOM4JV2();
    }*/
}
