package com.kuta.database.mysql.pojo.extend;

import java.util.Date;
import java.util.List;

import com.kuta.database.mysql.pojo.PluginOrganization;
import com.kuta.database.mysql.pojo.PluginOrganizationParam;
import com.kuta.database.mysql.pojo.PluginParamTemplate;


public class PluginOrganizationExt extends PluginOrganization {

	private List<PluginOrganizationParam> params;
	
	private List<PluginScriptWithRel> scripts;
	
	private List<PluginParamTemplate> paramTemplates;
	
	public PluginOrganizationExt() { super();}
	
	public PluginOrganizationExt(Integer poid, Integer pid, Integer oid, Date created, Boolean enable, String status) {
		super(poid, pid, oid, created, enable, status);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -8458987927966535847L;


	public List<PluginOrganizationParam> getParams() {
		return params;
	}

	public void setParams(List<PluginOrganizationParam> params) {
		this.params = params;
	}


	public List<PluginParamTemplate> getParamTemplates() {
		return paramTemplates;
	}

	public void setParamTemplates(List<PluginParamTemplate> paramTemplates) {
		this.paramTemplates = paramTemplates;
	}

	public List<PluginScriptWithRel> getScripts() {
		return scripts;
	}

	public void setScripts(List<PluginScriptWithRel> scripts) {
		this.scripts = scripts;
	}
}
