package com.van.core.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.filter.GenericFilterBean;

import com.van.halley.bpm.service.HistoricQueryService;
import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.MailReceive;
import com.van.halley.db.persistence.entity.OutMsgInfo;
import com.van.halley.db.persistence.entity.User;
import com.van.service.MailReceiveService;
import com.van.service.OutMsgInfoService;

/**
 * @author Think
 * 获取一些在header 展示的数据
 */
public class PageContextAttributeFilter extends GenericFilterBean {
	private OutMsgInfoService outMsgInfoService;
	private MailReceiveService mailReceiveService;
	private HistoricQueryService historicQueryService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		User user = (User) req.getSession().getAttribute("userSession");
		if(user == null){
			filterChain.doFilter(request, response);
			return;
		}
		
		MailReceive filter = new MailReceive();
		filter.setStatus("未读");
		filter.setUserId(user.getId());
		req.getSession().setAttribute("unreadMails", mailReceiveService.queryForList(filter));
		
		OutMsgInfo msgInfo = new OutMsgInfo();
		msgInfo.setReceiver(user);
		msgInfo.setStatus("未读");
		req.getSession().setAttribute("unreadMsgs", outMsgInfoService.queryForList(msgInfo));
		
		PageView pageView = new PageView(1);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("assigneeUserId", user.getId());
		variables.put("finished", "F");
		//historicQueryService.queryHistoricTaskInstance(pageView, variables);
		
		req.getSession().setAttribute("handleTasks", historicQueryService.queryHistoricTaskInstance(pageView, variables).getResults());
		
		variables = new HashMap<String, Object>();
		variables.put("claimUserId", user.getId());
		variables.put("finished", "F");
		//historicQueryService.queryHistoricTaskInstance(pageView, variables);
		req.getSession().setAttribute("claimTasks", historicQueryService.queryHistoricTaskInstance(pageView, variables).getResults());
		filterChain.doFilter(request, response);
	}


	public MailReceiveService getMailReceiveService() {
		return mailReceiveService;
	}

	public void setMailReceiveService(MailReceiveService mailReceiveService) {
		this.mailReceiveService = mailReceiveService;
	}

	public OutMsgInfoService getOutMsgInfoService() {
		return outMsgInfoService;
	}

	public void setOutMsgInfoService(OutMsgInfoService outMsgInfoService) {
		this.outMsgInfoService = outMsgInfoService;
	}

	public HistoricQueryService getHistoricQueryService() {
		return historicQueryService;
	}

	public void setHistoricQueryService(HistoricQueryService historicQueryService) {
		this.historicQueryService = historicQueryService;
	}
	
}
