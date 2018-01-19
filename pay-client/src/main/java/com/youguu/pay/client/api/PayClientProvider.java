package com.youguu.pay.client.api;

import com.youguu.pay.common.api.PayService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 支付Client，对Rest接口提供服务
 */
public class PayClientProvider {

	private static PayService payService;
	private static final ClassPathXmlApplicationContext context;

	static {
		context = new ClassPathXmlApplicationContext(new
				String[]{"classpath:spring/dubbo-pay-client.xml"});
		context.start();
}

	public static PayService getPayService() {
		if (payService == null) {
			synchronized (PayClientProvider.class) {
				if (payService == null) {
					payService = context.getBean("payService", PayService.class);
				}
			}
		}
		return payService;
	}
}
