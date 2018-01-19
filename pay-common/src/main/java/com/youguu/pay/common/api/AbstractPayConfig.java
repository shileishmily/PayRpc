package com.youguu.pay.common.api;

/**
 * Created by leo on 2018/1/16.
 */
public abstract class AbstractPayConfig implements PayConfig {
	@Override
	public Boolean getSandBox() {
		return null;
	}

	@Override
	public String getAppId() {
		return null;
	}

	@Override
	public String getServerUrl() {
		return null;
	}

	@Override
	public String getFormat() {
		return null;
	}

	@Override
	public String getCharset() {
		return null;
	}

	@Override
	public String getPrivateKey() {
		return null;
	}

	@Override
	public String getPublicKey() {
		return null;
	}

	@Override
	public String getSignType() {
		return null;
	}

	@Override
	public String getMchId() {
		return null;
	}

	@Override
	public String getBaseUrl() {
		return null;
	}

	@Override
	public String getMakeOrderUrl() {
		return null;
	}

	@Override
	public String getNotifyUrl() {
		return null;
	}

	@Override
	public String getQueryOrderUrl() {
		return null;
	}

	@Override
	public String getCloseOrderUrl() {
		return null;
	}

	@Override
	public String getRefundUrl() {
		return null;
	}

	@Override
	public String getRefundQueryUrl() {
		return null;
	}

	@Override
	public String getDownloadBill() {
		return null;
	}
}
