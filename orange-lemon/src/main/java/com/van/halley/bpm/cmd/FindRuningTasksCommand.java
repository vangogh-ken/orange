package com.van.halley.bpm.cmd;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.task.TaskQuery;

import com.van.halley.core.page.PageView;

public class FindRuningTasksCommand implements Command<Object>{
	private String assignee;
	private List<String> cadidateGroups;
	@Override
	public Map<String, Object> execute(CommandContext arg0) {
		Map<String, Object> map = new HashMap<String, Object>(0);
		PageView pageView = new PageView(1);
		TaskQuery taskQuery = Context.getProcessEngineConfiguration().getTaskService().createTaskQuery().taskAssignee(assignee);
		pageView.setTotalCount(taskQuery.count());;
		pageView.setResults(taskQuery.listPage(0, 10));
		map.put("handleTasks", pageView);
		
		pageView = new PageView(1);
		taskQuery = Context.getProcessEngineConfiguration().getTaskService().createTaskQuery().taskCandidateGroupIn(cadidateGroups);
		pageView.setTotalCount(taskQuery.count());;
		pageView.setResults(taskQuery.listPage(0, 10));
		map.put("claimTasks", pageView);
		return map;
	}
	
	public FindRuningTasksCommand(String assignee, List<String> cadidateGroups){
		this.assignee = assignee;
		this.cadidateGroups = cadidateGroups;
	}

}
