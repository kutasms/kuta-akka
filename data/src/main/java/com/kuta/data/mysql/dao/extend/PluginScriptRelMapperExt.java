package com.kuta.data.mysql.dao.extend;

import java.util.List;

import com.kuta.data.mysql.pojo.extend.PluginScriptWithRel;

public interface PluginScriptRelMapperExt {
	List<PluginScriptWithRel> getScripts(Integer pid);
}
