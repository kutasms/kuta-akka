package com.kuta.data.mysql.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kuta.data.mysql.pojo.PluginHttpReportExample;
import com.kuta.data.mysql.pojo.extend.PluginHttpReportExt;

public interface PluginHttpReportMapperExt {
	List<PluginHttpReportExt> selectExtByExample(PluginHttpReportExample example);
	PluginHttpReportExt selectExtByPrimaryKey(@Param("phrid")Integer phrid);
}
