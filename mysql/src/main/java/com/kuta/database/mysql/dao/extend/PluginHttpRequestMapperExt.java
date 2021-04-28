package com.kuta.database.mysql.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kuta.database.mysql.pojo.PluginHttpRequestExample;
import com.kuta.database.mysql.pojo.extend.PluginHttpRequestExt;

public interface PluginHttpRequestMapperExt {
	List<PluginHttpRequestExt> selectExtByExample(PluginHttpRequestExample example);
	PluginHttpRequestExt selectExtByPrimaryKey(@Param("phrid")Integer phrid);
}
