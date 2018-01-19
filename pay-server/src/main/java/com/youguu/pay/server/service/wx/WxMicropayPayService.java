package com.youguu.pay.server.service.wx;

import com.youguu.pay.common.api.AbstractPayService;
import com.youguu.pay.common.api.PayConfig;
import com.youguu.pay.server.annotation.PayChannel;

/**
 * 微信刷卡支付
 * Created by leo on 2018/1/16.
 */
@PayChannel(code = 104)
public class WxMicropayPayService extends AbstractPayService {
	private PayConfig payConfig = new WxPayConfig();

	@Override
	public PayConfig getPayConfig() {
		return payConfig;
	}

	@Override
	public void build() {

	}

	public void setPayConfig(PayConfig payConfig) {
		this.payConfig = payConfig;
	}
}
