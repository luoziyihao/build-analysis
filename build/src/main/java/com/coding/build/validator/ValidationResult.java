package com.coding.build.validator;

import com.coding.common.build.SpecificReason;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class ValidationResult {

	boolean isValid = true;
	SpecificReason reason = SpecificReason.SUCCESS;
	
	public ValidationResult(){
	}
	
	public ValidationResult(boolean isValid, SpecificReason reason){
		this.isValid = isValid;
		this.reason = reason;
	}
}
