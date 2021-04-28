package com.kuta.database.mysql.dao.extend;

import com.kuta.database.mysql.dao.DepartmentMapper;
import com.kuta.database.mysql.pojo.extend.DepartmentExt;

public interface DepartmentMapperExt extends DepartmentMapper{
	DepartmentExt selectExtByPrimaryKey(Integer did);
}
