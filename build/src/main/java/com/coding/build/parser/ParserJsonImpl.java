package com.coding.build.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;

import com.coding.build.builder.Builder;
import com.coding.build.builder.BuilderConfiguration;
import com.coding.build.builder.BuilderImpl;
import com.coding.build.builder.Group;
import com.coding.build.builder.Member;
import com.coding.common.build.SpecificReason;

public class ParserJsonImpl implements Parser{
	
	Map<Group, SpecificReason> parsingResult;
	
	public ParserJsonImpl(){
		parsingResult = new HashMap<>();
	}

//	public Parser instance(){
//		if(instance == null){
//			instance = new ParserJsonImpl();
//		}
//		return instance;
//	}
	/*
	 * (non-Javadoc)
	 * @see com.coding.build.parser.Parser#parse(java.lang.String)
	 * NOTE: we may need to provide different specificReasons later..currently I give invalid_json_config to all failure cases.
	 */
	@Override
	public Group parse(String dirPath) {
		//System.out.println("Start parsing: " + dirPath);
		File[] jsonFiles = getAllJsonFiles(new File(dirPath));
		
		String groupId = parseId(dirPath);
		if(groupId == null){
			return null;
		}
		Group newGroup = new Group(groupId);
		if(jsonFiles.length == 0 || jsonFiles.length > 1){
						
			parsingResult.put(newGroup, SpecificReason.INVALID_JSON_CONFIG);
			return null;
		}
		
		File file = jsonFiles[0];
		if(! checkFile(file)){
			System.err.println(file.getName() + " is NOT a valid in file system check.");
			parsingResult.put(newGroup, SpecificReason.INVALID_JSON_CONFIG);
			return null;
		}
		try{
			JSONObject obj = getJsonObject(file);
			newGroup = convertToGroup(obj, file.getName());
		}catch(ParserFailException pfe){
			pfe.printStackTrace();
			parsingResult.put(newGroup, SpecificReason.INVALID_JSON_CONFIG);
			return null;
		}catch(JSONException je){
			je.printStackTrace();
			parsingResult.put(newGroup, SpecificReason.INVALID_JSON_CONFIG);
			return null;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		System.out.println("Valid group: " + newGroup.groupId);
		return newGroup;
	}
	
	private String parseId(String filePath){
		int index = filePath.lastIndexOf(File.separator);
		String r = null;
		if(index >-1){
			r = filePath.substring(index+1);
		}
		return r;
	}
	
	private File[] getAllJsonFiles(File dirPath){
		if(dirPath != null && dirPath.exists() && dirPath.canRead() && dirPath.isDirectory()){
			File[] jsonFiles = dirPath.listFiles(new JsonFilenameFilter());
			Arrays.stream(jsonFiles).forEach(System.out::println);
			return jsonFiles;
		}
		System.out.println("DirPath is NOT valid: " + dirPath); 
		return new File[0];
	}
	
	/*
	 * {
	  "config": [
	    {
	      "id": "1204187480",
	      "codePath": "group17/1204187480/code/homework"
	    },
	    {
	      "id": "1158154002",
	      "codePath": "group17/1158154002"
	    }
	  ]
	}
	 */
	private Group convertToGroup(JSONObject jsonObject, String groupId) throws JSONException, ParserFailException{
		if(jsonObject == null) throw new ParserFailException("Invalid JSON object for: " + groupId) ;
		Group group = new Group(groupId);
		JSONArray memberObjArray =  jsonObject.getJSONArray(BuilderConfiguration.CONFIG_TAG);
		for(int i = 0; i<memberObjArray.length(); i++){
			JSONObject memberObj = memberObjArray.getJSONObject(i);
			String id = memberObj.getString(BuilderConfiguration.ID_TAG);
			String path = memberObj.getString(BuilderConfiguration.CODE_PATH_TAG);
			Member newMember = new Member();
			newMember.id = id; newMember.buildPath = BuilderConfiguration.project_root + File.separator+  path;
			group.addMember(newMember);
		}
		
		return group;
	}
	
	private JSONObject getJsonObject(File file) throws ParserFailException{
		
		String jsonContent = readFile(file);
		JSONObject newObject = null;
		try {
			newObject = new JSONObject(jsonContent);
		} catch (JSONException e) {
			e.printStackTrace();
			throw new ParserFailException("Failed to parser Json content.");
		}
		return newObject;
	}
	
	private String readFile(File file){
		FileReader reader = null;
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		try {
			reader = new FileReader(file);
			br = new BufferedReader(reader);
			String line;
			while((line = br.readLine()) != null){
				sb.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
			if(br != null){
				try{
					br.close();
				}catch(IOException e){
					
				}
			}
		}
		return sb.toString();
	}
	
	private boolean checkFile(File jsonFile){
		System.out.println("Checking jsonFile: " + jsonFile);
		if(!jsonFile.exists() || ! jsonFile.canRead() || jsonFile.isDirectory()){
			return false;
		}
		return true;
	}
	
	private class JsonFilenameFilter implements FilenameFilter{

		@Override
		public boolean accept(File dir, String name) {
			if(name.endsWith(".json")){
				return true;
			}
			return false;
		}
		
	}

	@Override
	public Map<Group, SpecificReason> getFaultyGroups() {
		return parsingResult;
	}

}
