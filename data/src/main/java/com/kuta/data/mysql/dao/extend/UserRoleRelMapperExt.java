package com.kuta.data.mysql.dao.extend;

import java.util.List;

import com.kuta.data.mysql.dao.UserRoleRelMapper;
import com.kuta.data.mysql.pojo.Role;
import com.kuta.data.mysql.pojo.User;


public interface UserRoleRelMapperExt extends UserRoleRelMapper{
	List<User> getUsers(Integer rid);
	List<Role> getRoles(Integer uid);
}
