package com.kuta.data.mysql.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface RolePermRelMapperExt {
	List<Integer> getPidList(@Param("rid") Integer rid);
}
