package com.youguu.pay.server.service.wx;

import com.alibaba.fastjson.JSONObject;
import com.youguu.pay.common.api.AbstractPayService;
import com.youguu.pay.common.api.PayConfig;
import com.youguu.pay.common.bean.PayOrder;
import com.youguu.pay.common.exception.PayErrorException;
import com.youguu.pay.common.response.Response;
import com.youguu.pay.server.annotation.PayChannel;
import com.youguu.pay.server.util.HttpUtils;
import com.youguu.pay.server.util.XML;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 微信App支付
 * 接口文档地址：https://pay.weixin.qq.com/wiki/doc/api/index.html
 * Created by leo on 2018/1/16.
 */
@PayChannel(code = 103)
public class WxAppPayService extends AbstractPayService {
	private PayConfig payConfig = new WxPayConfig();

	@Override
	public Response<Map<String, Object>> makeOrder(int payCode, PayOrder order) {
		Response rs = new Response();
		rs.setCode("0000");
		rs.setMsg("ok");

		Map<String, Object> parameters = getPublicParameters();
		parameters.put("body", order.getSubject());// 购买支付信息
		parameters.put("out_trade_no", order.getOutTradeNo());// 订单号
		parameters.put("spbill_create_ip", order.getSpbillCreateIp());
		parameters.put("total_fee", order.getPrice().multiply(new BigDecimal(100d)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue());// 总金额单位为分

		parameters.put("attach", order.getBody());
		parameters.put("notify_url", payConfig.getNotifyUrl());
		parameters.put("trade_type", "APP");

		this.setSign(parameters);

		String requestXML = XML.getMap2Xml(parameters);

		String content;
		if (payConfig.getSandBox()) {
			content = HttpUtils.post(payConfig.getBaseUrl() + "sandbox/" + payConfig.getMakeOrderUrl(), requestXML);
		} else {
			content = HttpUtils.post(payConfig.getBaseUrl() + payConfig.getMakeOrderUrl(), requestXML);
		}

		JSONObject result = XML.toJSONObject(content);//XML TO JSON

		SortedMap<String, Object> params = new TreeMap<>();
		params.put("partnerid", payConfig.getMchId());
		params.put("appid", payConfig.getAppId());
		params.put("prepayid", result.get("prepay_id"));
		params.put("timestamp", System.currentTimeMillis() / 1000);
		params.put("noncestr", result.get("nonce_str"));
		params.put("package", "Sign=WXPay");
		this.setSign(params);

		rs.setT(params);

		return rs;
	}

	@Override
	public Response<Map<String, Object>> notify(int payCode, Map parameterMap, InputStream is) {
		Response rs = new Response();
		rs.setCode("0000");
		rs.setMsg("ok");

		TreeMap<String, Object> map = new TreeMap<String, Object>();
		try {
			Map<String, Object> resultMap = XML.inputStream2Map(is, map);
			rs.setT(resultMap);
		} catch (IOException e) {
			throw new PayErrorException("IOException", e.getMessage());
		}
		return rs;
	}

	@Override
	public boolean notifyVerify(int payCode, Map params) {
		//TODO 异步通知结果校验
		return true;
	}

	@Override
	public Response<String> queryOrder(int payCode, String tradeNo, String outTradeNo) {
		Response rs = new Response();
		rs.setCode("0000");
		rs.setMsg("ok");

		Map<String, Object> parameters = getPublicParameters();
		if (null != tradeNo) {
			parameters.put("transaction_id", tradeNo);
		} else {
			parameters.put("out_trade_no", outTradeNo);
		}

		this.setSign(parameters);

		String requestXML = XML.getMap2Xml(parameters);

		String content;
		if (payConfig.getSandBox()) {
			content = HttpUtils.post(payConfig.getBaseUrl() + "sandbox/" + payConfig.getQueryOrderUrl(), requestXML);
		} else {
			content = HttpUtils.post(payConfig.getBaseUrl() + payConfig.getQueryOrderUrl(), requestXML);
		}

		rs.setT(content);
		return rs;
	}

	@Override
	public Response closeOrder(int payCode, String tradeNo, String outTradeNo) {
		Response rs = new Response();
		rs.setCode("0000");
		rs.setMsg("ok");

		Map<String, Object> parameters = getPublicParameters();
		parameters.put("out_trade_no", outTradeNo);

		this.setSign(parameters);

		String requestXML = XML.getMap2Xml(parameters);

		String content;
		if (payConfig.getSandBox()) {
			content = HttpUtils.post(payConfig.getBaseUrl() + "sandbox/" + payConfig.getCloseOrderUrl(), requestXML);
		} else {
			content = HttpUtils.post(payConfig.getBaseUrl() + payConfig.getCloseOrderUrl(), requestXML);
		}

		rs.setT(content);
		return rs;
	}

	@Override
	public Response<String> refund(int payCode, String tradeNo, String outTradeNo, BigDecimal refundAmount, BigDecimal
			totalAmount) {
		Response rs = new Response();
		rs.setCode("0000");
		rs.setMsg("ok");

		Map<String, Object> parameters = getPublicParameters();
		if (null != tradeNo) {
			parameters.put("transaction_id", tradeNo);
			parameters.put("out_refund_no", tradeNo);
		} else {
			parameters.put("out_trade_no", outTradeNo);
			parameters.put("out_refund_no", outTradeNo);//商户系统内部的退款单号，商户系统内部唯一
		}
		parameters.put("total_fee", totalAmount.multiply(new BigDecimal(100d)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue());//订单总金额，单位为分
		parameters.put("refund_fee", refundAmount.multiply(new BigDecimal(100d)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue());//退款总金额，单位为分

		this.setSign(parameters);

		String requestXML = XML.getMap2Xml(parameters);

		String content;
		if (payConfig.getSandBox()) {
			content = HttpUtils.post(payConfig.getBaseUrl() + "sandbox/" + payConfig.getRefundUrl(), requestXML);
		} else {
			content = HttpUtils.post(payConfig.getBaseUrl() + payConfig.getRefundUrl(), requestXML);
		}

		rs.setT(content);
		return rs;
	}

	@Override
	public Response<String> refundQuery(int payCode, String tradeNo, String outTradeNo) {
		Response rs = new Response();
		rs.setCode("0000");
		rs.setMsg("ok");

		Map<String, Object> parameters = getPublicParameters();
		if (null != tradeNo) {
			parameters.put("transaction_id", tradeNo);
		} else {
			parameters.put("out_trade_no", outTradeNo);
		}

		this.setSign(parameters);

		String requestXML = XML.getMap2Xml(parameters);

		String content;
		if (payConfig.getSandBox()) {
			content = HttpUtils.post(payConfig.getBaseUrl() + "sandbox/" + payConfig.getRefundQueryUrl(), requestXML);
		} else {
			content = HttpUtils.post(payConfig.getBaseUrl() + payConfig.getRefundQueryUrl(), requestXML);
		}

		rs.setT(content);
		return rs;
	}

	/**
	 * @param payCode
	 * @param billDate 下载对账单的日期，格式：20140603
	 * @param billType ALL，返回当日所有订单信息，默认值；SUCCESS，返回当日成功支付的订单；REFUND，返回当日退款订单；RECHARGE_REFUND，返回当日充值退款订单（相比其他对账单多一栏“返还手续费”）;
	 *
	 * @return
	 */
	@Override
	public Response downloadBill(int payCode, Date billDate, String billType) {
		Response rs = new Response();
		rs.setCode("0000");
		rs.setMsg("ok");

		Map<String, Object> parameters = getPublicParameters();
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		parameters.put("bill_date", dft.format(billDate));
		parameters.put("bill_type", billType);

		this.setSign(parameters);

		String requestXML = XML.getMap2Xml(parameters);

		String content;
		if (payConfig.getSandBox()) {
			content = HttpUtils.post(payConfig.getBaseUrl() + "sandbox/" + payConfig.getRefundQueryUrl(), requestXML);
		} else {
			content = HttpUtils.post(payConfig.getBaseUrl() + payConfig.getRefundQueryUrl(), requestXML);
		}

		rs.setT(content);
		return rs;
	}

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

	/**
	 * 获取公共参数
	 * @return
	 */
	private Map<String, Object> getPublicParameters() {
		Map<String, Object> parameters = new TreeMap<>();
		parameters.put("appid", payConfig.getAppId());
		parameters.put("mch_id", payConfig.getMchId());
		parameters.put("nonce_str", UUID.randomUUID().toString().replace("-", ""));
		return parameters;

	}

	private Map<String, Object> setSign(Map<String, Object> parameters) {
		//TODO 签名

		return parameters;
	}
}
