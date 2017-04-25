package com.coding.analysis.parser;

import com.coding.common.analysis.entity.surefire.ErrorInfo;
import com.coding.common.analysis.entity.surefire.PropertyInfo;
import com.coding.common.analysis.entity.surefire.TestCaseInfo;
import com.coding.common.analysis.entity.surefire.TestSuiteInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;
import strman.Strman;

import java.io.File;
import java.io.IOException;

/**
 * Created by luoziyihao on 4/18/17.
 */
@Slf4j
public class TestsuiteParserImpl implements TestsuiteParser {


    private static final String TESTSUITE_TAG = "testsuite";
    private static final String TESTCASE_TAG = Strman.append(TESTSUITE_TAG, "/testcase");
    private static final String PROPERTY_TAG = Strman.append(TESTSUITE_TAG, "/properties/property");

    private static final String SYSTEM_OUT_TAG = Strman.append(TESTCASE_TAG, "/system-out");
    private static final String ERROR_TAG = Strman.append(TESTCASE_TAG, "/error");
    @Override
    public TestSuiteInfo parse(File analysisInput) {
        Digester digester = new Digester();
        // 创建 TestSuiteInfo 对象
        digester.addObjectCreate(TESTSUITE_TAG, TestSuiteInfo.class);
        digester.addSetProperties(TESTSUITE_TAG);

        //add property
        digester.addObjectCreate(PROPERTY_TAG, PropertyInfo.class);
        digester.addSetProperties(PROPERTY_TAG);
        digester.addSetNext(PROPERTY_TAG, "addPropertyInfo");

        // add testcase
        digester.addObjectCreate(TESTCASE_TAG, TestCaseInfo.class);
        digester.addSetProperties(TESTCASE_TAG, new String[]{"classname"}, new String[]{"className"});
        digester.addBeanPropertySetter(SYSTEM_OUT_TAG, "systemOut");

        digester.addObjectCreate(ERROR_TAG, ErrorInfo.class);
        digester.addSetProperties(ERROR_TAG);
        digester.addCallMethod(ERROR_TAG, "setText", 0);

        digester.addSetNext(ERROR_TAG, "addErrorInfo");
        digester.addSetNext(TESTCASE_TAG, "addTestCaseInfo");


        // 将 testsuite 节点
        try {
            return (TestSuiteInfo) digester.parse(analysisInput);
        } catch (IOException | SAXException e) {
            throw new IllegalStateException(e);
        }
    }
}
