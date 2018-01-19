package com.youguu.pay.server.service.ali;

import com.youguu.pay.common.api.AbstractPayService;
import com.youguu.pay.common.api.PayConfig;
import com.youguu.pay.server.annotation.PayChannel;

/**
 * 阿里当面付
 * Created by leo on 2018/1/16.
 */
@PayChannel(code = 102)
public class AliF2FPayService extends AbstractPayService {

	private PayConfig payConfig = new AliPayConfig();

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
