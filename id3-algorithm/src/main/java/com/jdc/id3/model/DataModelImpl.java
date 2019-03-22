package com.jdc.id3.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jdc.id3.DataModel;
import com.jdc.id3.DataSet;

public class DataModelImpl implements DataModel {

	private DataSet dataset;

	public DataModelImpl(DataSet dataset) {
		super();
		this.dataset = dataset;
	}

	@Override
	public String next(Map<String, String> baseQuery) {
		
		Map<String, BigDecimal> gains = new HashMap<>();
		List<String> columns = dataset.columns();
		columns.removeAll(baseQuery.keySet());
		
		BigDecimal base = entropy(find(baseQuery, "Yes"), find(baseQuery, "No"));
		
		for(String column : columns) {
			
			List<String> values = values(baseQuery, column);
			List<BigDecimal> props = new ArrayList<>();
			List<BigDecimal> entropies = new ArrayList<>();
			
			for(String value : values) {
				
				Map<String, String> query  = new HashMap<>(baseQuery);
				query.put(column, value);
				
				Long yes = find(query, "Yes");
				Long no = find(query, "Yes");
				
				if(yes == 0 && no == 0) {
					continue;
				}
				
				Long total = dataset.find(baseQuery);
				
				props.add(prop(yes + no, total));
				entropies.add(entropy(yes, no));
			}
			
			gains.put(column, gain(base, entropies, props));
		}
		
		return gains.entrySet().stream()
				.sorted((a,b) -> b.getValue().compareTo(a.getValue()))
				.findFirst()
				.map(a -> a.getKey()).orElse(null);
	}

	@Override
	public List<String> values(Map<String, String> query, String column) {
		return dataset.values(query, column);
	}

	@Override
	public Long find(Map<String, String> query, String decision) {
		return dataset.find(query, decision);
	}

}
