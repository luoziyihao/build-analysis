package com.coding.common.build;

public enum SpecificReason {
	SUCCESS,  //everything went well. surefire-report generated
	MAVEN_VALIDATION_ERROR, // mvn validate fail, including missing pom, faulty pom format
	MAVEN_COMPILE_FAILED, //mvn compile fail
	NO_SUCH_DIRECTORY, //The path specified in JSON configuration for a member is not found
	INVALID_JSON_CONFIG, //JSON file has a faulty format,
	INDETERMINATE //for any unpredictable reason
}
