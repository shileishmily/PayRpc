package com.youguu.pay.server.service.ali;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.youguu.pay.common.api.AbstractPayConfig;

/**
 * Created by leo on 2018/1/17.
 */
public class AliPayConfig extends AbstractPayConfig {
	private Boolean sandBox;
	private String serverUrl;
	private String appId;
	private String privateKey;
	private String format;
	private String charset;
	private String publicKey;
	private String signType;

	public AliPayConfig() {

	}

	@Override
	public Boolean getSandBox() {
		return sandBox;
	}

	public void setSandBox(Boolean sandBox) {
		this.sandBox = sandBox;
	}

	@Override
	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	@Override
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@Override
	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	@Override
	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	@Override
	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	@Override
	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	@Override
	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}
}
