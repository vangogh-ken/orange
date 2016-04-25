package com.van.halley.core.mail;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailInfoBuilder {
	private static Logger LOG = LoggerFactory.getLogger(MailInfoBuilder.class);
	private Map<String, MailHostInfo> mailMatchers = new HashMap<String, MailHostInfo>();
	
	private String suffixText;
	private String sendHostText;
	private String sendPortText;
	private String sendAddressText;
	private String receiveHostText;
	private String receivePortText;
	private String receiveAddressText;
	
	@PostConstruct
	public void build(){
		String[] suffixs = suffixText.split(",");
		String[] sendHosts = sendHostText.split(",");
		String[] sendPorts = sendPortText.split(",");
		String[] sendAddresses = sendAddressText.split(",");
		String[] receiveHosts = receiveHostText.split(",");
		String[] receivePorts = receivePortText.split(",");
		String[] receiveAddresses = receiveAddressText.split(",");
		
		for(int i=0, len=suffixs.length; i<len; i++){
			mailMatchers.put(suffixs[0], new MailHostInfo(sendHosts[i], Integer.parseInt(sendPorts[i]), sendAddresses[i], 
					receiveHosts[i], Integer.parseInt(receivePorts[i]), receiveAddresses[i]));
		}
	}
	
	public MailHostInfo match(String mail){
		for(String suffix : mailMatchers.keySet()){
			if(mail.endsWith(suffix)){
				return mailMatchers.get(suffix);
			}
		}
		LOG.info("wrong mail address");
		throw new IllegalArgumentException("wrong mail address");
	}
	
	public String getSuffixText() {
		return suffixText;
	}
	public void setSuffixText(String suffixText) {
		this.suffixText = suffixText;
	}
	public String getSendHostText() {
		return sendHostText;
	}
	public void setSendHostText(String sendHostText) {
		this.sendHostText = sendHostText;
	}
	public String getSendPortText() {
		return sendPortText;
	}
	public void setSendPortText(String sendPortText) {
		this.sendPortText = sendPortText;
	}
	public String getSendAddressText() {
		return sendAddressText;
	}
	public void setSendAddressText(String sendAddressText) {
		this.sendAddressText = sendAddressText;
	}
	public String getReceiveHostText() {
		return receiveHostText;
	}
	public void setReceiveHostText(String receiveHostText) {
		this.receiveHostText = receiveHostText;
	}
	public String getReceivePortText() {
		return receivePortText;
	}
	public void setReceivePortText(String receivePortText) {
		this.receivePortText = receivePortText;
	}
	public String getReceiveAddressText() {
		return receiveAddressText;
	}
	public void setReceiveAddressText(String receiveAddressText) {
		this.receiveAddressText = receiveAddressText;
	}
	
	

}
