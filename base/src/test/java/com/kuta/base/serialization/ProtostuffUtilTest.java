package com.kuta.base.serialization;

import java.util.ArrayList;
import java.util.List;

import com.kuta.base.collection.KutaTuple;
import com.kuta.base.util.KutaConsoleUtil;

import junit.framework.TestCase;

public class ProtostuffUtilTest extends TestCase {
	
	private List<KutaTuple<Long, Long>> getPages(long min, long max, long pageSize) {
		List<KutaTuple<Long, Long>> pages = new ArrayList<KutaTuple<Long,Long>>();
		if(max - min > pageSize) {
			for (long i = min; i <= max; i += pageSize) {
				if(i + pageSize <= max) {
					pages.add(new KutaTuple<Long, Long>(i==min? min : i+1, i + pageSize));
				}
				else {
					pages.add(new KutaTuple<Long, Long>(i==min? min : i+1, max));
				}
			}
		} else {
			pages.add(new KutaTuple<Long, Long>(min, max));
		}
		return pages;
	}
	
	public void testMapOrList(){
		 List<KutaTuple<Long, Long>> pages = getPages(50000,259200000, 2000000);
		 KutaConsoleUtil.printObj(pages, true);
//		System.out.println(address.toLowerCase());
	}
}
