package com.coding.common.analysis.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by luoziyihao on 4/12/17.
 */
@Data
@Accessors(fluent = true)
public class TestSuiteInfo {
    private List<PropertyInfo> propertyInfos;
    private List<TestCaseInfo> testCaseInfos;
}
