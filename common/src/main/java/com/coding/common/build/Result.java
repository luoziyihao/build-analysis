package com.coding.common.build;

import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class Result {
	private Date buildTime;
	private String path;
	private boolean success = false;
	private SpecificReason specificReason;
	private PomInfo pomInfo;
}
