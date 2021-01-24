package com.kuta.akka.base.entity;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;
import com.kuta.akka.base.serialization.KutaSerialMessage;

public class ForwardWebsocketRequest extends KutaSerialMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7561800225066846469L;

	private JSONObject params;

	public JSONObject getParams() {
		return params;
	}

	public void setParams(JSONObject params) {
		this.params = params;
	}
	
	public String toJSONString() {
		return JSONObject.toJSONString(this.params);
	}
}
