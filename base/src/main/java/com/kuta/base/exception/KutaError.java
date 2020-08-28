package com.kuta.base.exception;

/**
 * KSF框架错误枚举
 * */
public enum KutaError {
	/**
	 * 未找到任何信息
	 * */
	NOTHING_FOUND(500001,"nothing found","未找到任何信息"),
	/**
	 * 参数不能为空
	 * */
	ARGUMENT_NOT_EMPTY(500002,"argument %s cannot be empty.","参数%s不能为空"),
	/**
	 * 缓存键格式化错误
	 * */
	CACHE_KEY_FORMAT_ERROR(500003,"cache format error!","缓存键格式化错误"),
	/**
	 * 文件加载失败
	 * */
	FILE_LOAD_ERROR(500004,"file load error", "文件加载失败");
	/**
	 * 简要说明
	 * */
	private String desc;
	/**
	 * 编号
	 * */
	private int code;
	/**
	 * 名称
	 * */
	private String name;
	
	/**
	 * 构造函数
	 * @param code 编号
	 * @param name 名称
	 * @param desc 简要说明
	 * */
	private KutaError(int code, String name, String desc) {
		this.setCode(code);
		this.setName(name);
		this.setDesc(desc);
	}

	/**
	 * 获取简要介绍
	 * @return 简要说明
	 * */
	public String getDesc() {
		return desc;
	}

	/**
	 * 设置简要说明
	 * @param desc 简要说明
	 * */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	/**
	 * 获取编号
	 * @return 编号
	 * */
	public int getCode() {
		return code;
	}
	/**
	 * 设置编号
	 * @param code 编号
	 * */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * 获取名称
	 * @return 名称
	 * */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * @param name 名称
	 * */
	public void setName(String name) {
		this.name = name;
	}
	
	
}
