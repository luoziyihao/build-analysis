package com.coding.common.analysis.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by luoziyihao on 4/18/17.
 */
@Data
@Accessors(chain = true)
public class SurefireReports {
    private SurefireReportsCountInfo surefireReportsCountInfo;
    private List<TestSuiteInfo> testSuiteInfos;

}
