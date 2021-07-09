package com.kuta.data.mysql.dao.extend;

import java.util.List;

import com.kuta.data.mysql.dao.PermissionMapper;
import com.kuta.data.mysql.pojo.Permission;


public interface PermissionMapperExt  extends PermissionMapper{
	List<Permission> getMergePermissions(Integer uid);
}
