package com.youguu.pay.server.config;

import org.junit.Test;

import java.io.File;
import java.util.Map;


/**
 * Created by leo on 2018/1/17.
 */
public class INIConfigurationTest {
	@Test
	public void getSection() throws Exception {
		INIConfiguration ini = new INIConfiguration();
		String path = this.getClass().getResource("/").getPath()+"properties/pay.properties";

		File file = new File(path);
		ini.read(file);

		Map<String, String>  map = ini.getSection("101");
		System.out.println(map.toString());
	}

}