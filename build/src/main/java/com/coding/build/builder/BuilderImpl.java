package com.coding.build.builder;

import com.coding.build.executor.Executor;
import com.coding.build.executor.ExecutorImpl;
import com.coding.build.parser.Parser;
import com.coding.build.parser.ParserJsonImpl;
import com.coding.build.validator.ValidationOptionFactory;
import com.coding.build.validator.ValidationOptionFactoryImpl;
import com.coding.build.validator.Validator;
import com.coding.build.validator.ValidatorImpl;
import com.coding.common.build.BuildResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BuilderImpl implements Builder{

	private Executor executor = null;
	private Parser jsonParser = null;
	private Validator validator = null;
	
	BuildResult buildResult = null;
	
	private String maven_home_path =null;
	private String project_root = null;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	public BuilderImpl(String projectRoot){
		this.project_root = projectRoot;
		init();
		buildResult = BuildResult.getInstance();
	}
	
	public boolean build(Group target) {
		logger.debug("Going to build target: " + target);
		if(executor != null){
			try {
				executor.setMavenHome(BuilderConfiguration.maven_home_path);
				executor.process(target);
			} catch (FileNotFoundException e) {
				logger.error("build failed for group={}", target, e);
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


	public List<Group> fetchGroups(String projectRoot) {
		List<Group> groups = new LinkedList<>();
		Path p = Paths.get(projectRoot);
		
		Predicate<Path> pfilter = path-> path.getFileName().toString().matches(".*group[0-9]+");
		try {
			groups = Files.list(p)
					.filter(pfilter).map(path -> jsonParser.parse(path.toString()))
					.filter(Objects::nonNull)
					.collect(Collectors.toList());
		} catch (IOException e) {
			logger.error("fetchGroups projectRoot={}", projectRoot, e);
		}
		return groups;
	}

	@Override
	public BuildResult getResult() {
		return this.buildResult;
	}

	@Override
	public void init() {
		
		Parser parser = new ParserJsonImpl();
		Validator validator = new ValidatorImpl();
		ValidationOptionFactory factory = new ValidationOptionFactoryImpl();
		validator.setupValidationOptionConstructor(factory);
		Executor executor = new ExecutorImpl();
		
		setParser(parser);
		setValidator(validator);
		setExecutor(executor);
		
	}

	@Override
	public void buildAll() throws BuildException{
		checkTools();
		List<Group> groups = fetchGroups(this.project_root);
		groups.stream().filter(Objects::nonNull).forEach(this::build);
	}
	
	private void checkTools() throws BuildException{
		if(validator == null) throw new BuildException("Invalid building validator. Call init() first.");
		if(jsonParser == null) throw new BuildException("Invalid building parser. Call init() first.");
		if(executor == null) throw new BuildException("Invalid building executor. Call init() first.");
	}
	

}
