package com.coding.build.validator;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.assertj.core.data.MapEntry;


import com.coding.build.builder.Group;
import com.coding.build.builder.Member;

public class ValidatorImpl implements Validator{

	private List<ValidationOption> validationItems = null;
	private ValidationOptionFactory factory = null;
	public ValidatorImpl(){
		validationItems = new ArrayList<>();
		
	}

	@Override
	public Map<Group, Map<Member, ValidationResult>> validate(List<? extends Group> groups) throws ValidateFailException {

		Map<Group, Map<Member, ValidationResult>> vResults = new HashMap<>();
		//order is important
//		groups.parallelStream().map(aGroup ->{
//			Map<Member, ValidationResult> memberResultMapping = new HashMap<>();
//			aGroup.getMembers().parallelStream().
//			map(member ->{
//				
//				ValidationResult result = null;
//				for(int i = 0; i< validationItems.size(); i++){
//					result = validationItems.get(i).check(member);
//					if(! result.isValid){
//						break;
//					}
//				}
//				memberResultMapping.put(member, result);
//				return null;	
//			});
//			vResults.put(aGroup, memberResultMapping);
//			return null;
//		});
//		
//		
//		groups.forEach(group ->{
//			group.getMembers().forEach(member ->{
//				
//			});
//		});
		Instant start = Instant.now();
		//LocalDateTime start  = LocalDateTime.now();
		groups.stream().filter(g->g!=null).forEach(group ->{
			System.out.println("valid group: " + group.groupId);
			Map<Member, ValidationResult> memberResultMapping = new HashMap<>();
			group.getMembers().stream().filter(m->m!=null).forEach(member ->{	
				System.out.println("valid member: " + member);
				ValidationResult result = null;
				for(int i = 0; i< validationItems.size(); i++){
					result = validationItems.get(i).check(member);
					if(! result.isValid){
						break;
					}
				}
				//System.out.println("member: " + member + " result: " + result);
				memberResultMapping.put(member, result);
			});
			vResults.put(group, memberResultMapping);
		});
		Instant end = Instant.now();
	
		Duration timeElapsed = Duration.between(start, end);
		System.out.println("time elapsed: " + timeElapsed.toMillis());
		if(vResults.size() == 0) {
			System.err.println("zero valid entries in the result map.");
		}
		return vResults;
	}
	
	

	public void setUpValidationCriteria() {
		if(factory == null) {
			System.err.println("Need to setup validation option constructor.");
			return;
		}
		try {
			//should add directory check first;
			validationItems.add(factory.constructOption(ValidationOptionFactory.ValidateType.DIRECTORY_CHECK));
			validationItems.add(factory.constructOption(ValidationOptionFactory.ValidateType.POM_CHECK));
			//add more check later...
		} catch (ValidateFailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setupValidationOptionConstructor(
			ValidationOptionFactory constructor) {
		System.out.println("setting up validation option factory...");
		factory = constructor;	
		setUpValidationCriteria();
	}

}
