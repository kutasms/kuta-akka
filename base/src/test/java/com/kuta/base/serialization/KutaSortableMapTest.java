package com.kuta.base.serialization;

import com.kuta.base.collection.KutaSortableMap;
import com.kuta.base.util.KutaConsoleUtil;

import junit.framework.TestCase;

public class KutaSortableMapTest extends TestCase {
	
	public void testMoveUp() {
		KutaSortableMap<Integer, String> map = new KutaSortableMap<Integer, String>();
		map.put(2, "akka");
		map.put(5,"java");
		map.put(1,"cmd");
		map.put(3,"kuta");
		map.put(4,"xkkk");
		map.moveUp(2);
		map.moveUp(4);
//		KutaConsoleUtil.printObj(map);
	}
	
	public void testMoveDown() {
		KutaSortableMap<Integer, String> map = new KutaSortableMap<Integer, String>();
		map.put(2, "akka");
		map.put(5,"java");
		map.put(1,"cmd");
		map.put(3,"kuta");
		map.put(4,"xkkk");
		map.moveDown(4);
//		map.moveDown(4);
		KutaConsoleUtil.printObj(map);
		System.out.println("ceilingKey" + map.ceilingKey(5));
		KutaConsoleUtil.printObj(map.descendingKeySet());
		System.out.println("floorKey:" + map.floorKey(5));
		System.out.println("higherKey:" + map.higherKey(5));
		System.out.println("lastKey:" + map.lastKey());
		System.out.println("lowerKey:" + map.lowerKey(5));

	}
}
