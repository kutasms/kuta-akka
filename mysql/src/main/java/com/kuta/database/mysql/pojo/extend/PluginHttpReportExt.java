package com.kuta.database.mysql.pojo.extend;

import java.util.List;

import com.kuta.database.mysql.pojo.PluginHttpHeader;
import com.kuta.database.mysql.pojo.PluginHttpReport;

public class PluginHttpReportExt extends PluginHttpReport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 413031502272529375L;
	public PluginHttpReportExt() {
		super();
	}
	public PluginHttpReportExt(Long phrid, String url, String key, String name, String method, Integer poid) {
		super(phrid, url, key, name, method, poid);
	}
	public List<PluginHttpHeader> getHeaders() {
		return headers;
	}
	public void setHeaders(List<PluginHttpHeader> headers) {
		this.headers = headers;
	}
	private List<PluginHttpHeader> headers;
}
