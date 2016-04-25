package com.van.halley.cycle.sample;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.van.halley.core.websocket.handler.WebsocketMessageHandler;
import com.van.halley.cycle.core.CycleJob;
import com.van.halley.cycle.core.CycleJobStatus;
import com.van.service.QuartzJobService;

@Configurable(preConstruction=true, dependencyCheck=true, autowire=Autowire.BY_TYPE)
public class CycleJobSample extends CycleJob {
	private static Logger LOG = LoggerFactory.getLogger(CycleJobSample.class);
	private long id = System.currentTimeMillis();
	
	private QuartzJobService quartzJobService;
	@Autowired
	private WebsocketMessageHandler handler;
	@Override
	public CycleJobStatus execute() {
		handler.sendMessageToUsers("HHH");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		LOG.info(id + " : " + toString() + 
				" : " + quartzJobService.toString() + 
				" : " + handler.toString());
		return CycleJobStatus.WAITING;
	}
	public QuartzJobService getQuartzJobService() {
		return quartzJobService;
	}
	@Resource
	public void setQuartzJobService(QuartzJobService quartzJobService) {
		this.quartzJobService = quartzJobService;
	}
	
	
}
