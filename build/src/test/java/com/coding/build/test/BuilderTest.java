package com.coding.build.test;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.junit.Before;
import org.junit.Test;

import com.coding.build.builder.Builder;
import com.coding.build.builder.BuilderConfiguration;
import com.coding.build.builder.BuilderImpl;
import com.coding.build.builder.Group;
import com.coding.build.builder.Member;
import com.coding.build.executor.Executor;
import com.coding.build.executor.ExecutorImpl;
import com.coding.build.parser.Parser;
import com.coding.build.parser.ParserJsonImpl;
import com.coding.build.parser.ParserUtil;
import com.coding.build.validator.ValidateFailException;
import com.coding.build.validator.ValidationOptionFactory;
import com.coding.build.validator.ValidationOptionFactoryImpl;
import com.coding.build.validator.ValidationResult;
import com.coding.build.validator.Validator;
import com.coding.build.validator.ValidatorImpl;
import com.coding.build.validator.ValidatorUtil;
import com.coding.common.build.Result;

@Slf4j
public class BuilderTest {

	Builder builder = null;
	Parser parser = null;
	Validator validator = null;
	Executor executor = null;
	@Before
	public void setUp() throws Exception {
		builder = new BuilderImpl();
		parser = new ParserJsonImpl();
		validator = new ValidatorImpl();
		ValidationOptionFactory factory = new ValidationOptionFactoryImpl();
		validator.setupValidationOptionConstructor(factory);
		executor = new ExecutorImpl();
		
		builder.setParser(parser);
		
		builder.setValidator(validator);
		builder.setExecutor(executor);
	}
	
//	@Test
//	public void testBuilder(){
//		Map<String, String> propertyMapping =  System.getenv();
//		System.out.println("dumpping env mapping: ");
//		dumpMap(propertyMapping);
//		
//		Builder bdr = new BuilderImpl();
//		Executor executor = new ExecutorImpl();
//		bdr.setExecutor(executor);
//
//		Member testMember = new Member();
//		
//		testMember.buildPath = "/Users/erlisuo/Documents/workspace/mvn-example/mvn-example/pom.xml";
//		testMember.id = "1";
//		Group group = new Group("group17");
//		Result aResult = new Result();
//		bdr.build(group);
//	}
	
	public void dumpMap(Map<String, String> map){
		Iterator<String> keys = map.keySet().iterator();
		while(keys.hasNext()){
			String key = keys.next();
			String val = map.get(key);
			System.out.println("env key: " + key + " value: " + val);
		}
	}
	
//	@Test
//	public void testfetchGroups(){
//		
//		Collection<Group> groupCollection =  builder.fetchGroups(BuilderImpl.project_root);
//		
//		groupCollection.forEach(System.out::println);
//	}
//	
//	@Test
//	public void testValidateGroups(){
//		Collection<Group> groupCollection =  builder.fetchGroups(BuilderImpl.project_root);
//		
//		groupCollection.forEach(System.out::println);
//	}
	
	@Test
	public void testBuild(){
		List<Group> availableGroups =  builder.fetchGroups(BuilderConfiguration.project_root);
		availableGroups.forEach(System.out::println);
		ParserUtil.dumpParsingFaultyMap(parser.getFaultyGroups());
		Map<Group, Map<Member, ValidationResult>> map = null;
		try {
			map = validator.validate(availableGroups);
		} catch (ValidateFailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ValidatorUtil.dumpValidationMap(map);
		map.forEach((group, memberMap)->{
			System.out.println("about to execute validation");
			executor.process(group);
		});
		
		
	}
}
