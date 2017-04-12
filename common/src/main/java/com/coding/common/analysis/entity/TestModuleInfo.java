package com.coding.common.analysis.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by luoziyihao on 4/12/17.
 */
@Accessors(fluent = true)
@Data
public class TestModuleInfo {
    private double time;
    private int tests;
    private int errors;
    private int skipped;
    private int failures;

    private List<TestSuiteInfo> testSuiteInfos;

    private String artifactId;
    private String groupId;
}
