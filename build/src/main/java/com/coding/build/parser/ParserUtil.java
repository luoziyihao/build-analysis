package com.coding.build.parser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import com.coding.build.builder.Group;
import com.coding.common.build.PomInfo;
import com.coding.common.build.SpecificReason;
import com.google.common.base.Strings;

import ch.qos.logback.core.net.SyslogOutputStream;

public class ParserUtil {

//	public static final String NAME_TAG="name";
//	public static final String VERSION_TAG="version";
//	public static final String MODELVERSION_TAG="modelVersion";
//	public static final String GROUPID_TAG="groupId";
	
	public static MavenXpp3Reader reader = new MavenXpp3Reader();
	
	public static void dumpParsingFaultyMap(Map<Group, SpecificReason> map){
		map.forEach((group, reason) ->{
			System.out.println("Invalid group: " + group.groupId + " reason: " + reason);
		});
	}
	
	public static List<PomInfo> searchPoms(String path){
		
		System.out.println("exploring path: " + path);
		List<Path> pomPaths = new ArrayList<>();
		try {
			pomPaths = Files.walk(Paths.get(path))
					.filter((file)->Files.isRegularFile(file) && file.getFileName().toString().toLowerCase().equals("pom.xml") )
					.collect(Collectors.toList());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map<String, Model> artifactId2ModelMap = genArtifactId2ModelMap(pomPaths);
		
		artifactId2ModelMap.forEach((key, val)->{
			System.out.println("key: " + key + " model: " + val.toString());
		});
		
		List<PomInfo> poms = new  ArrayList<PomInfo>();
		
		pomPaths.forEach(p ->{
			poms.add(parsePom(p.toAbsolutePath().toString(), artifactId2ModelMap));
		});
		
		System.out.println("dumping poms");
		poms.forEach(System.out::println);
		
		return poms;
	}
	
	private static String findName(Model curModel, Map<String, Model>  map){
		String value = curModel.getName();
		while(value==null || value.isEmpty()){
			try{
				Model pModel = map.get (curModel.getParent().getArtifactId());
				if(pModel != null){
					value = pModel.getName();	
				}
				curModel = pModel;
			}catch(Exception e){
				System.err.println(e.getMessage());
				break;
			}
		}
		return Strings.nullToEmpty(value);
	}
	
	private static String findVersion(Model curModel, Map<String, Model> map){
		String value = curModel.getName();
		while(value==null || value.isEmpty()){
			Model pModel = map.get(curModel.getParent().getArtifactId());
			if(pModel != null){
				value = pModel.getVersion();
			}
			curModel = pModel;
		}
		return value;
	}
	
	private static String findModelVersion(Model curModel, Map<String, Model> map){
		String value = curModel.getModelVersion();
		while(value==null || value.isEmpty()){
			Model pModel = map.get(curModel.getParent().getArtifactId());
			if(pModel != null){
				value = pModel.getModelVersion();
			}
			curModel = pModel;
		}
		return value;
	}
	
	private static String findModelGroupId(Model curModel, Map<String, Model> map){
		String value = curModel.getGroupId();
		while(value==null || value.isEmpty()){
			Model pModel = map.get(curModel.getParent().getArtifactId());
			if(pModel != null){
				value = pModel.getGroupId();
			}
			curModel = pModel;
		}
		return value;
	}
	
	private static Map<String, Model> genArtifactId2ModelMap(List<Path> pomPathes){
		
		Map<String, Model> map = new HashMap<>();
		pomPathes.forEach(p->{
			try {
				Model m = reader.read(new FileReader(p.toAbsolutePath().toString()));
				if(m != null){
					map.put(m.getArtifactId(), m);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		return map;
	}
	
	public static PomInfo parsePom(String pom, Map<String, Model> artifactId2ModelMap){
		MavenXpp3Reader reader = new MavenXpp3Reader();
		//PomInfo defaultPom= new PomInfo();
		PomInfo pomInfo = new PomInfo();
		try {
			pomInfo.path(pom);
			Model model = reader.read(new FileReader(pom));
			if(Objects.nonNull(model.getArtifactId())) {
				pomInfo.artifactId(model.getArtifactId());
			}
			if(Objects.nonNull(model.getGroupId())) {
				pomInfo.groupId(model.getGroupId());
			}else{
				pomInfo.groupId(findModelGroupId(model, artifactId2ModelMap));
			}
			if(Objects.nonNull(model.getVersion())) {
				pomInfo.version(model.getVersion());
			}else{
				pomInfo.version(findVersion(model, artifactId2ModelMap));
			}
			if(Objects.nonNull(model.getPackaging())) {
				pomInfo.packaging(model.getPackaging());
			}
			if(Objects.nonNull(model.getName())) {
				pomInfo.name(model.getName());
			}else{
				pomInfo.name(findName(model, artifactId2ModelMap));
			}
			if(Objects.nonNull(model.getModelVersion())) {
				pomInfo.modelVersion(model.getModelVersion());
			}else{
				pomInfo.modelVersion(findModelVersion(model, artifactId2ModelMap));
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pomInfo;
	}
}
