package com.jdc.id3;

import java.util.List;
import java.util.Map;

import com.jdc.id3.model.DataModelImpl;
import com.jdc.id3.model.factory.NodeFactory;
import com.jdc.id3.model.factory.NodeFactoryForRoot;

public interface Id3Algorithm {

	String determine(Map<String, String> query);
	
	List<String> conditions();
	
	static Id3Algorithm algorithm(DataSet dataset) {
		DataModel model = new DataModelImpl(dataset);
		NodeFactory factory  = new NodeFactoryForRoot(model);
		return factory.generate();
	}
}
