package com.coding.analysis;

import com.coding.common.analysis.entity.TestSuiteInfo;

import java.io.File;

/**
 * Created by luoziyihao on 4/19/17.
 */
public interface TestsuiteParser {

    TestSuiteInfo parse(File analysisInput);
}
