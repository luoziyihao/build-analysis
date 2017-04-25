package com.coding.common.build;

import java.util.*;

public class BuildResult {
	
	Map<String, List<Result>> buildResultMapping; //qq -> result
	
	private static BuildResult instance = null;
	
	public static BuildResult getInstance(){
		if(instance == null){
			instance = new BuildResult();
		}
		return instance;
	}
	//if no other place is using the buildResult instance, i will change it to private later.
	public BuildResult(){
		buildResultMapping = new HashMap<>();
	}
	
	public void addResultEntry(String id, String path, boolean success, SpecificReason reason, Date buildTime, PomInfo pomInfo){
		
		Result newResult = new Result();
		newResult.success(success);
		newResult.specificReason(reason);
		newResult.path(path);
		newResult.buildTime(buildTime);
		newResult.pomInfo(pomInfo);
		if(buildResultMapping.get(id) == null){
			List<Result> results = new LinkedList<>();
			buildResultMapping.put(id, results);
		}
		buildResultMapping.get(id).add(newResult);
	}
	
	public List<Result> getResult(String id){
		return buildResultMapping.get(id);
	}
	
	public Iterator<String> getIds(){
		return buildResultMapping.keySet().iterator();
	}

	public Set<Map.Entry<String, List<Result>>> entrySet(){
		return buildResultMapping.entrySet();
	}

	public void dump(){
		buildResultMapping.forEach((id, l) ->{
			System.out.println("ID: " + id);
			l.forEach(System.out::println);
		});
	}
}
