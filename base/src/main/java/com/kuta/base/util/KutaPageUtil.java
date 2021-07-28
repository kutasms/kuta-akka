package com.kuta.base.util;

import java.util.ArrayList;
import java.util.List;

import com.kuta.base.collection.KutaTuple;

public class KutaPageUtil {
	/**
	 *	根据最小值，最大值和页大小获取每一页的开始和结束，均使用>= min && <= max
	 *	@param min 最小值
	 *	@param max 最大值
	 *	@param pageSize 页大小
	 *	@return 包含每一页开始和结束的List
	 * */
	public static List<KutaTuple<Long, Long>> getPages(long min, long max, long pageSize) {
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
}
