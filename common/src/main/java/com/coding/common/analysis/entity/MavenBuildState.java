package com.coding.common.analysis.entity;

/**
 * Created by luoziyihao on 4/20/17.
 */
public enum MavenBuildState {
    NEVER_BUILD

    , SUCCESS
    , FAILED
    , CONFIG_ILLEGAL
    , COMPILE_FAILED
    , TEST_FAILED
    , TEST_SKIPPED
    , DEPENDENCY_FAILED
}
