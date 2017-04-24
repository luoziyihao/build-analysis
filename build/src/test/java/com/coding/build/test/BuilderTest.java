package com.coding.build.test;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import com.coding.build.builder.Builder;
import com.coding.build.builder.BuilderImpl;
import com.coding.build.builder.Group;
import com.coding.build.builder.Member;
import com.coding.build.executor.Executor;
import com.coding.build.executor.ExecutorImpl;
import org.springframework.core.io.ClassPathResource;

@Slf4j
public class BuilderTest {

	
	
	@Test
	public void testBuilder() throws IOException {
		Map<String, String> propertyMapping =  System.getenv();
		System.out.println("dumpping env mapping: ");
		dumpMap(propertyMapping);
		
		Builder bdr = new BuilderImpl();
		Executor executor = new ExecutorImpl();
		bdr.setExecutor(executor);

		Member testMember = new Member();
		testMember.buildPath = new ClassPathResource("mock/test-project/pom.xml").getFile().getAbsolutePath();
		testMember.id = "1";
		Group group = new Group("group17");
		
		bdr.build(group);
	}
	
	public void dumpMap(Map<String, String> map){
		Iterator<String> keys = map.keySet().iterator();
		while(keys.hasNext()){
			String key = keys.next();
			String val = map.get(key);
			System.out.println("env key: " + key + " value: " + val);
		}
	}
}
