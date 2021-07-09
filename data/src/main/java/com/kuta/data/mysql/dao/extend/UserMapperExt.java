package com.kuta.data.mysql.dao.extend;

import java.util.List;

import com.kuta.data.mysql.pojo.UserExample;
import com.kuta.data.mysql.pojo.extend.UserExt;


public interface UserMapperExt {
	List<UserExt> selectExtByExample(UserExample example);
	UserExt selectExtByPrimaryKey(Integer uid);
}
