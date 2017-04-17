package com.coding.common.build;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class PomInfo {
	private String artifactId;
	private String groupId;
	private String version;
}
