package com.coding.common.build;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class PomInfo {
	private String artifactId="";
	private String groupId="";
	private String version="";
	private String packaging = "jar";
	private String name = "";
	private String modelVersion = "";
	private String path;	// pom所在的目录
}
