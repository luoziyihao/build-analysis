package com.coding.build.builder;

import com.coding.build.executor.Executor;

public interface Builder {

	public boolean build(Target target);
	public void setExecutor(Executor executor);
}
