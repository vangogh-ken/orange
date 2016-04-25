package com.van.halley.wx.cp.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.chanjar.weixin.cp.api.WxCpMessageRouter;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;

public class WxCpConsumer implements Runnable {
	private static Logger LOG = LoggerFactory.getLogger(WxCpConsumer.class);
	private WxCpMessageRouter wxCpMessageRouter;
	private WxCpService wxCpService;
	private WxCpStore wxCpStore;
	
	
	private boolean active;
	private Thread thread;
	private ExecutorService executorService;
	/**
	 * 达到的job数之后就等待1s
	 */
	private int thresholdJobCount = 10;
	/**
	 * 线程数
	 */
	private int consumerThreadCount = 10;
	
	
	public WxCpConsumer(){
		
	}
	
	public WxCpConsumer(int consumerThreadCount, int thresholdJobCount){
		this.consumerThreadCount = consumerThreadCount;;
		this.thresholdJobCount = thresholdJobCount;
	}

	public void start() {
		active = true;
		thread = new Thread(this);
		thread.setDaemon(true);
		thread.start();
		executorService = Executors.newFixedThreadPool(consumerThreadCount);
		
		wxCpMessageRouter.setExecutorService(Executors.newFixedThreadPool(consumerThreadCount));
	}

	public void stop() {
		active = false;
		thread = null;
		executorService.shutdown();
	}

	@Override
	public void run() {
		while (active) {
			for (int i = 0; i < thresholdJobCount; i++) {
				doConsume();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				LOG.info(ex.getMessage(), ex);
			}
		}
	}

	public void doConsume() {
		try {
			WxCpXmlMessage wxCpXmlMessage = wxCpStore.take();
			executorService.execute(new Result(wxCpXmlMessage));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	class Result implements Runnable{
		private WxCpXmlMessage wxCpXmlMessage;
		
		public Result(WxCpXmlMessage wxCpXmlMessage){
			this.wxCpXmlMessage = wxCpXmlMessage;
		}
		
		@Override
		public void run() {
			try {
				//wxCpService.messageSend(wxCpMessageRouter.route(wxCpXmlMessage));
				wxCpMessageRouter.route(wxCpXmlMessage);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	//getter setter
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

	public ExecutorService getExecutorService() {
		return executorService;
	}

	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}

	public WxCpStore getWxCpStore() {
		return wxCpStore;
	}

	public void setWxCpStore(WxCpStore wxCpStore) {
		this.wxCpStore = wxCpStore;
	}

	public int getThresholdJobCount() {
		return thresholdJobCount;
	}

	public void setThresholdJobCount(int thresholdJobCount) {
		this.thresholdJobCount = thresholdJobCount;
	}

	public int getConsumerThreadCount() {
		return consumerThreadCount;
	}

	public void setConsumerThreadCount(int consumerThreadCount) {
		this.consumerThreadCount = consumerThreadCount;
	}

	public WxCpService getWxCpService() {
		return wxCpService;
	}

	public void setWxCpService(WxCpService wxCpService) {
		this.wxCpService = wxCpService;
	}

	public WxCpMessageRouter getWxCpMessageRouter() {
		return wxCpMessageRouter;
	}

	public void setWxCpMessageRouter(WxCpMessageRouter wxCpMessageRouter) {
		this.wxCpMessageRouter = wxCpMessageRouter;
	}

	
}


