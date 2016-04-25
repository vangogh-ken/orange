package com.van.halley.rep.data;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

public class BasisDataSource {
	@Autowired
	private BasisDataSourceSupport dataSourceSupport;
	
	public Map<String, Object> attendanceMakeUp(Map<String, String> params){
		String filterText = "KSRQ > '" + params.get("KSRQ") + "' AND ZZRQ < '" + params.get("ZZRQ") + "'";
		Map<String, Object> map = dataSourceSupport
				.getBasisList("c165e51d-b4de-11e4-b05f-b870f47f73d5", filterText);
		return map;
	}
	
	public Map<String, Object> attendancePersonal(Map<String, String> params){
		String filterText = "KSRQ > '" + params.get("KSRQ") 
			+ "' AND ZZRQ < '" + params.get("ZZRQ") 
			+ "' AND CURRENT_USER_ID='" + params.get("CURRENT_USER_ID") + "'";
		Map<String, Object> map = dataSourceSupport
				.getBasisList("c165e51d-b4de-11e4-b05f-b870f47f73d5", filterText);
		return map;
	}
	
	public Map<String, Object> vacation(Map<String, String> params){
		String filterText = "KSSJ > '" + params.get("KSSJ") + "' AND JSSJ < '" + params.get("JSSJ") + "'";
		Map<String, Object> map = dataSourceSupport
				.getBasisList("44ffe580-a4a3-11e4-81db-b870f47f73d5", filterText);
		return map;
	}
	
	public Map<String, Object> vacationPersonal(Map<String, String> params){
		String filterText = "KSSJ > '" + params.get("KSSJ") 
			+ "' AND JSSJ < '" + params.get("JSSJ") + "'"
			+ "' AND CURRENT_USER_ID='" + params.get("CURRENT_USER_ID") + "'";
		Map<String, Object> map = dataSourceSupport
				.getBasisList("44ffe580-a4a3-11e4-81db-b870f47f73d5", filterText);
		return map;
	}
	
	public Map<String, Object> merit(Map<String, String> params){
		String filterText = "KHNF = '" + params.get("KHNF") + "' AND KHYF = '" + params.get("KHYF") + "'";
		
		Map<String, Object> map = dataSourceSupport
				.getBasisList("615cc8e7-b141-11e4-b278-b870f47f73d5", filterText);
		return map;
	}
	
	public Map<String, Object> meritBalance(Map<String, String> params){
		String filterText = "KHNF = '" + params.get("KHNF") + "' AND KHYF = '" + params.get("KHYF") + "'";
		
		Map<String, Object> map = dataSourceSupport
				.getBasisList("1131f233-6417-11e5-8c39-b870f47f73d5", filterText);
		return map;
	}
	
	public Map<String, Object> meritSeason(Map<String, String> params){
		String filterText = "KHNF = '" + params.get("KHNF") + "' AND KHYF = '" + params.get("KHYF") + "'";
		
		Map<String, Object> map = dataSourceSupport
				.getBasisList("1a041f8e-6417-11e5-8c39-b870f47f73d5", filterText);
		return map;
	}
	
}
