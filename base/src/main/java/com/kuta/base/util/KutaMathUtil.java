package com.kuta.base.util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.kuta.base.exception.KutaIllegalArgumentException;

public class KutaMathUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(KutaMathUtil.class);
	
	private static Random random = new Random();
	
	/**
	 * Get results by specifying probability
	 * */
	public static int probability(Integer[] probArr) {
		int total = 0;
		Map<Integer, Integer> targetMap = new HashMap<Integer, Integer>();
		int value = 0;
		for(int i=0;i<probArr.length;i++) {
			targetMap.put(i, probArr[i] + value);
			total += probArr[i];
			value+=probArr[i];
		}
		if(total > 100) {
			throw new KutaIllegalArgumentException("Total probability of exceeding 100%");
		}
		int num = random.nextInt(100) + 1;
        for(int y=0;y<probArr.length;y++) {
        	if(num <= targetMap.get(y)) {
        		return y;
        	}
        }
		return -1;
	}
}
