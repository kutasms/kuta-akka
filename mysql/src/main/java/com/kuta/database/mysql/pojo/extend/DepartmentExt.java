package com.kuta.database.mysql.pojo.extend;

import com.kuta.database.mysql.pojo.Department;
import com.kuta.database.mysql.pojo.Organization;

public class DepartmentExt extends Department{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5077261161769071613L;
	
	private Organization organization;

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	public DepartmentExt() {}
	public DepartmentExt(Integer did, String name, String alias, String status, Integer parentDid, Integer oid) {
        super(did, name, alias, status, parentDid, oid);
    }
}
