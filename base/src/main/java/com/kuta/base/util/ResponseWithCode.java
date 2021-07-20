package com.kuta.base.util;

public class ResponseWithCode {

	private String response;
	private Integer code;

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public static ResponseWithCode create(String response, Integer code) {
		ResponseWithCode rsp = new ResponseWithCode();
		rsp.response = response;
		rsp.code = code;
		return rsp;
	}

}
