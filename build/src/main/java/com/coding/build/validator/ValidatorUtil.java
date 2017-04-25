package com.coding.build.validator;

import java.util.Map;

import org.apache.log4j.Logger;

import com.coding.build.builder.Group;
import com.coding.build.builder.Member;

public class ValidatorUtil {
	//private static final Logger logger = Logger.getRootLogger();
	public static void dumpValidationMap(Map<Group, Map<Member, ValidationResult>> map){
		map.forEach((group, memberMap) ->{
			System.out.println("Group Id: " + group.groupId);
			memberMap.forEach((member, result) ->{
				System.out.println("Member: " + member);
				System.out.println("Result: " + result);
			});
		});
	}
}
