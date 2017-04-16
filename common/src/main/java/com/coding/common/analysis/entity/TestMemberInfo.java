package com.coding.common.analysis.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by luoziyihao on 4/12/17.
 */
@Accessors(chain = true)
@Data
public class TestMemberInfo {
    private String qq;
    private List<TestModuleInfo> testModuleInfos;
}
