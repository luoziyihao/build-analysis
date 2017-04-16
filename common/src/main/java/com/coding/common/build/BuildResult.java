package com.coding.common.build;

import java.util.*;

public class BuildResult {
	
	Map<String, List<Result>> buildResultMapping;
	
	public BuildResult(){
		buildResultMapping = new HashMap<>();
	}
	
	public void addResultEntry(String id, String path, boolean success, String reason, Date buildTime, PomInfo pomInfo){
		Result newResult = new Result();
		newResult.success(success);
		newResult.specificReason(SpecificReason.valueOf(reason));
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

}
