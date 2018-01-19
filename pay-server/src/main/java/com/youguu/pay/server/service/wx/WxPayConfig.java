package com.youguu.pay.server.service.wx;

import com.youguu.pay.common.api.AbstractPayConfig;

/**
 * Created by leo on 2018/1/17.
 */
public class WxPayConfig extends AbstractPayConfig {
	private String appId;
	private String mchId;
	private String baseUrl;
	private String makeOrderUrl;
	private String notifyUrl;
	private String queryOrderUrl;
	private String closeOrderUrl;
	private String refundUrl;
	private String refundQueryUrl;
	private String downloadBill;
	private Boolean sandBox;

	@Override
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@Override
	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	@Override
	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	@Override
	public String getMakeOrderUrl() {
		return makeOrderUrl;
	}

	public void setMakeOrderUrl(String makeOrderUrl) {
		this.makeOrderUrl = makeOrderUrl;
	}

	@Override
	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	@Override
	public String getQueryOrderUrl() {
		return queryOrderUrl;
	}

	public void setQueryOrderUrl(String queryOrderUrl) {
		this.queryOrderUrl = queryOrderUrl;
	}

	@Override
	public String getCloseOrderUrl() {
		return closeOrderUrl;
	}

	public void setCloseOrderUrl(String closeOrderUrl) {
		this.closeOrderUrl = closeOrderUrl;
	}

	@Override
	public String getRefundUrl() {
		return refundUrl;
	}

	public void setRefundUrl(String refundUrl) {
		this.refundUrl = refundUrl;
	}

	@Override
	public String getRefundQueryUrl() {
		return refundQueryUrl;
	}

	public void setRefundQueryUrl(String refundQueryUrl) {
		this.refundQueryUrl = refundQueryUrl;
	}

	@Override
	public String getDownloadBill() {
		return downloadBill;
	}

	public void setDownloadBill(String downloadBill) {
		this.downloadBill = downloadBill;
	}

	@Override
	public Boolean getSandBox() {
		return sandBox;
	}

	public void setSandBox(Boolean sandBox) {
		this.sandBox = sandBox;
	}
}
