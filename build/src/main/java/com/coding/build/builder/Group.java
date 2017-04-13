package com.coding.build.builder;

import java.util.ArrayList;
import java.util.List;

public class Group {

	List<Member> members = null;
	public String groupId;
	public Group(String groupId){
		this.groupId = groupId;
		members = new ArrayList<>();
	}
	
	public void addMember(Member m){
		members.add(m);
	}
	
	public Member getMember(String id){
		for(Member m : members){
			if(m.id.equalsIgnoreCase(id)){
				return m;
			}
		}
		return null;
	}
	
	public Member getMember(int index){
		return members.get(index);
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(groupId);
		sb.append(System.lineSeparator());
		for(int i = 0 ; i<members.size(); i++){
			sb.append(members.get(i));
			sb.append(System.lineSeparator());
		}
		
		return sb.toString();
	}
}
