package com.kuta.akka.base.entity.binary;

public class BinaryMessageHeader {
	
	/**
	 * 	头大小
	 * */
	public final int HEADER_LENGTH = 25;
	
	private Integer total;
	private Integer code;
	private short unicode;
	private short index;
	private boolean isFinal;
	private Integer sid;
	private Integer appId;
	private Integer contentLen;
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public short getUnicode() {
		return unicode;
	}
	public void setUnicode(short unicode) {
		this.unicode = unicode;
	}
	public short getIndex() {
		return index;
	}
	public void setIndex(short index) {
		this.index = index;
	}
	public boolean isFinal() {
		return isFinal;
	}
	public void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}
	public Integer getAppId() {
		return appId;
	}
	public void setAppId(Integer appId) {
		this.appId = appId;
	}
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public Integer getContentLen() {
		return total - HEADER_LENGTH;
	}
	public Integer getHeaderLen() {
		return HEADER_LENGTH;
	}

	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
}
