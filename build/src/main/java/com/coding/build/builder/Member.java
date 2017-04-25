package com.coding.build.builder;

import com.coding.common.build.PomInfo;

import lombok.Data;

public @Data class Member {
	public String buildPath;
	public String id;
	public PomInfo pom;
	
	public String toString(){
		return "ID: " + id+" Build Path: " + buildPath;
	}
}
