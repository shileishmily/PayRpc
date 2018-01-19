package com.youguu.pay.common.api;

/**
 * 支付配置
 * Created by leo on 2018/1/16.
 */
public interface PayConfig {
	Boolean getSandBox();
	String getAppId();

	String getServerUrl();
	String getFormat();
	String getCharset();
	String getPrivateKey();
	String getPublicKey();
	String getSignType();

	String getMchId();
	String getBaseUrl();
	String getMakeOrderUrl();
	String getNotifyUrl();
	String getQueryOrderUrl();
	String getCloseOrderUrl();
	String getRefundUrl();
	String getRefundQueryUrl();
	String getDownloadBill();

}
