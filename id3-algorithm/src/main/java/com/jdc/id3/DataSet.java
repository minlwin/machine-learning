package com.jdc.id3;

import java.util.List;
import java.util.Map;

public interface DataSet {

	List<String> values(Map<String, String> query, String column);

	Long find(Map<String, String> query, String decision);

	List<String> columns();

	Long find(Map<String, String> baseQuery);

}
