package com.coding.build.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.coding.build.builder.Group;
import com.coding.build.builder.Member;
import com.coding.build.parser.Parser;
import com.coding.build.parser.ParserFailException;
import com.coding.build.parser.ParserJsonImpl;
import com.coding.build.validator.ValidateFailException;
import com.coding.build.validator.ValidationOptionFactory;
import com.coding.build.validator.ValidationOptionFactoryImpl;
import com.coding.build.validator.ValidationResult;
import com.coding.build.validator.Validator;
import com.coding.build.validator.ValidatorImpl;

public class ValidatorTest {
	List<Group> groups = null;
	Validator validator = null;
	@Before
	public void setUp() throws Exception {
		
		Parser parser = new ParserJsonImpl();
		groups = new ArrayList<Group>();
		String targetDir = "/Users/erlisuo/Documents/workspace/CodeRising_2017/build-analysis/build/src/main/resources/project/config";
		groups.add(parser.parse(targetDir));
		validator = new ValidatorImpl();
		ValidationOptionFactory factory = new ValidationOptionFactoryImpl();
		validator.setupValidationOptionConstructor(factory);
	}

	@Test
	public void testValidate() {
		if(groups == null){
			System.out.println("Groups are NOT valid");
			return;
		}
		System.out.println("number of groups: " + groups.size());
		groups.stream().forEach(System.out::println);
		List<String> paths = groups.stream().map(x-> x.groupId).collect(Collectors.toList());
		paths.forEach(System.out::println);
		
		try {
			Map<Group, Map<Member, ValidationResult>> resultMap = validator.validate(groups);
		} catch (ValidateFailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	


}
