package com.kuta.database.mysql.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kuta.database.mysql.pojo.PluginHttpHeader;

public interface PluginHttpHeaderMapperExt {
	List<PluginHttpHeader> getHeaders(@Param("phrid")Long phrid, @Param("type")byte type);
}
