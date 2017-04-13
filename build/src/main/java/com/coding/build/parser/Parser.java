package com.coding.build.parser;

import java.util.List;

import com.coding.build.builder.Group;


public interface Parser {
	/**
	 * 
	 * @param path the directory path contains the configuration files, ie, group17.json, group18.json
	 * @return List of Group object which would be used by builder 
	 * @throws ParserFailException
	 */
	public List<Group> parse(String path) throws ParserFailException;
}
