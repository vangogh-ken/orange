package com.van.halley.wx.cp.core;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import me.chanjar.weixin.cp.bean.WxCpMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;

public class WxCpStore {
	private BlockingQueue<WxCpXmlMessage> queue = new LinkedBlockingQueue<WxCpXmlMessage>();

	@PostConstruct
	public void start() {
	}

	@PreDestroy
	public void stop() {
	}

	public WxCpXmlMessage take() throws InterruptedException {
		return queue.take();
	}

	public void put(WxCpXmlMessage wxCpXmlMessage) {
		try {
			queue.put(wxCpXmlMessage);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public BlockingQueue<WxCpXmlMessage> getQueue() {
		return queue;
	}

	public void setQueue(BlockingQueue<WxCpXmlMessage> queue) {
		this.queue = queue;
	}
	
	
}
