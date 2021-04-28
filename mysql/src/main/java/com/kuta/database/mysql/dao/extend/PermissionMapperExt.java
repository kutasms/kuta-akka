package com.kuta.database.mysql.dao.extend;

import java.util.List;

import com.kuta.database.mysql.dao.PermissionMapper;
import com.kuta.database.mysql.pojo.Permission;


public interface PermissionMapperExt  extends PermissionMapper{
	List<Permission> getMergePermissions(Integer uid);
}
