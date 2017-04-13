package com.coding.build.executor;

import java.io.FileNotFoundException;

import org.apache.maven.shared.invoker.MavenInvocationException;

import com.coding.build.builder.Group;

public interface Executor {

	public boolean execute(Group target) throws MavenInvocationException, FileNotFoundException;
	public void setMavenHome(String homePath) throws FileNotFoundException;
}
