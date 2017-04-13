package com.coding.build.builder;

import com.coding.build.executor.Executor;

public interface Builder {

	public boolean build(Group target);
	public void setExecutor(Executor executor);
}
