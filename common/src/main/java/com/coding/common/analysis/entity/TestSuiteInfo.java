package com.coding.common.analysis.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
public class TestSuiteInfo {
    private String name;
    private double time;
    private int tests;
    private int errors;
    private int skipped;
    private int failures;

    private List<PropertyInfo> propertyInfos;
    private List<TestCaseInfo> testCaseInfos;

    public void addTestCaseInfo(TestCaseInfo testCaseInfo) {
        if (this.testCaseInfos == null) {
            this.testCaseInfos = new ArrayList<>();
        }
        this.testCaseInfos.add(testCaseInfo);
    }

    public void addPropertyInfo(PropertyInfo propertyInfo) {
        if (this.propertyInfos == null) {
            this.propertyInfos = new ArrayList<>();
        }
        this.propertyInfos.add(propertyInfo);
    }

    public SurefireReportsCountInfo surefireReportsCountInfo() {
        return new SurefireReportsCountInfo()
                .setTime(this.getTime())
                .setTests(this.tests)
                .setErrors(this.errors)
                .setSkipped(this.skipped)
                ;
    }
}
