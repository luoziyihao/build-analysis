package com.coding.build.executor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import com.coding.common.build.BuildResult;
import com.coding.common.build.PomInfo;
import com.coding.common.build.Result;
import com.coding.common.build.SpecificReason;

public class ExecutorImpl implements Executor{

	File mavenHomeDir = null;

	private List<ExecutionCommand> allCommands = null;
	private Map<Member, ExecutionResult> executionResults = null;
	
	
	public ExecutorImpl(){
		mavenHomeDir = new File(BuilderConfiguration.maven_home_path);
		allCommands = new LinkedList<>();
		executionResults = new HashMap<>();
		
		prepareCommands();
	}
	
	private void prepareCommands(){
		ExecutionCommand validation = member -> {
			ExecutionResult result = new ExecutionResult();
			result.member(member);
			result.phase(BuildPhase.VALIDATION);
			boolean success = process(member, VALIDATION);
			result.success(success);
			recordResult(member, result.phase(), success, "maven validation");
			return result;
		};
		allCommands.add(validation);
		ExecutionCommand dependencyCheck = member ->{
			ExecutionResult result = new ExecutionResult();
			result.member(member);
			result.phase(BuildPhase.DEPENDENCY);
			boolean success = process(member, Executor.DEPENDENCY);
			result.success(success);
			recordResult(member, result.phase(), success, "maven dependency check");
			return result;
		};
		//allCommands.add(dependencyCheck);
		ExecutionCommand compile = member ->{
			ExecutionResult result = new ExecutionResult();
			result.member(member);
			result.phase(BuildPhase.COMPILE);
			boolean success = process(member, Executor.COMPILE);
			result.success(success);
			recordResult(member, result.phase(), success, "maven compile");
			return result;
		};
		allCommands.add(compile);
		ExecutionCommand compileTest = member ->{
			ExecutionResult result = new ExecutionResult();
			result.member(member);
			result.phase(BuildPhase.TEST_COMPILE);
			boolean success = process(member, Executor.TEST_COMPILE);
			result.success(success);
			recordResult(member, result.phase(), success, "maven test compile");
			return result;
		};
		//allCommands.add(compileTest);
		ExecutionCommand test = member ->{
			ExecutionResult result = new ExecutionResult();
			result.member(member);
			result.phase(BuildPhase.TEST);
			boolean success = process(member, Executor.TEST);
			result.success(success);
			recordResult(member, result.phase(), success, "maven test");
			return result;
		};
		//allCommands.add(test);
		ExecutionCommand surefireReport = member ->{
			ExecutionResult result = new ExecutionResult();
			result.member(member);
			result.phase(BuildPhase.REPORT);
			boolean success = process(member, Executor.REPORT);
			result.success(success);
			recordResult(member, result.phase(), success, "maven surefireReport");
			return result;
		};
		allCommands.add(surefireReport);
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
			Invoker invoker = new DefaultInvoker();
			invoker.setMavenHome(mavenHomeDir);
			result = invoker.execute( request );
			
			if(result.getExitCode() != 0 ){
				System.out.println(commandString + ": Failed.");
				if(result.getExecutionException() != null){
					System.out.println("Exception: " + result.getExecutionException().getMessage() );
				}
				return false;
			}else{
				System.out.println(commandString + ": successed.");
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
	
	private void recordResult(Member m, BuildPhase phase, boolean success, String description){
//		Result result = new Result();
//		result.buildTime(new Date());
//		result.path(m.buildPath);
		SpecificReason reason = null;
		if(success){	
			reason = ExecutorTool.mapBuildPhaseToSucess();
		}
		else{
			reason = ExecutorTool.mapBuildPhaseToFailedResultMvnBuildState(phase);
		}
		BuildResult.getInstance().setResult(m.id, m.buildPath, success, reason, new Date(), m.getPom(), description);
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
		
		allCommands/*.parallelStream()*/.forEach(cmd -> {
			target.getMembers().parallelStream().forEach(m->{
				//if(executionResults.get(m) == null || (/*executionResults.get(m).success() &&*/ ! hasFailedCase(m))){
					ExecutionResult r = cmd.execute(m);
					executionResults.put(m, r);
				//}
			});	
		});
	}
	
//	private boolean hasFailedCase(Member m){
//		List<Result> list =	BuildResult.getInstance().getResult(m.id);
//		if(list != null){
//			for(Result r : list){
//				if(! r.success()){
//					System.out.println(m + " has failed case.");
//					return true;
//				}
//			}
//		}
//		return false;
//	}
	
	@Override
	public void setMavenHome(String homePath) throws FileNotFoundException  {
		mavenHomeDir = new File(homePath);
		if(!mavenHomeDir.exists()){
			throw new FileNotFoundException("Target file path does NOT exist. " + homePath);
		}
	}
	
}
