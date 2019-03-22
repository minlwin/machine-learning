package com.jdc.id3.model.decision;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NodeForBranch implements NodeAsChild, NodeAsParent{

	private String column;
	private String value;
	
	private NodeAsParent parent;
	private List<NodeAsChild> children;
	
	public NodeForBranch(String column, String value, NodeAsParent parent) {
		super();
		this.column = column;
		this.value = value;
		this.parent = parent;
		this.children = new ArrayList<>();
		parent.children().add(this);
	}

	@Override
	public String determine(Map<String, String> query) {
		
		if(query.get(column).equals(value)) {
			for(NodeAsChild child : children) {
				
				String result  = child.determine(query);
				
				if(!result.equals(NO_DATA)) {
					return result;
				}
			}
		}
		
		return NO_DATA;
	}
	
	public boolean sameColumn(String column) {
		return this.column.equals(column);
	}

	@Override
	public List<NodeAsChild> children() {
		return children;
	}

	@Override
	public NodeAsParent parent() {
		return parent;
	}

	@Override
	public String condition() {
		return String.format("%s%s = %s", parent instanceof NodeForRoot ? "" : " AND ", column, value);
	}
	

}
