package com.coding.build.parser;

import java.util.Map;

import com.coding.build.builder.Group;
import com.coding.common.build.SpecificReason;

public class ParserUtil {
	public static void dumpParsingFaultyMap(Map<Group, SpecificReason> map){
		map.forEach((group, reason) ->{
			System.out.println("Invalid group: " + group.groupId + " reason: " + reason);
		});
	}
}
