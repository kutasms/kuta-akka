package com.kuta.data.mysql.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UserPermRelMapperExt {
	List<Integer> getPidList(@Param("uid") Integer uid);
}
