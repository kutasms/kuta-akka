package com.kuta.data.mysql.pojo.extend;

import java.util.Date;
import java.util.List;

import com.kuta.data.mysql.pojo.Permission;
import com.kuta.data.mysql.pojo.Role;
import com.kuta.data.mysql.pojo.User;


public class UserExt extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6335845327992131091L;
	private DepartmentExt department;
	
	private List<Role> roles;
	private List<Permission> perms;
	
	public DepartmentExt getDepartment() {
		return department;
	}
	public void setDepartment(DepartmentExt department) {
		this.department = department;
	}
	public UserExt() {
		
	}
	public UserExt(Integer uid, String account, String nick, String name, String password, Date created, Date lastLogin, String phone, String status, Boolean isSuper, String avatar, Integer did, Date lastOnline) {
        super( uid,  account,  nick,  name,  password,  created,  lastLogin,  phone,  status,  isSuper,  avatar,  did,  lastOnline);
    }
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public List<Permission> getPerms() {
		return perms;
	}
	public void setPerms(List<Permission> perms) {
		this.perms = perms;
	}
}
