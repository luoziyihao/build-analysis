package com.coding.build.validator;


public interface ValidationOptionFactory {
	public static enum ValidateType { POM_CHECK, DIRECTORY_CHECK};
	public ValidationOption constructOption(ValidateType type) throws ValidateFailException;
}
