package com.kuta.database.mysql.dao.extend;

import java.util.List;

import com.kuta.database.mysql.dao.UserRoleRelMapper;
import com.kuta.database.mysql.pojo.Role;
import com.kuta.database.mysql.pojo.User;


public interface UserRoleRelMapperExt extends UserRoleRelMapper{
	List<User> getUsers(Integer rid);
	List<Role> getRoles(Integer uid);
}
