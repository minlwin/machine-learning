package com.jdc.id3.model.factory;

import static com.jdc.id3.model.factory.NodeFactoryForRoot.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jdc.id3.model.decision.Node;
import com.jdc.id3.model.decision.NodeAsParent;
import com.jdc.id3.model.decision.NodeForBranch;

public class NodeFactoryForBranch implements NodeFactoryAsParent, NodeFactoryAsChild {

	private NodeForBranch node;
	
	private NodeFactoryAsParent parent;
	private List<NodeFactoryAsChild> children;

	public NodeFactoryForBranch(String column, String value, NodeFactoryAsParent parent) {
		super();
		this.parent = parent;
		this.children = new ArrayList<>();
		
		this.node = new NodeForBranch(column, value, (NodeAsParent) parent.generate());
		
		Map<String, String> query = new HashMap<>();
		query.put(column, value);
		
		Long yes = model.find(new HashMap<>(query), "Yes");
		Long no = model.find(new HashMap<>(query), "No");
		
		if(yes != 0 && no != 0) {
			String next = model.next(new HashMap<>(query));
			generate(next, query);
		}
		
		if(yes == 0 && no != 0) {
			children.add(new NodeFactoryForLeaf(column, value, this, "No"));
		}
		
		if(yes !=0 && no == 0) {
			children.add(new NodeFactoryForLeaf(column, value, this, "Yes"));
		}
		
	}

	private void generate(String column, Map<String, String> base) {

		List<String> values = model.values(base, column);

		for(String value : values) {
			Map<String, String> query = new HashMap<>(base);
			
			query.put(column, value);
			
			Long yes = model.find(new HashMap<>(query), "Yes");
			Long no = model.find(new HashMap<>(query), "No");
			
			if(yes == 0 && no == 0) {
				continue;
			}
			
			if(yes == 0) {
				children.add(new NodeFactoryForLeaf(column, value, this, "No"));
				continue;
			}
			
			if(no == 0) {
				children.add(new NodeFactoryForLeaf(column, value, this, "Yes"));
				continue;
			}

			children.add(new NodeFactoryForBranch(column, value, this));
		}
	}

	@Override
	public Node generate() {
		return node;
	}

	@Override
	public NodeFactoryAsParent parent() {
		return parent;
	}

	@Override
	public List<NodeFactoryAsChild> children() {
		return children;
	}

}
