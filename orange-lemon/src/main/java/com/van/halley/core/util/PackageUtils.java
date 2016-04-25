package com.van.halley.core.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class PackageUtils {
	public static List<String> getAllClassesInPackage(String packageName){
		List<String> list = new ArrayList<String>();
		Enumeration<URL> dirs = null;
		try {
			dirs = Thread.currentThread().getContextClassLoader().getResources(packageName);
			while(dirs.hasMoreElements()){
				URL url = dirs.nextElement();
				try {
					System.out.println(url.toURI());
					System.out.println(url.toString());
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
