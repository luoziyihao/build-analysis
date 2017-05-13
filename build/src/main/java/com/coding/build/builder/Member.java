package com.coding.build.builder;

import java.util.List;

import com.coding.common.build.PomInfo;

import lombok.Data;

public @Data class Member {
	public String buildPath;
	public String id;
	public List<PomInfo> pom;
	
	public String toString(){
		return "ID: " + id+" Build Path: " + buildPath;
	}
}
