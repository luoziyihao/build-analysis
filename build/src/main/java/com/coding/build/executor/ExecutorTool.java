package com.coding.build.executor;

import com.coding.build.executor.Executor.BuildPhase;
import com.coding.common.build.SpecificReason;

public class ExecutorTool {
//{VALIDATION, COMPILE, DEPENDENCY, TEST_COMPILE, TEST};
	public static SpecificReason mapBuildPhaseToFailedResultMvnBuildState(BuildPhase phase){
		switch(phase){
		case VALIDATION:
			return SpecificReason.MAVEN_VALIDATION_ERROR;
//		case DEPENDENCY:
//			return SpecificReason.DEPENDENCY_FAILED;
		case COMPILE:
			return SpecificReason.MAVEN_COMPILE_FAILED;
//		case TEST_COMPILE:
//			return SpecificReason.TEST_SKIPPED;
//		case TEST:
//			return SpecificReason.TEST_FAILED;
		default:
			return SpecificReason.INDETERMINATE;	
		}
	}
	
	public static SpecificReason mapBuildPhaseToSucess(){
		return SpecificReason.SUCCESS;
	}
}
