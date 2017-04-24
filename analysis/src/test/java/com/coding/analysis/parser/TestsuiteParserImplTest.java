package com.coding.analysis.parser;

import com.alibaba.fastjson.JSONObject;
import com.coding.common.analysis.entity.TestSuiteInfo;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;

/**
 * Created by luoziyihao on 4/18/17.
 */
public class TestsuiteParserImplTest {


    private static final String FILE_PATH = "mock/test-project/target/surefire-reports/TEST-com.coding.analysis.analysis.AnalysisImplTest.xml";

    @Test
    public void parse() throws Exception {
        TestsuiteParserImpl testsuiteParser = new TestsuiteParserImpl();
        TestSuiteInfo testSuiteInfo = testsuiteParser.parse(new ClassPathResource(FILE_PATH).getFile());
        System.out.println(JSONObject.toJSONString(testSuiteInfo));

    }

}