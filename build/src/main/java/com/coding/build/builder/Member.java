package com.coding.build.builder;

import lombok.Data;

public @Data class Member {
	public String buildPath;
	public String id;
	
	public String toString(){
		return "ID: " + id+" Build Path: " + buildPath;
	}
}
