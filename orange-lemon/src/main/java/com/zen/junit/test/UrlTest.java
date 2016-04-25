package com.zen.junit.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;


public class UrlTest {
	public static void main(String[] args) {
		t1();
	}
	
	public static void t1(){
		try {
			String s = IOUtils.toString(new URL("http://172.16.74.100/pulsar/login/doLogin?password=admin&username=root"));
			System.out.println(s);
			s = IOUtils.toString(new URL("http://172.16.74.100/pulsar/tenants/clean/alltenant?pageSize=0"));
			System.out.println(s);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static String getInputHtmlUTF8(String urlStr) {
		URL url = null;
		try {
			url = new URL(urlStr);
			HttpURLConnection httpsURLConnection = (HttpURLConnection) url.openConnection();
			httpsURLConnection.setRequestMethod("GET");
			httpsURLConnection.setConnectTimeout(5 * 1000);
			httpsURLConnection.connect();
			if (httpsURLConnection.getResponseCode() == 200) {
				// 通过输入流获取网络图片
				InputStream inputStream = httpsURLConnection.getInputStream();
				//String data = IOUtils.readLines(input, encoding).copy(input, output).(inputStream, "UTF-8");
				//inputStream.close();
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return null;

	}
}
