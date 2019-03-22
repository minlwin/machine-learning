package com.jdc.id3;

import static java.math.BigDecimal.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

public interface DataModel {

	String next(Map<String, String> query);
	List<String> values(Map<String, String> query, String column);
	Long find(Map<String, String> query, String decision);

	default BigDecimal entropy(Long yes, Long no) {
		
		try {
			
			BigDecimal propYes = prop(yes, yes + no);
			BigDecimal propNo = prop(no, yes + no);
			
			return ZERO.subtract(propYes.multiply(log2(propYes))).subtract(propNo.multiply(log2(propNo)));
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ZERO;
	}
	
	default BigDecimal prop(Long target, Long total) {
		
		try {
			
			return valueOf(target).divide(valueOf(total), 4, 
					RoundingMode.HALF_EVEN);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ZERO;
	}
	
	default BigDecimal log2(BigDecimal data) {
		
		try {
			return valueOf(Math.log(data.doubleValue()))
					.divide(valueOf(Math.log(2)), 4, RoundingMode.HALF_EVEN);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ZERO;
	}
	
	default BigDecimal gain(BigDecimal base, List<BigDecimal> entropies, List<BigDecimal> props) {
		
		try {
			
			BigDecimal target = ZERO;
			
			for (int i = 0; i < props.size(); i++) {
				target = target.add(entropies.get(i).multiply(props.get(i)));
			}
			
			return base.subtract(target);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ZERO;
	}
}
