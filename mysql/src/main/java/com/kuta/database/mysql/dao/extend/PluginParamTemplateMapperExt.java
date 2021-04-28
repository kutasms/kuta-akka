package com.kuta.database.mysql.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kuta.database.mysql.pojo.PluginParamTemplate;


public interface PluginParamTemplateMapperExt {
	List<PluginParamTemplate> getList(@Param("pid")Integer pid);
}
