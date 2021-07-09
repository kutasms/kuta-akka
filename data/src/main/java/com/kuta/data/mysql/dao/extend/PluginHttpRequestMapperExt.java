package com.kuta.data.mysql.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kuta.data.mysql.pojo.PluginHttpRequestExample;
import com.kuta.data.mysql.pojo.extend.PluginHttpRequestExt;

public interface PluginHttpRequestMapperExt {
	List<PluginHttpRequestExt> selectExtByExample(PluginHttpRequestExample example);
	PluginHttpRequestExt selectExtByPrimaryKey(@Param("phrid")Integer phrid);
}
