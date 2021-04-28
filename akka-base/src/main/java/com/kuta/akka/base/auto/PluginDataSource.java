package com.kuta.akka.base.auto;

import java.util.List;

import com.kuta.database.mysql.pojo.PluginHttpReport;
import com.kuta.database.mysql.pojo.PluginHttpRequest;
import com.kuta.database.mysql.pojo.extend.PluginOrganizationExt;

public class PluginDataSource <T extends PluginTaskData>{
	public enum Mode{	HTTP, 	Memory	}
	
	private Mode mode;
	private T data;
	private PluginOrganizationExt entity;
	private List<PluginHttpReport> httpReports;
	private List<PluginHttpRequest> httpRequests;
	public Mode getMode() {
		return mode;
	}
	public void setMode(Mode mode) {
		this.mode = mode;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public PluginOrganizationExt getEntity() {
		return entity;
	}
	public void setEntity(PluginOrganizationExt entity) {
		this.entity = entity;
	}
	public List<PluginHttpReport> getHttpReports() {
		return httpReports;
	}
	public void setHttpReports(List<PluginHttpReport> httpReports) {
		this.httpReports = httpReports;
	}
	public List<PluginHttpRequest> getHttpRequests() {
		return httpRequests;
	}
	public void setHttpRequests(List<PluginHttpRequest> httpRequests) {
		this.httpRequests = httpRequests;
	}
}
