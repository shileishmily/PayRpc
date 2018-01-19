package com.youguu.pay.server;

import com.youguu.core.logging.Log;
import com.youguu.core.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by leo on 2018/1/16.
 */
public class Bootstrap {

	private static final Log logger = LogFactory.getLog(Bootstrap.class);

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] {"spring/dubbo-pay-provider.xml"});
		context.start();
	}
}
