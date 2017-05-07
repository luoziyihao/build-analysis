package com.coding.common.build;

public enum SpecificReason {
	SUCCESS, 
	INVALID_FORMAT, 
	MAVEN_POM_NOT_FOUND,
	NO_SUCH_DIRECTORY, //The path specified in JSON configuration for a member is not found
	INVALID_JSON_CONFIG, //JSON file has a faulty format

      FAILED
    , CONFIG_ILLEGAL
    , COMPILE_FAILED
    , TEST_FAILED
    , TEST_SKIPPED
    , DEPENDENCY_FAILED
}
