package com.coding.build.executor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;

import com.coding.build.builder.Target;

public class ExecutorImpl implements Executor{

	File mavenHomeDir = null;
	public ExecutorImpl(){
		
	}
	
	
	@Override
	public boolean execute(Target target) throws MavenInvocationException, FileNotFoundException {
		File targetPathFile = new File(target.buildPath);
		if(targetPathFile == null || ! targetPathFile.exists()) throw new FileNotFoundException("Target file path does NOT exist. " + target.buildPath);
		
		InvocationRequest request = new DefaultInvocationRequest();
		request.setPomFile( targetPathFile );
		request.setGoals( Collections.singletonList( "compile" ) );
		 
		Invoker invoker = new DefaultInvoker();
		invoker.setMavenHome(mavenHomeDir);
		InvocationResult result = invoker.execute( request );
		
		
		if(result.getExitCode() != 0 ){
			if(result.getExecutionException() != null){
				System.out.println("Exception: " + result.getExecutionException().getMessage() );
			}
			return false;
		}else{
			System.out.println("execution result: " + result.getExitCode());
			return true;
		}
	}


	@Override
	public void setMavenHome(String homePath) throws FileNotFoundException  {
		mavenHomeDir = new File(homePath);
		if(!mavenHomeDir.exists()){
			throw new FileNotFoundException("Target file path does NOT exist. " + homePath);
		}
	}
	
}
