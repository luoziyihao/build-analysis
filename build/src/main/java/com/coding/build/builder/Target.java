package com.coding.build.builder;

public class Target {

	public String buildPath;
	
	public Target(String path){
		this.buildPath = path;
	}
	
	public String toString(){
		return buildPath;
	}
}
