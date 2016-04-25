package com.zen.junit.test;


public class B extends A{
	private String s;
	@Override
	public String getS() {
		return this.s;
	}

	@Override
	public void setS(String s) {
		this.s = s;
	}
	
	public void exe(){
		System.out.println(this.s);
	}
	
}