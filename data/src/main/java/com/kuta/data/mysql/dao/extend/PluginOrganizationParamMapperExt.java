package com.kuta.data.mysql.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kuta.data.mysql.pojo.PluginOrganizationParam;

public interface PluginOrganizationParamMapperExt {
	List<PluginOrganizationParam> getList(@Param("poid")Integer poid);
}
