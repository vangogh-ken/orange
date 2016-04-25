package com.zen.junit.test;

import java.io.IOException;


public class SimpleTest {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		/**
		 * String fileName = "测试数据1_李四.docx";
		 * 
		 * System.out.println((fileName.split("\\."))[0]); String realName =
		 * (fileName
		 * .split("\\.")[0]).contains("_")?(fileName.split("\\.")[0]).split
		 * ("_")[1]:""; System.out.println(realName);
		 **/

		/*GenericConversionService conversionService = new GenericConversionService();
		conversionService.addConverter(new StringToPhoneNumberConverter());*/

		String phoneNumberStr = "010-12345678";
		//PhoneNumberModel phoneNumber = conversionService.convert(
				//phoneNumberStr, PhoneNumberModel.class);

		//Assert.assertEquals("010", phoneNumber.getAreaCode());

		String orgFileName = "测试.zip";
		String suffix = orgFileName.contains("\\.") ? "" : "."
				+ orgFileName.split("\\.")[orgFileName.split("\\.").length - 1];
		System.out.println(suffix);
		System.out.println(orgFileName.contains(".zip"));
		System.out.println(orgFileName.contains("."));
	}

}
