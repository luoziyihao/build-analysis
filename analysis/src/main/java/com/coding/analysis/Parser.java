package com.coding.analysis;

import com.coding.common.analysis.entity.TestMemberInfo;

/**
 * Created by luoziyihao on 4/12/17.
 */
public interface Parser {
    TestMemberInfo parse(AnalysisInput analysisInput);
}
