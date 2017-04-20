package com.coding.build.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.coding.build.builder.Group;
import com.coding.build.builder.Member;

public class ParserJsonImpl implements Parser{

	public static final String CONFIG_TAG = "config";
	public static final String ID_TAG = "id";
	public static final String CODE_PATH_TAG = "codePath";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public ParserJsonImpl(){
		
	}

	@Override
	public List<Group> parse(String dirPath) {
		List<Group> groups = new LinkedList<>();
		File[] jsonFiles = getAllJsonFiles(new File(dirPath));
		for(File file : jsonFiles)
		{
			try{
				if(! checkFile(file)){
					throw new ParserFailException("Invalid file: " + file);
				}
				JSONObject obj = getJsonObject(file);
				Group group = convertToGroup(obj, file.getName());
				groups.add(group);
			
			}catch(ParserFailException e){
				System.err.println("Invalid input json source file. " + e.getMessage());
				continue;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return groups;
	}
	
	private File[] getAllJsonFiles(File dirPath){
		if(dirPath != null && dirPath.exists() && dirPath.canRead() && dirPath.isDirectory()){
			File[] jsonFiles = dirPath.listFiles(new JsonFilenameFilter());
			for(File f : jsonFiles){
				System.out.println("File path: " + f.getName());
				
			}
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
	private Group convertToGroup(JSONObject jsonObject, String groupId) throws JSONException{
		if(jsonObject == null) return null;
		Group group = new Group(groupId);
		JSONArray memberObjArray =  jsonObject.getJSONArray(CONFIG_TAG);
		for(int i = 0; i<memberObjArray.length(); i++){
			JSONObject memberObj = memberObjArray.getJSONObject(i);
			String id = memberObj.getString(ID_TAG);
			String path = memberObj.getString(CODE_PATH_TAG);
			Member newMember = new Member();
			newMember.id = id; newMember.buildPath = path;
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

}
