package com.coding.common.build;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BuildResult {
	
	private Map<String, Result> buildResultMapping; //qq -> result
	
	private static BuildResult instance = null;
	
	public static BuildResult getInstance(){
		if(instance == null){
			instance = new BuildResult();
		}
		return instance;
	}
	
	private BuildResult(){
		buildResultMapping = new ConcurrentHashMap<>();
	}
	
	public void setResult(String id, String path, boolean success, SpecificReason reason, Date buildTime, List<PomInfo> pomInfos, String description){
		Result newResult = new Result();
		newResult.success(success);
		newResult.specificReason(reason);
		newResult.path(path);
		newResult.buildTime(buildTime);
		newResult.pomInfos(pomInfos);
		newResult.description(description);
		buildResultMapping.put(id, newResult);
	}
	
	public Result getResult(String id){
		return buildResultMapping.get(id);
	}
	
	public Iterator<String> getIds(){
		return buildResultMapping.keySet().iterator();
	}

	public void dump(){
		buildResultMapping.forEach((id, result) ->{
			System.out.println("ID: " + id);
			if(Objects.isNull(result)) {
				System.out.println("Result is NULL");
			}else{
				System.out.println("Result: " + result);
			}
		});
	}
}
