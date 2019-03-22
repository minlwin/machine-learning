package com.jdc.id3.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.jdc.id3.DataSet;

public class DataSetImpl implements DataSet {

	private String decision;
	private String[] excluded;
	private String[][] dataset;

	public DataSetImpl(String decision, String[] excluded, String[][] dataset) {
		super();
		this.decision = decision;
		this.excluded = excluded;
		this.dataset = dataset;
	}

	@Override
	public List<String> values(Map<String, String> query, String column) {
		return Arrays.stream(dataset)
				.skip(1)
				.filter(filter(query))
				.map(a -> a[index(column)])
				.distinct().collect(Collectors.toList());
	}

	@Override
	public Long find(Map<String, String> query, String decision) {
		query.put(this.decision, decision);
		return find(query);
	}

	@Override
	public List<String> columns() {
		List<String> list = new ArrayList<>(Arrays.asList(dataset[0]));
		list.remove(decision);
		list.removeAll(Arrays.asList(excluded));
		return list;
	}

	@Override
	public Long find(Map<String, String> query) {
		return Arrays.stream(dataset).skip(1).filter(filter(query)).count();
	}

	private Predicate<String[]> filter(Map<String, String> query) {
		
		Predicate<String[]> filter = a -> true;
		
		for(String key : query.keySet()) {
			int index = index(key);
			filter = filter.and(a -> a[index].equals(query.get(key)));
		}
		
		return filter;
	}

	private int index(String column) {
		
		for(int i=0; i < dataset[0].length;  i++) {
			if(dataset[0][i].equals(column)) {
				return i;
			}
		}
		
		throw new IllegalArgumentException("Ivalid column Name");
	}

}
