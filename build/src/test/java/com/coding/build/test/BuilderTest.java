package com.coding.build.test;


import java.util.Collection;

import java.io.IOException;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.junit.Before;
import org.junit.Test;

import com.coding.build.builder.BuildException;
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

import org.springframework.core.io.ClassPathResource;


@Slf4j
public class BuilderTest {


	Builder builder = null;
	@Before
	public void setUp() throws Exception {
		builder = new BuilderImpl(BuilderConfiguration.project_root);
		builder.init();
	}
	
//	@Test
//	public void testBuild(){
//		List<Group> availableGroups =  builder.fetchGroups(BuilderConfiguration.project_root);
//		availableGroups.forEach(x->{System.out.println("available groups: " + x);});
//		ParserUtil.dumpParsingFaultyMap(parser.getFaultyGroups());
//		Map<Group, Map<Member, ValidationResult>> map = null;
//		try {
//			map = validator.validate(availableGroups);
//		} catch (ValidateFailException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch(Exception e){
//			e.printStackTrace();
//		}
//		
//		//ValidatorUtil.dumpValidationMap(map);
//		map.forEach((group, memberMap)->{
//			//System.out.println("about to execute validation");
//			executor.process(group);
//		});
//	}
	
	@Test
	public void testBuild(){
		try {
			builder.buildAll();
			builder.getResult().dump();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
}
