package com.kuta.data.mysql.pojo.extend;

import java.io.Serializable;

import com.kuta.base.database.KutaDBEntity;

public class UserBase extends KutaDBEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1472437605078689667L;
	
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
