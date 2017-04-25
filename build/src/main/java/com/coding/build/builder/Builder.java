package com.coding.build.builder;

import java.util.List;

import com.coding.build.executor.Executor;
import com.coding.build.parser.Parser;
import com.coding.build.validator.Validator;

public interface Builder {
	public List<Group> fetchGroups(String projectRoot);
	public boolean buildAll(List<Group> groups);
	public boolean build(Group target);
	public void setParser(Parser parser);
	public void setExecutor(Executor executor);
	public void setValidator(Validator validator);
}
