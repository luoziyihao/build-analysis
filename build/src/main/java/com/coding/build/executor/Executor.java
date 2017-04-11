package com.coding.build.executor;

import java.io.FileNotFoundException;

import org.apache.maven.shared.invoker.MavenInvocationException;

import com.coding.build.builder.Target;

public interface Executor {

	public boolean execute(Target target) throws MavenInvocationException, FileNotFoundException;
	public void setMavenHome(String homePath) throws FileNotFoundException;
}
