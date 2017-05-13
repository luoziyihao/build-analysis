package com.coding.build.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import com.coding.build.builder.Group;
import com.coding.common.build.PomInfo;
import com.coding.common.build.SpecificReason;

public class ParserUtil {
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
		List<PomInfo> pomInfos = new ArrayList<>();
		pomPaths.forEach((pomPath) ->{
			pomInfos.add(parsePom(pomPath.toAbsolutePath().toString()));
		});
		pomInfos.forEach(System.out::println);
		return pomInfos;
	}
	
	public static PomInfo parsePom(String pom){
		MavenXpp3Reader reader = new MavenXpp3Reader();
		PomInfo pomInfo = new PomInfo();
		try {
			Model model = reader.read(new FileReader(pom));
			if(Objects.nonNull(model.getArtifactId())) {
				pomInfo.artifactId(model.getArtifactId());
			}
			if(Objects.nonNull(model.getGroupId())) {
				pomInfo.groupId(model.getGroupId());
			}
			if(Objects.nonNull(model.getVersion())) {
				pomInfo.version(model.getVersion());
			}
			if(Objects.nonNull(model.getPackaging())) {
				pomInfo.packaging(model.getPackaging());
			}
			if(Objects.nonNull(model.getName())) {
				pomInfo.name(model.getName());
			}
			if(Objects.nonNull(model.getModelVersion())) {
				pomInfo.modelVersion(model.getModelVersion());
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
