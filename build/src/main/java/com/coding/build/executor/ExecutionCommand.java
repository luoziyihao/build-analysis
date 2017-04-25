package com.coding.build.executor;

import com.coding.build.builder.Member;

public interface ExecutionCommand {
	public ExecutionResult execute(Member m);
}
