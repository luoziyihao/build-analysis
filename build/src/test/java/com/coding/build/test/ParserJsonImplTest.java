package com.coding.build.test;

import static org.junit.Assert.*;

import java.util.List;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import com.coding.build.builder.Group;
import com.coding.build.parser.Parser;
import com.coding.build.parser.ParserFailException;
import com.coding.build.parser.ParserJsonImpl;

public class ParserJsonImplTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testParse() {
		Parser parser = new ParserJsonImpl();
		String targetDir = "/Users/erlisuo/Documents/workspace/CodeRising_2017/build-analysis/build/src/main/resources/project/config";

		Group group = parser.parse(targetDir);
		System.out.println(group);

	}

}
