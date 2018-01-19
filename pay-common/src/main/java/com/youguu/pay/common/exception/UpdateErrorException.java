package com.youguu.pay.common.exception;

/**
 * Created by leo on 2018/1/16.
 */
public class UpdateErrorException extends RuntimeException {
	private String code;

	public UpdateErrorException(String code, String msg) {
		super(msg);
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
