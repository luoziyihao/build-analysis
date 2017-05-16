package com.coding.common.build;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class PomInfo {
	private String artifactId="DemoArtifactId";
	private String groupId="DemoGroupId";
	private String version="DemoVersion";
	private String packaging = "DemoPackage";
	private String name = "DemoName";
	private String modelVersion = "DemoModelVersion";
	private String path;	// pom所在的目录
}
