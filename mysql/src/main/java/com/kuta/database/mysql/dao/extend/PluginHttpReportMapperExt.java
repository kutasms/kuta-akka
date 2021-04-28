package com.kuta.database.mysql.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kuta.database.mysql.pojo.PluginHttpReportExample;
import com.kuta.database.mysql.pojo.extend.PluginHttpReportExt;

public interface PluginHttpReportMapperExt {
	List<PluginHttpReportExt> selectExtByExample(PluginHttpReportExample example);
	PluginHttpReportExt selectExtByPrimaryKey(@Param("phrid")Integer phrid);
}
