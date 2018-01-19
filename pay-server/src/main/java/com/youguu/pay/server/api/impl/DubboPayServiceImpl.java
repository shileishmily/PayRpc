package com.youguu.pay.server.api.impl;

import com.youguu.pay.common.api.AbstractPayService;
import com.youguu.pay.common.api.PayService;
import com.youguu.pay.common.bean.PayOrder;
import com.youguu.pay.common.response.Response;
import com.youguu.pay.server.service.PayServiceFactory;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * Created by leo on 2018/1/16.
 */
public class DubboPayServiceImpl extends AbstractPayService {
	@Override
	public Response makeOrder(int payCode, PayOrder order) {
		PayService service = PayServiceFactory.getInstance().getPayService(payCode);
		return service.makeOrder(payCode, order);
	}

	@Override
	public Response notify(int payCode, Map parameterMap, InputStream is) {
		PayService service = PayServiceFactory.getInstance().getPayService(payCode);
		return service.notify(payCode, parameterMap, is);
	}

	@Override
	public boolean notifyVerify(int payCode, Map params) {
		PayService service = PayServiceFactory.getInstance().getPayService(payCode);
		return service.notifyVerify(payCode, params);
	}

	@Override
	public Response queryOrder(int payCode, String tradeNo, String outTradeNo) {
		PayService service = PayServiceFactory.getInstance().getPayService(payCode);
		return service.queryOrder(payCode, tradeNo, outTradeNo);
	}

	@Override
	public Response closeOrder(int payCode, String tradeNo, String outTradeNo) {
		PayService service = PayServiceFactory.getInstance().getPayService(payCode);
		return service.closeOrder(payCode, tradeNo, outTradeNo);
	}

	@Override
	public Response refund(int payCode, String tradeNo, String outTradeNo, BigDecimal refundAmount, BigDecimal
			totalAmount) {
		PayService service = PayServiceFactory.getInstance().getPayService(payCode);
		return service.refund(payCode, tradeNo, outTradeNo, refundAmount, totalAmount);
	}

	@Override
	public Response refundQuery(int payCode, String tradeNo, String outTradeNo) {
		PayService service = PayServiceFactory.getInstance().getPayService(payCode);
		return service.refundQuery(payCode, tradeNo, outTradeNo);
	}

	@Override
	public Response downloadBill(int payCode, Date billDate, String billType) {
		PayService service = PayServiceFactory.getInstance().getPayService(payCode);
		return service.downloadBill(payCode, billDate, billType);
	}
}
