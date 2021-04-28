package com.kuta.database.mysql.dao.extend;

import java.util.List;

import com.kuta.database.mysql.pojo.UserExample;
import com.kuta.database.mysql.pojo.extend.UserExt;


public interface UserMapperExt {
	List<UserExt> selectExtByExample(UserExample example);
	UserExt selectExtByPrimaryKey(Integer uid);
}
