package com.coding.build.validator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.coding.build.builder.Member;
import com.coding.common.build.SpecificReason;

public class ValidationOptionFactoryImpl implements ValidationOptionFactory{
	
	public ValidationOptionFactoryImpl(){
		
	}

	
	public ValidationOption constructOption(ValidateType type) throws ValidateFailException{
		
		switch (type){
		case POM_CHECK:
			return new ValidationOptionPom();
		
		case DIRECTORY_CHECK:
			return new ValidationOptionDirectory();
			
		default:
			throw new ValidateFailException("Invalid validation option type: " + type);
		}
		
			
	}

	private class ValidationOptionPom extends ValidationOption{

		/**
		 * Prerequisite: the member passed directory validation
		 */
		@Override
		public ValidationResult check(Member m) {
			Path p = Paths.get(m.buildPath);		
			try {
				List<Path> pathes = Files.list(p).filter(path -> path.getFileName().toString().toLowerCase().equals("pom.xml")).collect(Collectors.toList());
				if(pathes.size() == 0) return new ValidationResult(false, SpecificReason.MAVEN_POM_NOT_FOUND);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return new ValidationResult(true, SpecificReason.SUCCESS);
		}

		@Override
		public String getDescription() {
			return "Check if Maven POM exists.";
		}
	}
	
	private class ValidationOptionDirectory extends ValidationOption{

		@Override
		public ValidationResult check(Member m) {
			Path p = Paths.get(m.buildPath);
			return Files.exists(p) ? new ValidationResult() : new ValidationResult(false, SpecificReason.NO_SUCH_DIRECTORY);
			
		}

		@Override
		public String getDescription() {
			return "Check if building path exists.";
		}
	}
}
