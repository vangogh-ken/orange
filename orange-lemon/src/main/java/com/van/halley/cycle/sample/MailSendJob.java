package com.van.halley.cycle.sample;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.van.halley.core.mail.MailInfoBuilder;
import com.van.halley.core.websocket.handler.WebsocketMessageHandler;
import com.van.halley.cycle.core.CycleJob;
import com.van.halley.cycle.core.CycleJobStatus;
import com.van.service.QuartzJobService;

@Configurable(preConstruction=true, dependencyCheck=true, autowire=Autowire.BY_TYPE)
public class MailSendJob extends CycleJob {
	private static Logger LOG = LoggerFactory.getLogger(MailSendJob.class);
	
	@Autowired
	private MailInfoBuilder mailInfoBuilder;
	
	private MailDTO mail;
	
	@Override
	public CycleJobStatus execute() {
		return CycleJobStatus.SUCCESS;
	}
	
}
