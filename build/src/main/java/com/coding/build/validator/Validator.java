package com.coding.build.validator;

import java.util.List;
import java.util.Map;

import com.coding.build.builder.Group;
import com.coding.build.builder.Member;

public interface Validator {
	public Map<Group, Map<Member, ValidationResult>> validate(List<? extends Group> target) throws ValidateFailException;
	public void setupValidationOptionConstructor(ValidationOptionFactory constructor);
	
}
