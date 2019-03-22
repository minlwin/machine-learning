package com.jdc.id3.model.decision;

import java.util.ArrayList;
import java.util.List;

public interface NodeAsParent extends Node {

	List<NodeAsChild> children();

	default List<String> conditions() {
		List<String> conditions = new ArrayList<>();
		
		for(NodeAsChild child  : children()) {
			conditions.addAll(child.conditions());
		}
		
		return conditions;
	}
}
