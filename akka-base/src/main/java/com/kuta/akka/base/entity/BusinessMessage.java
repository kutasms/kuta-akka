package com.kuta.akka.base.entity;

import com.kuta.akka.base.serialization.KutaSerialMessage;
import com.kuta.base.collection.KutaHashMap;

public class BusinessMessage extends KutaSerialMessage {
	
	private KutaHashMap<String, Object> map;

	
	public KutaHashMap<String, Object> getMap() {
		return map;
	}

	public void setMap(KutaHashMap<String, Object> map) {
		this.map = map;
	}
	
}
