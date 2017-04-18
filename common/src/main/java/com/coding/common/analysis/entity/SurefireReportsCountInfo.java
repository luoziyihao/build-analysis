package com.coding.common.analysis.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by luoziyihao on 4/18/17.
 */
@Data
@Accessors(chain = true)
public class SurefireReportsCountInfo {
    private double time;
    private int tests;
    private int errors;
    private int skipped;
    private int failures;
}
