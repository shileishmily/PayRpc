package com.youguu.pay.server.service.ali;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.*;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.youguu.pay.common.api.AbstractPayService;
import com.youguu.pay.common.api.PayConfig;
import com.youguu.pay.common.bean.PayOrder;
import com.youguu.pay.common.response.Response;
import com.youguu.pay.server.annotation.PayChannel;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 阿里App支付
 * 接口文档地址：https://docs.open.alipay.com/api_1/
 * Created by leo on 2018/1/16.
 */
@PayChannel(code = 101)
public class AliAppPayService extends AbstractPayService {
	private PayConfig payConfig = new AliPayConfig();
	private AlipayClient alipayClient;

	@Override
	public Response<String> makeOrder(int payCode, PayOrder order) {
		Response rs = new Response();
		rs.setCode("0000");
		rs.setMsg("ok");

		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setOutTradeNo(order.getOutTradeNo());
		model.setTotalAmount(order.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		model.setSubject(order.getSubject());
		model.setBody(order.getBody());
		model.setTimeoutExpress("30m");//该笔订单允许的最晚付款时间，逾期将关闭交易。
		model.setProductCode("QUICK_MSECURITY_PAY");

		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
		request.setBizModel(model);
		request.setNotifyUrl(payConfig.getNotifyUrl());

		try {
			AlipayTradeAppPayResponse response = getAlipayClient().sdkExecute(request);

			rs.setT(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
		} catch (AlipayApiException e) {
			rs.setCode("0001");
			rs.setMsg(e.getMessage());
		}
		return rs;
	}

	@Override
	public Response<Map<String, Object>> notify(int payCode, Map parameterMap, InputStream is) {
		Response rs = new Response();
		rs.setCode("0000");
		rs.setMsg("ok");

		Map<String, Object> params = new TreeMap<>();
		for (Iterator iter = parameterMap.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[])parameterMap.get(name);
			String valueStr = "";
			for (int i = 0,len =  values.length; i < len; i++) {
				valueStr += (i == len - 1) ?  values[i] : values[i] + ",";
			}
			if (!valueStr.matches("\\w+")) {
				try {
					if (valueStr.equals(new String(valueStr.getBytes("iso8859-1"), "iso8859-1"))) {
						valueStr = new String(valueStr.getBytes("iso8859-1"), payConfig.getCharset());
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			params.put(name, valueStr);
		}
		rs.setT(params);
		return rs;
	}

	@Override
	public boolean notifyVerify(int payCode, Map params) {
		//TODO 异步通知结果校验
		return true;
	}

	@Override
	public Response<AlipayTradeQueryResponse> queryOrder(int payCode, String tradeNo, String outTradeNo) {
		Response rs = new Response();
		rs.setCode("0000");
		rs.setMsg("ok");
		AlipayTradeQueryModel model = new AlipayTradeQueryModel();
		if(null!=tradeNo){
			model.setTradeNo(tradeNo);
		} else {
			model.setOutTradeNo(outTradeNo);
		}

		AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
		request.setBizModel(model);
		try {
			AlipayTradeQueryResponse queryResponse = getAlipayClient().execute(request);
			rs.setT(queryResponse);
		} catch (AlipayApiException e) {
			rs.setCode("0001");
			rs.setMsg(e.getMessage());
		}
		return rs;
	}

	@Override
	public Response<AlipayTradeCloseResponse> closeOrder(int payCode, String tradeNo, String outTradeNo) {
		Response rs = new Response();
		rs.setCode("0000");
		rs.setMsg("ok");

		AlipayTradeCloseModel model = new AlipayTradeCloseModel();
		if(null != tradeNo){
			model.setTradeNo(tradeNo);
		} else {
			model.setOutTradeNo(outTradeNo);
		}
//		model.setOperatorId("111");//卖家端自定义的的操作员ID,可选参数

		AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
		request.setBizModel(model);

		try {
			AlipayTradeCloseResponse closeResponse = getAlipayClient().execute(request);
			rs.setT(closeResponse);
		} catch (AlipayApiException e) {
			rs.setCode("0001");
			rs.setMsg(e.getMessage());
		}
		return rs;
	}

	@Override
	public Response<AlipayTradeRefundResponse> refund(int payCode, String tradeNo, String outTradeNo, BigDecimal refundAmount, BigDecimal
			totalAmount) {
		Response rs = new Response();
		rs.setCode("0000");
		rs.setMsg("ok");

		AlipayTradeRefundModel model = new AlipayTradeRefundModel();
		if(null != tradeNo){
			model.setTradeNo(tradeNo);
		} else {
			model.setOutTradeNo(outTradeNo);
		}
		model.setRefundAmount(refundAmount.toString());//需要退款的金额，该金额不能大于订单金额,单位为元，支持两位小数

		AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
		request.setBizModel(model);

		try {
			AlipayTradeRefundResponse refundResponse = getAlipayClient().execute(request);
			rs.setT(refundResponse);
		} catch (AlipayApiException e) {
			rs.setCode("0001");
			rs.setMsg(e.getMessage());
		}
		return rs;
	}

	@Override
	public Response<AlipayTradeFastpayRefundQueryResponse> refundQuery(int payCode, String tradeNo, String outTradeNo) {
		Response rs = new Response();
		rs.setCode("0000");
		rs.setMsg("ok");

		AlipayTradeFastpayRefundQueryModel model = new AlipayTradeFastpayRefundQueryModel();
		if(null != tradeNo){
			model.setTradeNo(tradeNo);
		} else {
			model.setOutTradeNo(outTradeNo);
		}
		model.setOutRequestNo(outTradeNo);

		AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
		request.setBizModel(model);

		try {
			AlipayTradeFastpayRefundQueryResponse refundQueryResponse = getAlipayClient().execute(request);
			rs.setT(refundQueryResponse);
		} catch (AlipayApiException e) {
			rs.setCode("0001");
			rs.setMsg(e.getMessage());
		}
		return rs;
	}

	@Override
	public Response<String> downloadBill(int payCode, Date billDate, String billType) {
		Response rs = new Response();
		rs.setCode("0000");
		rs.setMsg("ok");

		AlipayDataDataserviceBillDownloadurlQueryModel model = new AlipayDataDataserviceBillDownloadurlQueryModel();
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		model.setBillDate(dft.format(billDate));//日账单格式为yyyy-MM-dd，月账单格式为yyyy-MM
		model.setBillType(billType);

		AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
		request.setBizModel(model);

		try {
			AlipayDataDataserviceBillDownloadurlQueryResponse downloadResponse = getAlipayClient().execute(request);
			rs.setT(downloadResponse.getBillDownloadUrl());//账单下载地址链接，获取连接后30秒后未下载，链接地址失效。
		} catch (AlipayApiException e) {
			rs.setCode("0001");
			rs.setMsg(e.getMessage());
		}
		return rs;
	}

	@Override
	public PayConfig getPayConfig() {
		return payConfig;
	}

	@Override
	public void build() {
		this.alipayClient = new DefaultAlipayClient(payConfig.getServerUrl(), payConfig.getAppId(), payConfig.getPrivateKey(), payConfig.getFormat(), payConfig.getCharset(), payConfig.getPublicKey(), payConfig.getSignType());
	}

	@Override
	public void setPayConfig(PayConfig payConfig) {
		this.payConfig = payConfig;
	}

	public AlipayClient getAlipayClient() {
		return alipayClient;
	}
}
