package com.kuta.base.entity;

public enum ParamType {
	
	/**
	 * 任务：8f2vi890k(我在获取列表的时候就会传给你)
	 * 		Sim_Serial:9aa390bf
	 * 			wx_8k29v8f  success
	 * 			wx_9vi22ma failue  
	 * 
	 * */
	
	String(String.class, "string",1),
	Integer(Integer.class, "int",2),
	Boolean(Boolean.class, "bool",4),
	Long(Long.class, "long",8),
	Short(Short.class,"short",16),
	Byte(Byte.class,"byte",64),
	Float(Float.class,"float",256),
	Double(Double.class,"double",512);
	
	private Class<?> clazz;
	private String name;
	private Integer number;
	
	private ParamType(Class<?> clazz, String name, Integer number) {
		this.clazz = clazz;
		this.name = name;
		this.number = number;
	}
	
	public ParamType parse(String name) {
		for(int i=0;i<ParamType.values().length;i++) {
			if(ParamType.values()[i].getName().equals(name)) {
				return ParamType.values()[i];
			}
		}
		return null;
	}
	public ParamType parse(Class<?> clazz) {
		for(int i=0;i<ParamType.values().length;i++) {
			if(ParamType.values()[i].getClazz().equals(clazz)) {
				return ParamType.values()[i];
			}
		}
		return null;
	}
	public ParamType parse(Integer number) {
		for(int i=0;i<ParamType.values().length;i++) {
			if(ParamType.values()[i].getNumber().equals(number)) {
				return ParamType.values()[i];
			}
		}
		return null;
	}
	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
}
