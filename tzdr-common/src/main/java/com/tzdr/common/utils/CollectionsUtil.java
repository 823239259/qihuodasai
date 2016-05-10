package com.tzdr.common.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class CollectionsUtil {

	 public static Map<String,Double> sortMap(Map<String,Double> targetMap) {
	        ArrayList<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(targetMap.entrySet());
	        
	        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {

	            @Override
	            public int compare(Entry<java.lang.String, Double> arg0,
	                    Entry<java.lang.String, Double> arg1) {
	                return arg0.getValue().compareTo(arg1.getValue());
	            }
	        });
	        Map<String,Double> newMap = new LinkedHashMap<String, Double>();
	        for (int i = 0; i < list.size(); i++) {
	            newMap.put(list.get(list.size() - 1 - i).getKey(), list.get(list.size() - 1 - i).getValue());
	        }
	        return newMap;
	    }
}
