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
public interface PayService<T> {
	/**
	 * 设置支付配置
	 * @param payConfig
	 */
	void setPayConfig(PayConfig payConfig);

	/**
	 * 获取支付配置
	 *
	 * @return
	 */
	PayConfig getPayConfig();

	void build();

	/**
	 * 统一下单接口
	 * @param order
	 * @return
	 */
	Response<T> makeOrder(int payCode, PayOrder order);

	/**
	 * 支付通知接口，通知地址由统一下单接口传给第三方支付平台
	 * 该方法负责将请求参数或者请求流转化为Map
	 * @param parameterMap
	 * @param is
	 * @return
	 */
	Response<T> notify(int payCode, Map<String, String[]> parameterMap, InputStream is);

	/**
	 * 回调校验
	 *
	 * @param params 回调回来的参数集
	 * @return 签名校验 true通过
	 */
	boolean notifyVerify(int payCode, Map<String, Object> params);

	/**
	 * 订单查询接口
	 * 通过该接口主动查询订单状态，完成下一步的业务逻辑。
	 * @param tradeNo
	 * @param outTradeNo
	 * @return
	 */
	Response<T> queryOrder(int payCode, String tradeNo, String outTradeNo);

	/**
	 * 关闭订单接口
	 * 订单支付失败需要生成新单号重新发起支付，要对原订单号调用关单，避免重复支付；
	 * 系统下单后，用户支付超时，系统退出不再受理，避免用户继续，请调用关单接口。
	 * 注意：订单生成后不能马上调用关单接口，最短调用时间间隔为5分钟。
	 * @param tradeNo
	 * @param outTradeNo
	 * @return
	 */
	Response<T> closeOrder(int payCode, String tradeNo, String outTradeNo);

	/**
	 * 申请退款接口
	 * 支付款按原路退到买家帐号上
	 * @param tradeNo
	 * @param outTradeNo
	 * @param refundAmount
	 * @param totalAmount
	 * @return
	 */
	Response<T> refund(int payCode, String tradeNo, String outTradeNo, BigDecimal refundAmount, BigDecimal totalAmount);

	/**
	 * 退款查询接口
	 * 提交退款申请后，通过调用该接口查询退款状态。退款有一定延时。
	 * @param tradeNo
	 * @param outTradeNo
	 * @return
	 */
	Response<T> refundQuery(int payCode, String tradeNo, String outTradeNo);

	/**
	 * 下载对账单
	 * 商户可以通过该接口下载历史交易清单。比如掉单、系统错误等导致商户数据和第三方支付数据不一致，通过对账单核对后可校正支付状态。
	 * @param billDate
	 * @param billType
	 * @return
	 */
	Response<T> downloadBill(int payCode, Date billDate, String billType);
}
