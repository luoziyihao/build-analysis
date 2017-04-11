package com.coding.build.test;

import java.util.Iterator;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import com.coding.build.builder.Builder;
import com.coding.build.builder.BuilderImpl;
import com.coding.build.builder.Target;
import com.coding.build.executor.Executor;
import com.coding.build.executor.ExecutorImpl;

@Slf4j
public class BuilderTest {

	
	
	@Test
	public void testBuilder(){
		Map<String, String> propertyMapping =  System.getenv();
		System.out.println("dumpping env mapping: ");
		dumpMap(propertyMapping);
		
		Builder bdr = new BuilderImpl();
		Executor executor = new ExecutorImpl();
		bdr.setExecutor(executor);
		Target target = new Target("/telos/esuo/workspace/cjserver/pom.xml");
		bdr.build(target);
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
