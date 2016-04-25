package com.van.halley.wx.cp.handler.support;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Base64;

import com.van.halley.core.util.IoUtils;
import com.van.halley.util.Object2JsonUtil;

/**
 * weixin 反馈回来的是GPS坐标，需要转换至具体的地图坐标系中
 * @author anxinxx
 *
 */
public class LocationSupport {
	/**
	 * 使用百度提供的API查找位置
	 * @param x
	 * @param y
	 * @return
	 */
	public static String baiDuXY(String x, String y) {
		String url = "http://api.map.baidu.com/ag/coord/convert?from=2&to=4&x=" + x + "&y=" + y + "";
		String result = null;
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5 * 1000);
			connection.connect();
			if (connection.getResponseCode() == 200) {
				InputStream is = connection.getInputStream();
				String jsonString = IoUtils.readString(is, "UTF-8");
				LocationDTO location = Object2JsonUtil.toSerialObject(jsonString, LocationDTO.class);
				result = location(location);

			}
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String location(LocationDTO locationDTO){
		byte[] xbuff = Base64.getUrlDecoder().decode(locationDTO.getX());
		byte[] ybuff = Base64.getUrlDecoder().decode(locationDTO.getY());
		return new String(xbuff) + "|" + new String(ybuff);
	}
	
	public static void main(String[] args) {
		LocationDTO locationDTO = new LocationDTO();
		locationDTO.setX("MTAuMDA2NTI3MjQ3NDU2");
		locationDTO.setY("MTAuMDA1OTk3MjQ3NDI=");
		System.out.println(location(locationDTO));
	}
}
