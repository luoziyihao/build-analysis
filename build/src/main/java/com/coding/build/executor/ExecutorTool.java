package com.coding.build.executor;

import com.coding.build.executor.Executor.BuildPhase;
import com.coding.common.build.SpecificReason;

public class ExecutorTool {
//{VALIDATION, COMPILE, DEPENDENCY, TEST_COMPILE, TEST};
	public static SpecificReason mapBuildPhaseToFailedResultMvnBuildState(BuildPhase phase){
		switch(phase){
		case VALIDATION:
			return SpecificReason.CONFIG_ILLEGAL;
		case DEPENDENCY:
			return SpecificReason.DEPENDENCY_FAILED;
		case COMPILE:
			return SpecificReason.COMPILE_FAILED;
		case TEST_COMPILE:
			return SpecificReason.TEST_SKIPPED;
		case TEST:
			return SpecificReason.TEST_FAILED;
		default:
			return SpecificReason.FAILED;	
		}
	}
	
	public static SpecificReason mapBuildPhaseToSucess(){
		return SpecificReason.SUCCESS;
	}
}
