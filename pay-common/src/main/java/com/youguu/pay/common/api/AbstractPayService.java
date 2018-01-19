package com.youguu.pay.common.api;

import com.youguu.pay.common.bean.PayOrder;
import com.youguu.pay.common.response.Response;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * Created by leo on 2018/1/16.
 */
public abstract class AbstractPayService implements PayService {

	@Override
	public void setPayConfig(PayConfig payConfig) {

	}

	@Override
	public void build() {

	}

	@Override
	public PayConfig getPayConfig() {
		return null;
	}

	@Override
	public Response makeOrder(int payCode, PayOrder order) {
		return null;
	}

	@Override
	public Response notify(int payCode, Map parameterMap, InputStream is) {
		return null;
	}

	@Override
	public boolean notifyVerify(int payCode, Map params) {
		return false;
	}

	@Override
	public Response queryOrder(int payCode, String tradeNo, String outTradeNo) {
		return null;
	}

	@Override
	public Response closeOrder(int payCode, String tradeNo, String outTradeNo) {
		return null;
	}

	@Override
	public Response refund(int payCode, String tradeNo, String outTradeNo, BigDecimal refundAmount, BigDecimal
			totalAmount) {
		return null;
	}

	@Override
	public Response refundQuery(int payCode, String tradeNo, String outTradeNo) {
		return null;
	}

	@Override
	public Response downloadBill(int payCode, Date billDate, String billType) {
		return null;
	}
}
