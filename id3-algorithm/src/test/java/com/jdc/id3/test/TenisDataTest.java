package com.jdc.id3.test;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.jdc.id3.DataSet;
import com.jdc.id3.Id3Algorithm;
import com.jdc.id3.model.DataSetImpl;

class TenisDataTest {
	
	static Id3Algorithm algorithm;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		List<String> lines = Files.readAllLines(Paths.get(TenisDataTest.class.getResource("/dataset.csv").toURI()));
		
		String[][] data = new String[lines.size()][];
		
		for (int i = 0; i < data.length; i++) {
			data[i] = lines.get(i).split(",");
		}
		
		DataSet dataset = new DataSetImpl("Decision", new String[] {"Day"}, data);
		algorithm = Id3Algorithm.algorithm(dataset);
		
		List<String> conditions = algorithm.conditions();
		
		for(String cond : conditions) {
			System.out.println(cond);
		}
	}

	@Test
	void test1() {
		
		Map<String, String> query = new HashMap<>();
		query.put("Outlook", "Overcast");
		
		assertEquals("Yes", algorithm.determine(query));
	}

	@Test
	void test2() {
		
		Map<String, String> query = new HashMap<>();
		query.put("Outlook", "Rain");
		query.put("Wind", "Weak");
		
		assertEquals("Yes", algorithm.determine(query));
	}

	@Test
	void test3() {
		
		Map<String, String> query = new HashMap<>();
		query.put("Outlook", "Sunny");
		query.put("Humidity", "High");
		
		assertEquals("No", algorithm.determine(query));
	}
}
