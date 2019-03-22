package com.jdc.id3.model.factory;

import java.util.List;

public interface NodeFactoryAsParent extends NodeFactory{
	
	List<NodeFactoryAsChild> children();
}
