package com.kuta.akka.base.entity;

import com.kuta.akka.base.serialization.KutaSerialMessage;

/**
 * 	数据片段计算完毕
 * */
public class FragmentCalculateCompleted extends KutaSerialMessage{
	private int count = 0;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
