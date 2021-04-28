package com.kuta.database.mysql.pojo.extend;

import java.util.List;

import com.kuta.database.mysql.pojo.PluginHttpHeader;
import com.kuta.database.mysql.pojo.PluginHttpRequest;

public class PluginHttpRequestExt extends PluginHttpRequest{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2517748620070981377L;
	public PluginHttpRequestExt() {
		super();
	}
	
	public PluginHttpRequestExt(Long phrid, String key, Integer poid, String url, String method) {
		super(phrid, key, poid, url, method);
	}
	
	public List<PluginHttpHeader> getHeaders() {
		return headers;
	}

	public void setHeaders(List<PluginHttpHeader> headers) {
		this.headers = headers;
	}

	private List<PluginHttpHeader> headers;
}
