package com.kuta.database.mysql.dao.extend;

import java.util.List;

import com.kuta.database.mysql.pojo.extend.PluginScriptWithRel;

public interface PluginScriptRelMapperExt {
	List<PluginScriptWithRel> getScripts(Integer pid);
}
