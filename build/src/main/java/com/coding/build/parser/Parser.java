package com.coding.build.parser;

import java.util.Map;

import org.json.JSONException;

import com.coding.build.builder.Group;
import com.coding.common.build.SpecificReason;


public interface Parser {
	/**
	 * 
	 * @param path the directory path contains the configuration files, ie, group17.json, group18.json
	 * @return Group object which would be used by builder 
	 * @throws ParserFailException
	 */
	public Group parse(String path);
}
