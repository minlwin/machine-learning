package com.jdc.id3.model.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jdc.id3.DataModel;
import com.jdc.id3.model.decision.Node;
import com.jdc.id3.model.decision.NodeForRoot;

public class NodeFactoryForRoot implements NodeFactoryAsParent {

	private NodeForRoot node;

	public static DataModel model;
	private List<NodeFactoryAsChild> children;

	public NodeFactoryForRoot(DataModel model) {
		super();
		NodeFactoryForRoot.model = model;

		this.children = new ArrayList<>();
		this.node = new NodeForRoot();
		
		String next = model.next(new HashMap<>());
		
		List<String> values = model.values(new HashMap<>(), next);
		
		for(String value : values) {
			children.add(new NodeFactoryForBranch(next, value, this));
		}
	}

	@Override
	public Node generate() {
		return node;
	}

	@Override
	public List<NodeFactoryAsChild> children() {
		return children;
	}

}
