package com.youguu.pay.server.service;

import com.youguu.pay.common.api.PayConfig;
import com.youguu.pay.common.api.PayService;
import com.youguu.pay.server.annotation.PayChannel;
import com.youguu.pay.server.config.INIConfiguration;
import com.youguu.pay.server.config.ReflectPayService;
import com.youguu.pay.server.service.ali.AliF2FPayService;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 获取支付实现类实例
 * Created by leo on 2018/1/16.
 */
public class PayServiceFactory {

	private Map<Integer, PayService> serviceMap = new HashMap<>();
	private static PayServiceFactory instance;

	private PayServiceFactory() {
		this.init();
	}

	public static synchronized PayServiceFactory getInstance() {
		if (instance == null) {
			instance = new PayServiceFactory();
		}
		return instance;
	}


	public void init(){
		try {
			List<Class<?>> classSet = ClassUtil.getClasses("com.youguu.pay.server.service");

			for (Class<?> cls : classSet) {
				PayChannel payChannel = cls.getAnnotation(PayChannel.class);
				if (payChannel != null)
					try {
						PayService payService = (PayService) cls.newInstance();
						Map<String, String> configMap = this.getSection(payChannel.code());
						ReflectPayService.invoke(payService, configMap);
						payService.build();
						serviceMap.put(payChannel.code(), payService);
					} catch (InstantiationException | IllegalAccessException e) {
						e.printStackTrace();
					}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Map<String, String> getSection(int payCode) throws IOException {
		INIConfiguration ini = new INIConfiguration();
		String path = this.getClass().getResource("/").getPath()+"properties/pay.properties";

		File file = new File(path);
		ini.read(file);

		Map<String, String>  map = ini.getSection(String.valueOf(payCode));
		return map;
	}

	public PayService getPayService(Integer payCode) {
		return serviceMap.get(payCode);
	}

	public static void main(String[] args) {
		PayService service = PayServiceFactory.getInstance().getPayService(103);
		PayConfig payConfig = service.getPayConfig();
		System.out.println(payConfig.getAppId());
	}
}
