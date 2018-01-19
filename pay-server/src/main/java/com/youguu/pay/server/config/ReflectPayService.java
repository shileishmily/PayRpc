package com.youguu.pay.server.config;

import com.youguu.pay.common.api.PayConfig;
import com.youguu.pay.common.api.PayService;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by leo on 2018/1/17.
 */
public class ReflectPayService {

	public static void invoke(PayService payService, Map<String, String> configMap) {
		PayConfig payConfig = payService.getPayConfig();
		Field[] field = payConfig.getClass().getDeclaredFields();
		try {
			for (int j = 0; j < field.length; j++) {
				String name = field[j].getName();
				String fieldValue = configMap.get(name);
				name = name.substring(0, 1).toUpperCase() + name.substring(1);
				String type = field[j].getGenericType().toString();

				if (type.equals("class java.lang.String")) {
					Method m = payConfig.getClass().getMethod("get" + name);
					String value = (String) m.invoke(payConfig); // 调用getter方法获取属性值
					if (value == null) {
						m = payConfig.getClass().getMethod("set" + name, String.class);
						m.invoke(payConfig, fieldValue);
					}
				}

				if (type.equals("class java.lang.Integer")) {
					Method m = payConfig.getClass().getMethod("get" + name);
					Integer value = (Integer) m.invoke(payConfig);
					if (value == null) {
						m = payConfig.getClass().getMethod("set" + name, Integer.class);
						m.invoke(payConfig, Integer.parseInt(fieldValue));
					}
				}

				if (type.equals("class java.lang.Boolean")) {
					Method m = payConfig.getClass().getMethod("get" + name);
					Boolean value = (Boolean) m.invoke(payConfig);
					if (value == null) {
						m = payConfig.getClass().getMethod("set" + name, Boolean.class);
						m.invoke(payConfig, Boolean.parseBoolean(fieldValue));
					}
				}
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}


}
