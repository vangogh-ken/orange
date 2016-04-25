package com.van.halley.core.mail;

public class MailHostInfo {
	private String sendHost;
	private int sendPort;
	private String sendAddress;
	private String receiveHost;
	private int receivePort;
	private String receiveAddress;
	
	public MailHostInfo(){
		
	}
	
	public MailHostInfo(String sendHost, int sendPort, String sendAddress, String receiveHost, int receivePort, String receiveAddress){
		this.sendHost = sendHost;
		this.sendPort = sendPort;
		this.sendAddress = sendAddress;
		
		this.receiveHost = receiveHost;
		this.receivePort = receivePort;
		this.receiveAddress = receiveAddress;
	}
	public String getSendHost() {
		return sendHost;
	}
	public void setSendHost(String sendHost) {
		this.sendHost = sendHost;
	}
	public int getSendPort() {
		return sendPort;
	}
	public void setSendPort(int sendPort) {
		this.sendPort = sendPort;
	}
	public String getSendAddress() {
		return sendAddress;
	}
	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
	}
	public String getReceiveHost() {
		return receiveHost;
	}
	public void setReceiveHost(String receiveHost) {
		this.receiveHost = receiveHost;
	}
	public int getReceivePort() {
		return receivePort;
	}
	public void setReceivePort(int receivePort) {
		this.receivePort = receivePort;
	}
	public String getReceiveAddress() {
		return receiveAddress;
	}
	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}
	
	
}
