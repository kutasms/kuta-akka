package com.kuta.data.mysql.dao.extend;

import com.kuta.data.mysql.dao.DepartmentMapper;
import com.kuta.data.mysql.pojo.extend.DepartmentExt;

public interface DepartmentMapperExt extends DepartmentMapper{
	DepartmentExt selectExtByPrimaryKey(Integer did);
}
