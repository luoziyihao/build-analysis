package com.coding.build.executor;

import java.io.FileNotFoundException;

import com.coding.build.builder.Group;

public interface Executor {
	
	//public static final String MavenHomePath = "/Users/erlisuo/Documents/workspace/lib/apache-maven-3.3.9";
	public static final String VALIDATION = "validate";
	public static final String COMPILE = "compile";
	public static final String DEPENDENCY = "dependency:copy-dependencies";
	public static final String TEST_COMPILE = "test-compile";
	public static final String TEST = "test";
	public static final String REPORT="surefire-report:report";
	public enum BuildPhase {VALIDATION, COMPILE, DEPENDENCY, TEST_COMPILE, TEST, REPORT};
	public void process(Group target);//throws MavenInvocationException, FileNotFoundException;
	public void setMavenHome(String homePath) throws FileNotFoundException;
}
