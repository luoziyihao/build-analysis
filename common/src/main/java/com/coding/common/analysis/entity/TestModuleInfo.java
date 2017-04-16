package com.coding.common.analysis.entity;

import com.coding.common.build.Result;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by luoziyihao on 4/12/17.
 */
@Data
@Accessors(chain = true)
public class TestModuleInfo {
    private double time;
    private int tests;
    private int errors;
    private int skipped;
    private int failures;

    private List<TestSuiteInfo> testSuiteInfos;

    private String artifactId;
    private String groupId;
    private Result result;
}
