package com.coding.analysis.parser;

import com.coding.common.analysis.entity.surefire.TestSuiteInfo;

import java.io.File;

/**
 * Created by luoziyihao on 4/19/17.
 */
public interface TestsuiteParser {

    TestSuiteInfo parse(File analysisInput);
}
