package com.zen.junit.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.van.halley.core.aspect.ReflectInvokeUtil;

public class TestGroup {
	public static void main(String[] args) {
		t1();
	}
	
	public static void t1(){
		List<E> list = new ArrayList<E>();
		list.add(new E("AAA", "BBB", 5));
		list.add(new E("AAA", "BBB", 4));
		list.add(new E("CCC", "BBB", 1));
		list.add(new E("DD", "BBB", 2));
		list.add(new E("EE", "D", 5));
		
		
		List<Map<String, Object>> items = ReflectInvokeUtil.groupObject(E.class, list, "id", "name");
		System.out.println(items);
	}
}
