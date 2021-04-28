package com.kuta.base.util;

import java.util.Arrays;

public enum Directions {
	UP("向上",0),
	DOWN("向下",1);
	
	private String name;
	private Integer val;
	
	
	private Directions(String name, Integer val) {
		this.name = name;
		this.val = val;
	}

	public static Directions parse(String name) {
		String upper = name.toUpperCase();
		for(int i=0;i<Directions.values().length;i++) {
			if(Directions.values()[i].getName().equals(upper)) {
				return Directions.values()[i];
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Integer getVal() {
		return val;
	}


	public void setVal(Integer val) {
		this.val = val;
	}


}
