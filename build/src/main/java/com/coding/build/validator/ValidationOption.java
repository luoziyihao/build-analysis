package com.coding.build.validator;

import com.coding.build.builder.Member;

public abstract class ValidationOption {

	public ValidationOption(){
		//may need some initialization later... not sure yet.
	}
	
	public abstract ValidationResult check(Member m);
}
