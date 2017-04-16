package com.coding.common.analysis.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by luoziyihao on 4/12/17.
 */
@Data
@Accessors(chain = true)
public class TestCaseInfo {
    private String name;
    private String className;
    private Double time;
    private List<ErrorInfo> errorInfos;
}
