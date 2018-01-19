package com.youguu.pay.common.response;

import java.io.Serializable;

/**
 * Created by lenovo on 2017/12/1.
 */
public class Response<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private String code;
	private String msg;
	private T t;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

	@Override
	public String toString() {
		return "Response [code=" + code + ", msg=" + msg + ", t=" + t + "]";
	}


}
