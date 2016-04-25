package com.van.halley.fre.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.van.halley.db.persistence.entity.FreightOrderBox;

public class FreightCommonUtil {
	/**
	 * 根据箱封信息获取具体的箱型箱量
	 * @param freightOrderBoxs
	 * @return
	 */
	public static String getFreightOrderBoxInfo(List<FreightOrderBox> freightOrderBoxs){
		if(freightOrderBoxs == null || freightOrderBoxs.isEmpty()){
			return "";
		}
		Map<String, List<FreightOrderBox>> belongMap = new HashMap<String, List<FreightOrderBox>>();
		for(FreightOrderBox item : freightOrderBoxs){
			if(belongMap.get(item.getFreightBoxRequire().getBoxBelong()) == null){
				List<FreightOrderBox> list = new ArrayList<FreightOrderBox>();
				list.add(item);
				belongMap.put(item.getFreightBoxRequire().getBoxBelong(), list);
			}else{
				List<FreightOrderBox> list = belongMap.get(item.getFreightBoxRequire().getBoxBelong());
				list.add(item);
				belongMap.put(item.getFreightBoxRequire().getBoxBelong(), list);
			}
		}
		StringBuilder text = new StringBuilder();
		for(String belong : belongMap.keySet()){
			List<FreightOrderBox> list = belongMap.get(belong);
			Map<String, Integer> boxTypeMap = new HashMap<String, Integer>();
			for(FreightOrderBox item : list){
				if(boxTypeMap.get(item.getFreightBoxRequire().getBoxType()) == null){
					boxTypeMap.put(item.getFreightBoxRequire().getBoxType(), 1);
				}else{
					boxTypeMap.put(item.getFreightBoxRequire().getBoxType(), boxTypeMap.get(item.getFreightBoxRequire().getBoxType()) + 1);
				}
			}
			for(String boxType : boxTypeMap.keySet()){
				text.append(boxType + "*" + boxTypeMap.get(boxType) + " " + belong + ";");
			}
		}
		return text.toString();
	}
	
	/**
	 * 箱型箱量箱属分开
	 * @param freightOrderBoxs
	 * @return
	 */
	public static String[] getFreightOrderBoxSingle(List<FreightOrderBox> freightOrderBoxs){
		if(freightOrderBoxs == null || freightOrderBoxs.isEmpty()){
			return new String[]{"", "", ""};
		}
		Map<String, List<FreightOrderBox>> belongMap = new HashMap<String, List<FreightOrderBox>>();
		for(FreightOrderBox item : freightOrderBoxs){
			if(belongMap.get(item.getFreightBoxRequire().getBoxBelong()) == null){
				List<FreightOrderBox> list = new ArrayList<FreightOrderBox>();
				list.add(item);
				belongMap.put(item.getFreightBoxRequire().getBoxBelong(), list);
			}else{
				List<FreightOrderBox> list = belongMap.get(item.getFreightBoxRequire().getBoxBelong());
				list.add(item);
				belongMap.put(item.getFreightBoxRequire().getBoxBelong(), list);
			}
		}
		StringBuilder text = new StringBuilder();
		String[] orderBoxInfo = new String[3];
		StringBuilder type = new StringBuilder();
		StringBuilder count = new StringBuilder();
		StringBuilder belg = new StringBuilder();
		for(String belong : belongMap.keySet()){
			List<FreightOrderBox> list = belongMap.get(belong);
			Map<String, Integer> boxTypeMap = new HashMap<String, Integer>();
			for(FreightOrderBox item : list){
				if(boxTypeMap.get(item.getFreightBoxRequire().getBoxType()) == null){
					boxTypeMap.put(item.getFreightBoxRequire().getBoxType(), 1);
				}else{
					boxTypeMap.put(item.getFreightBoxRequire().getBoxType(), boxTypeMap.get(item.getFreightBoxRequire().getBoxType()) + 1);
				}
			}
			for(String boxType : boxTypeMap.keySet()){
				text.append(boxType + "*" + boxTypeMap.get(boxType) + " " + belong + ";");
				
				type.append(boxType + ",");
				count.append(boxTypeMap.get(boxType) + ",");
				belg.append(belong + ",");
			}
		}
		text.toString();
		
		orderBoxInfo[0] = type.deleteCharAt(type.lastIndexOf(",")).toString();
		orderBoxInfo[1] = count.deleteCharAt(count.lastIndexOf(",")).toString();
		orderBoxInfo[2] = belg.deleteCharAt(belg.lastIndexOf(",")).toString();
		
		return orderBoxInfo;
	}
}
