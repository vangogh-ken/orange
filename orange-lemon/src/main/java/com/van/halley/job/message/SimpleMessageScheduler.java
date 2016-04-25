package com.van.halley.job.message;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.van.halley.core.websocket.handler.WebsocketMessageHandler;
import com.van.halley.db.persistence.entity.OutMsgInfo;
import com.van.service.OutMsgInfoService;

@Component
public class SimpleMessageScheduler {
	@Autowired
	private WebsocketMessageHandler handler;
	@Autowired
	private OutMsgInfoService outMsgInfoService;
	
	//@Scheduled(cron="0 0/10 * * * ?")//每10分钟执行
	@Scheduled(cron="* * * * * ?")//每秒执行
	public void execute(){
		OutMsgInfo filter = new OutMsgInfo();
		filter.setStatus("未读");
		filter.setHandled("F");
		List<OutMsgInfo> outMsgInfos = outMsgInfoService.queryForList(filter);
		if(outMsgInfos != null && !outMsgInfos.isEmpty()){
			for(OutMsgInfo outMsgInfo : outMsgInfos){
				if(handler.sendMessageToUser(
						outMsgInfo.getReceiver().getId(), 
						outMsgInfo.getContent() + " 来自：" +outMsgInfo.getSender().getDisplayName())){
					outMsgInfo.setHandled("T");
					outMsgInfoService.modify(outMsgInfo);
				}
			}
		}
	}
}
