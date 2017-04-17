package com.coding.common.analysis.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * Created by luoziyihao on 4/16/17.
 */
@Data
@Accessors(chain = true)
public class AnalysisResult {
    Map<String, TestMemberInfo> testMemberInfos;
}
