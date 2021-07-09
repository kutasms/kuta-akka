package com.kuta.data.mysql.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kuta.data.mysql.pojo.PluginHttpHeader;

public interface PluginHttpHeaderMapperExt {
	List<PluginHttpHeader> getHeaders(@Param("phrid")Long phrid, @Param("type")byte type);
}
