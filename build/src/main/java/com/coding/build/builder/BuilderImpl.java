package com.coding.build.builder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.apache.maven.shared.invoker.MavenInvocationException;

import com.coding.build.executor.Executor;
import com.coding.build.parser.Parser;
import com.coding.build.parser.ParserFailException;
import com.coding.build.validator.Validator;

public class BuilderImpl implements Builder{

	protected Executor executor = null;
	
	
	public Parser jsonParser = null;
	public Validator validator = null;
	public String maven_home_path =null;
	
	private final Logger logger = Logger.getLogger(this.getClass());
	public BuilderImpl(){

		maven_home_path = System.getenv(BuilderConfiguration.MAVEN_HOME_TAG);
		System.out.println("maven_home_path=" + maven_home_path);
		if(maven_home_path == null){
			maven_home_path = BuilderConfiguration.maven_home_path;
		}
	}
	

	
	@Override
	public boolean build(Group target) {
		logger.debug("Going to build target: " + target);
		if(executor != null){
			try {
				executor.setMavenHome(BuilderConfiguration.maven_home_path);
				executor.process(target);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	public void setExecutor(Executor executor){
		this.executor = executor;
	}

	public void setParser(Parser parser){
		this.jsonParser = parser;
	}
	
	public void setValidator(Validator validator){
		this.validator = validator;
	}


	@Override
	public List<Group> fetchGroups(String projectRoot) {
		List<Group> groups = new LinkedList<>();
		Path p = Paths.get(projectRoot);
		
		Predicate<Path> pfilter = path-> {
			return path.getFileName().toString().matches(".*group[0-9]+");
		};
		try {
			groups = Files.list(p).filter(pfilter).map(path -> {
				return jsonParser.parse(path.toString());
			}).filter(g -> g!= null).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return groups;
	}



	@Override
	public boolean buildAll(List<Group> groups) {
		// TODO Auto-generated method stub
		return false;
	}
	

}
