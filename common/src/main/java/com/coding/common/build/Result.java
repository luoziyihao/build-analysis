package com.coding.common.build;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class Result {
	private Date buildTime;
	private String path;
	private boolean success = false;
	private SpecificReason specificReason;
	private String description = "No Additional Description";
	private List<PomInfo> pomInfos = new ArrayList<>();
}
