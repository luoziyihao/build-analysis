package com.coding.build.builder;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.maven.shared.invoker.MavenInvocationException;

import com.coding.build.executor.Executor;

public class BuilderImpl implements Builder{

	protected Executor executor = null;
	protected String maven_home_path = "/telos/esuo/maven/current";
	protected String MAVEN_HOME_TAG = "M3_HOME"; 
	public BuilderImpl(){
		maven_home_path = System.getenv(MAVEN_HOME_TAG);
	}
	
	@Override
	public boolean build(Group target) {
		System.out.println("Going to build target: " + target);
		if(executor != null){
			try {
				executor.setMavenHome(maven_home_path);
				executor.execute(target);
			} catch (MavenInvocationException | FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	public void setExecutor(Executor executor){
		this.executor = executor;
	}
	

}
