
package com.jdc.id3.model.decision;

import java.util.List;

import com.jdc.id3.Id3Algorithm;

public interface Node extends Id3Algorithm{
	
	List<String> conditions();

	static final String NO_DATA = "Not Found";
}
