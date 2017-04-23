package com.coding.analysis.parser;

import com.alibaba.fastjson.JSONObject;
import com.coding.common.analysis.entity.TestSuiteInfo;
import org.junit.Test;

import java.io.File;

/**
 * Created by luoziyihao on 4/18/17.
 */
public class TestsuiteParserImplTest {


    private static final String FILE_PATH = "/home/luoziyihao/Works/OPEN_SOURCE/build-analysis/build/target/surefire-reports/TEST-com.coding.build.test.BuilderTest.xml";

    @Test
    public void parse() throws Exception {
        TestsuiteParserImpl testsuiteParser = new TestsuiteParserImpl();
        TestSuiteInfo testSuiteInfo = testsuiteParser.parse(new File(FILE_PATH));
        System.out.println(JSONObject.toJSONString(testSuiteInfo));

    }

}