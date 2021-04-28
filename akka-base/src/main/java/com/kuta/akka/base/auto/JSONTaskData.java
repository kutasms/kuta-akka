package com.kuta.akka.base.auto;

import com.alibaba.fastjson.JSONObject;

public class JSONTaskData extends PluginTaskData {
	private JSONObject json;

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}
}
