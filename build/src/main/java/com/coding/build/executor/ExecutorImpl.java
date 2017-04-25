package com.coding.build.executor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;

import com.coding.build.builder.BuilderConfiguration;
import com.coding.build.builder.Group;
import com.coding.build.builder.Member;

public class ExecutorImpl implements Executor{

	File mavenHomeDir = null;

	private List<ExecutionCommand> allCommands = null;
	private Map<Member, ExecutionResult> executionResults = null;
	Invoker invoker = null;
	
	public ExecutorImpl(){
		mavenHomeDir = new File(BuilderConfiguration.maven_home_path);
		allCommands = new LinkedList<>();
		executionResults = new HashMap<>();
		invoker = new DefaultInvoker();
		invoker.setMavenHome(mavenHomeDir);
		prepareCommands();
	}
	
	private void prepareCommands(){
		ExecutionCommand validation = member -> {
			ExecutionResult result = new ExecutionResult();
			result.member(member);
			result.phase(BuildPhase.VALIDATION);
			boolean success = process(member, VALIDATION);
			result.success(success);
			return result;
		};
		allCommands.add(validation);
		ExecutionCommand dependencyCheck = member ->{
			ExecutionResult result = new ExecutionResult();
			result.member(member);
			result.phase(BuildPhase.DEPENDENCY);
			boolean success = process(member, Executor.DEPENDENCY);
			result.success(success);
			return result;
		};
		allCommands.add(dependencyCheck);
		ExecutionCommand compile = null;
		ExecutionCommand compileTest = null;
		ExecutionCommand test = null;
		 
	}
	
	public boolean process(Member m, String commandString){
		System.out.println("About to process : " + m + " command: " + commandString);
		File targetPathFile = new File(m.buildPath); 
		if(targetPathFile == null || ! targetPathFile.exists()) {
			System.err.println("build path is NOT valid");
			return false;
		}
		InvocationRequest request = new DefaultInvocationRequest();
		request.setPomFile( targetPathFile );
		request.setGoals( Collections.singletonList( commandString ) );
		InvocationResult result;
		try {
			result = invoker.execute( request );
			
			if(result.getExitCode() != 0 ){
				System.out.println(commandString + ": successful.");
				if(result.getExecutionException() != null){
					System.out.println("Exception: " + result.getExecutionException().getMessage() );
				}
				return false;
			}else{
				System.out.println("execution result: " + result.getExitCode());
				return true;
			}
		} catch (MavenInvocationException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	
//	public boolean process(Group target) throws MavenInvocationException, FileNotFoundException {
//		File targetPathFile = new File(target.getMember(0).buildPath);
//		if(targetPathFile == null || ! targetPathFile.exists()) throw new FileNotFoundException("Target file path does NOT exist. " + target.getMember(0).buildPath);
//		
//		InvocationRequest request = new DefaultInvocationRequest();
//		request.setPomFile( targetPathFile );
//		request.setGoals( Collections.singletonList( "compile" ) );
//		 
//		
//		InvocationResult result = invoker.execute( request );
//		
//		
//		if(result.getExitCode() != 0 ){
//			if(result.getExecutionException() != null){
//				System.out.println("Exception: " + result.getExecutionException().getMessage() );
//			}
//			return false;
//		}else{
//			System.out.println("execution result: " + result.getExitCode());
//			return true;
//		}
//	}
	@Override
	public void process(Group target) {//throws MavenInvocationException, FileNotFoundException {
		target.getMembers().forEach(m->{
			allCommands.forEach(cmd -> {
				ExecutionResult r = cmd.execute(m);
				executionResults.put(m, r);
			});
		});
	}
	
	@Override
	public void setMavenHome(String homePath) throws FileNotFoundException  {
		mavenHomeDir = new File(homePath);
		if(!mavenHomeDir.exists()){
			throw new FileNotFoundException("Target file path does NOT exist. " + homePath);
		}
	}
	
}
