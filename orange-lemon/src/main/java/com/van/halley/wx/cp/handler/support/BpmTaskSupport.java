package com.van.halley.wx.cp.handler.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.activiti.engine.history.HistoricTaskInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.van.halley.bpm.service.HistoricQueryService;
import com.van.halley.bpm.service.TaskOperateService;
import com.van.halley.bpm.support.ProcessDefinitionCache;
import com.van.halley.bpm.support.TaskQueryCache;
import com.van.halley.core.page.PageView;
import com.van.halley.wx.cp.core.WxCpAgent;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpMessage;

@Component
public class BpmTaskSupport {
	private static Logger LOG = LoggerFactory.getLogger(BpmTaskSupport.class);
	
	@Autowired
	private HistoricQueryService historicQueryService;
	@Autowired
	private TaskOperateService taskOperateService;
	
	public void handleTasksQuery(String sysUserId, String wxCpUserId, WxCpService wxCpService){
		StringBuilder text = new StringBuilder();
		List<HistoricTaskInstance> list = handleTasks(sysUserId);
		if(list != null && !list.isEmpty()){
			for(int i=0, size=list.size(); i<size; i++){
				HistoricTaskInstance item = list.get(i);
				content(text, item, i+1);
			}
			text.append("回复序号查看详情");
		}else{
			text.append("未查询到相关记录");
		}
		
		WxCpMessage wxCpMessage = WxCpMessage.TEXT().agentId(WxCpAgent.DEFAULT).toUser(wxCpUserId).content(text.toString()).build();
		try {
			wxCpService.messageSend(wxCpMessage);
		} catch (WxErrorException e) {
			LOG.error("待办任务发送失败： {}", e);
		}
	}
	
	public void handleTask(int number, String sysUserId, String wxCpUserId, WxCpService wxCpService){
		StringBuilder text = new StringBuilder();
		HistoricTaskInstance item = handleTask(number, sysUserId);
		if(item != null){
			content(text, item, number);
			Map<String, String> map = taskOperateService.getNextActivity(item.getId());
			String type = map.get("type");
			int count = 1;
			if("userTask".equals(type)){
				for(Entry<String, String> entry : map.entrySet()){
					if("type".equals(entry.getKey())){
						continue;
					}else{
						String[] details = entry.getValue().split("<!!>");
						text.append(details[0] + details[1] + "/r/n" + details[2] + details[3] + "/r/n");
						count ++;
					}
				}
				
				text.append("回复序号直接发送");
			}else if("endEvent".equals(type)){
				
				text.append("回复序号直接完成");
			}else if(type.contains("getway")){
				
				
				if("exclusiveGateway".equals(type)){
					text.append("回复一个序号发送");
				}else if("inclusiveGateway".equals(type)){
					text.append("回复至少一个序号发送");
				}else if("inclusiveGateway".equals(type)){
					text.append("回复全部序号发送");
				}else{
					
				}
			}
		}else{
			text.append("未查询到相关记录");
		}
		
		WxCpMessage wxCpMessage = WxCpMessage.TEXT().agentId(WxCpAgent.DEFAULT).toUser(wxCpUserId).content(text.toString()).build();
		try {
			wxCpService.messageSend(wxCpMessage);
		} catch (WxErrorException e) {
			LOG.error("待办任务发送失败： {}", e);
		}
	}
	
	public void claimTasksQuery(String sysUserId, String wxCpUserId, WxCpService wxCpService){
		StringBuilder text = new StringBuilder();
		List<HistoricTaskInstance> list = claimTasks(sysUserId);
		if(list != null && !list.isEmpty()){
			for(int i=0, size=list.size(); i<size; i++){
				HistoricTaskInstance item = list.get(i);
				content(text, item, i+1);
			}
			text.append("回复序号查看详情");
		}else{
			text.append("未查询到相关记录");
		}
		
		WxCpMessage wxCpMessage = WxCpMessage.TEXT().agentId(WxCpAgent.DEFAULT).toUser(wxCpUserId).content(text.toString()).build();
		try {
			wxCpService.messageSend(wxCpMessage);
		} catch (WxErrorException e) {
			LOG.error("待办任务发送失败： {}", e);
		}
	}
	
	public void finishedTasksQuery(String sysUserId, String wxCpUserId, WxCpService wxCpService){
		StringBuilder text = new StringBuilder();
		List<HistoricTaskInstance> list = finishedTasks(sysUserId);
		if(list != null && !list.isEmpty()){
			for(int i=0, size=list.size(); i<size; i++){
				HistoricTaskInstance item = list.get(i);
				content(text, item, i+1);
			}
			text.append("回复序号查看详情");
		}else{
			text.append("未查询到相关记录");
		}
		
		WxCpMessage wxCpMessage = WxCpMessage.TEXT().agentId(WxCpAgent.DEFAULT).toUser(wxCpUserId).content(text.toString()).build();
		try {
			wxCpService.messageSend(wxCpMessage);
		} catch (WxErrorException e) {
			LOG.error("待办任务发送失败： {}", e);
		}
	}
	
	public HistoricTaskInstance handleTask(int number, String sysUserId){
		if(number > 0){
			List<HistoricTaskInstance> list = handleTasks(sysUserId);
			if(list == null || list.isEmpty() || list.size() < number){
				return null;
			}else{
				return list.get(number - 1);
			}
		}
		return null;
	}
	
	public HistoricTaskInstance claimTask(int number, String sysUserId){
		if(number > 0){
			List<HistoricTaskInstance> list = claimTasks(sysUserId);
			if(list == null || list.isEmpty() || list.size() < number){
				return null;
			}else{
				return list.get(number - 1);
			}
		}
		return null;
	}
	
	public HistoricTaskInstance finishedTask(int number, String sysUserId){
		if(number > 0){
			List<HistoricTaskInstance> list = finishedTasks(sysUserId);
			if(list == null || list.isEmpty() || list.size() < number){
				return null;
			}else{
				return list.get(number - 1);
			}
		}
		return null;
	}
	
	/**
	 * 拼接内容
	 * @param text
	 * @param item
	 * @param number
	 * @return
	 */
	private StringBuilder content(StringBuilder text, HistoricTaskInstance item, int number){
		if(item != null){
			text.append(number + ": ");
			text.append(ProcessDefinitionCache.getProcessName(item.getProcessDefinitionId()) + "<流程> ");
			text.append(item.getName() + "<任务> ");
			text.append(TaskQueryCache.getStarterDisplayName(item.getProcessInstanceId()) + "<发起> ");
			text.append(TaskQueryCache.getLastTaskAssignee(item.getId(), item.getProcessInstanceId()) + "<发送> ");
			text.append("\r\n");
		}
		
		return text;
	}
	
	private List<HistoricTaskInstance> handleTasks(String sysUserId){
		PageView<HistoricTaskInstance> pageView = new PageView<HistoricTaskInstance>(10, 1);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("finished", "F");
		params.put("assigneeUserId", sysUserId);
		
		pageView.setOrderBy("TI.START_TIME_");
		pageView.setOrder("DESC");
		pageView = historicQueryService.queryHistoricTaskInstance(pageView, params);
		return pageView.getResults();
	}
	
	private List<HistoricTaskInstance> claimTasks(String sysUserId){
		PageView<HistoricTaskInstance> pageView = new PageView<HistoricTaskInstance>(10, 1);
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("finished", "F");
		params.put("assigned", "F");
		params.put("claimUserId", sysUserId);
		
		pageView.setOrderBy("TI.START_TIME_");
		pageView.setOrder("DESC");
		
		pageView = historicQueryService.queryHistoricTaskInstance(pageView, params);
		return pageView.getResults();
	}
	
	private List<HistoricTaskInstance> finishedTasks(String sysUserId){
		PageView<HistoricTaskInstance> pageView = new PageView<HistoricTaskInstance>(10, 1);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("finished", "T");
		params.put("assigneeUserId", sysUserId);
		
		pageView.setOrderBy("TI.START_TIME_");
		pageView.setOrder("DESC");
		pageView = historicQueryService.queryHistoricTaskInstance(pageView, params);
		return pageView.getResults();
	}
}
