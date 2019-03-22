package com.jdc.id3.model.decision;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class NodeForLeaf implements NodeAsChild{
	
	private String column;
	private String value;
	private String result;
	private NodeAsParent parent;
	
	public NodeForLeaf(String column, String value, String result, NodeAsParent parent) {
		super();
		this.column = column;
		this.value = value;
		this.result = result;
		this.parent = parent;
		this.parent.children().add(this);
	}

	@Override
	public List<String> conditions() {
		StringBuffer sb = new StringBuffer(condition());
		
		NodeAsParent p = parent;
		
		while(null != p) {
			if(p instanceof NodeForBranch) {
				NodeForBranch child = (NodeForBranch) p;
				sb.insert(0, child.condition());
				p = child.parent();
			} else {
				break;
			}
		}
		
		return Arrays.asList(sb.toString());
	}

	@Override
	public String determine(Map<String, String> query) {

		if(null != query.get(column) && query.get(column).equals(value)) {
			return result;
		}
		
		return NO_DATA;
	}

	@Override
	public NodeAsParent parent() {
		return parent;
	}

	@Override
	public String condition() {
		
		NodeForBranch branch = (NodeForBranch) parent;
		
		if(branch.sameColumn(column)) {
			return String.format(" THEN %s", result);
		}
		
		return String.format(" AND %s = %s THEN %s", column, value, result);
	}

}
