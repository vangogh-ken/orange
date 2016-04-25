package com.zen.junit.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestList {
	public static void main(String[] args) {
		t2();
	}
	
	public static void t(){
		List<String> list = new ArrayList<String>();
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		list.add("E");
		
		List<String> list2 = new ArrayList<String>();
		list2.add("D");
		list2.add("E");
		list2.add("F");
		
		list.removeAll(list2);
		
		for(String s : list){
			System.out.println(s);
		}
	}
	
	public static void t2(){
		Map<String, String> map = new HashMap<String, String>();
		map.put(null, "A");
		System.out.println(map.get(null));
	}
}
