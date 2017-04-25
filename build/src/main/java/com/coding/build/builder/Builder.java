package com.coding.build.builder;

import java.util.List;
import java.util.Map;

import com.coding.build.executor.Executor;
import com.coding.build.parser.Parser;
import com.coding.build.validator.Validator;
import com.coding.common.build.BuildResult;
import com.coding.common.build.Result;

public interface Builder {
	public void buildAll() throws BuildException;
	public BuildResult getResult();
	public void setParser(Parser parser);
	public void setExecutor(Executor executor);
	public void setValidator(Validator validator);
	public void init();
}
