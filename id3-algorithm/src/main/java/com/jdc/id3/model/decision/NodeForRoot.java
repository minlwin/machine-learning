package com.jdc.id3.model.decision;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NodeForRoot implements NodeAsParent{
	
	private List<NodeAsChild> children;
	
	public NodeForRoot() {
		children = new ArrayList<>();
	}

	@Override
	public String determine(Map<String, String> query) {
		
		for(NodeAsChild child : children) {
			String result = child.determine(query);
			
			if(!result.contentEquals(NO_DATA)) {
				return result;
			}
		}
		
		return NO_DATA;
	}

	@Override
	public List<NodeAsChild> children() {
		return children;
	}

}
