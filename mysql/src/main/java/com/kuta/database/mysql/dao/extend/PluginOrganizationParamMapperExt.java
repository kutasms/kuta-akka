package com.kuta.database.mysql.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kuta.database.mysql.pojo.PluginOrganizationParam;

public interface PluginOrganizationParamMapperExt {
	List<PluginOrganizationParam> getList(@Param("poid")Integer poid);
}
