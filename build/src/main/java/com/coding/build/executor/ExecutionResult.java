package com.coding.build.executor;

import com.coding.build.builder.Member;
import com.coding.build.executor.Executor.BuildPhase;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class ExecutionResult {
	Member member;
	BuildPhase phase;
	boolean success;
}
