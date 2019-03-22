package com.jdc.id3.model.factory;

import com.jdc.id3.model.decision.Node;
import com.jdc.id3.model.decision.NodeAsParent;
import com.jdc.id3.model.decision.NodeForLeaf;

public class NodeFactoryForLeaf implements NodeFactoryAsChild{
	
	private NodeFactoryAsParent parent;
	private NodeForLeaf node;
	
	

	public NodeFactoryForLeaf(String column, String value, NodeFactoryAsParent parent, String result) {
		super();
		this.parent = parent;
		this.node = new NodeForLeaf(column, value, result, (NodeAsParent) parent.generate());
	}

	@Override
	public Node generate() {
		return node;
	}

	@Override
	public NodeFactoryAsParent parent() {
		return parent;
	}

}
