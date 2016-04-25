package com.van.halley.out.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.OutMsgInfo;
import com.van.halley.db.persistence.entity.User;
import com.van.halley.util.StringUtil;
import com.van.service.OutMsgInfoService;
import com.van.service.UserService;

@Controller
@RequestMapping(value = "/out/")
public class OutMsgInfoController {
	@Autowired
	private UserService userService;
	@Autowired
	private OutMsgInfoService outMsgInfoService;
	@Autowired
	private MessageHelper messageHelper;
	
	@RequestMapping(value = "msg-info-box")
	public String box(Model model, OutMsgInfo msgInfo, String sendDisplayName, 
			@RequestParam(value="tabType", required=false, defaultValue="tabHome")String tabType, 
			@ModelAttribute PageView pageView, HttpServletRequest request) {
		
		if("tabHome".equals(tabType)){
			pageView = new PageView(1);
			msgInfo = new OutMsgInfo();
			msgInfo.setReceiver((User)request.getSession().getAttribute("userSession"));
			PageView pageViewIn = outMsgInfoService.query(pageView, msgInfo);
			model.addAttribute("pageViewIn", pageViewIn);
			
			pageView = new PageView(1);
			msgInfo = new OutMsgInfo();
			msgInfo.setSender((User)request.getSession().getAttribute("userSession"));
			PageView pageViewOut = outMsgInfoService.query(pageView, msgInfo);
			model.addAttribute("pageViewOut", pageViewOut);
		}else if("talInbox".equals(tabType)){
			
		}
		
		return "/content/out/msg-info-box";
	}
	
	@RequestMapping(value = "msg-info-list-receive")
	public String list(Model model, OutMsgInfo msgInfo, String sendDisplayName,
			@ModelAttribute PageView pageView, HttpServletRequest request,
			HttpServletResponse response) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		if(msgInfo == null){
			msgInfo = new OutMsgInfo();
		}
		msgInfo.setReceiver((User)request.getSession().getAttribute("userSession"));
		
		if(!StringUtil.isNullOrEmpty(sendDisplayName)){
			String filterString = " SEND_USER_ID IN (SELECT ID FROM SYS_AUTH_USER WHERE DISPLAY_NAME LIKE '%" + sendDisplayName +"%') ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		
		pageView = outMsgInfoService.query(pageView, msgInfo);
		model.addAttribute("pageView", pageView);
		return "/content/out/msg-info-list-receive";
	}
	
	@RequestMapping(value = "msg-info-list-send")
	public String send(Model model, OutMsgInfo msgInfo, String receiveDisplayName,
			@ModelAttribute PageView pageView, HttpServletRequest request,
			HttpServletResponse response) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		if(msgInfo == null){
			msgInfo = new OutMsgInfo();
		}
		msgInfo.setSender((User)request.getSession().getAttribute("userSession"));
		
		if(!StringUtil.isNullOrEmpty(receiveDisplayName)){
			String filterString = " RECEIVE_USER_ID IN (SELECT ID FROM SYS_AUTH_USER WHERE DISPLAY_NAME LIKE '%" + receiveDisplayName +"%') ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		pageView = outMsgInfoService.query(pageView, msgInfo);
		model.addAttribute("pageView", pageView);
		return "/content/out/msg-info-list-send";
	}

	@RequestMapping(value = "msg-info-input")
	public String input(Model model, String id, String receiver, String userId) {
		OutMsgInfo item = null;
		if(id != null){
			item = outMsgInfoService.getById(id);
		}
		List<User> users = userService.getAll();
		model.addAttribute("users", users);
		model.addAttribute("item", item);
		if(receiver != null){
			System.out.println(receiver);
			model.addAttribute("receiver", receiver);
		}
		
		if(userId != null){
			model.addAttribute("receiver", userService.getById(userId).getDisplayName());
		}
		return "/content/out/msg-info-input";
	}

	@RequestMapping(value = "msg-info-save", method = RequestMethod.POST)
	public String add(OutMsgInfo msgInfo, @RequestParam(value="sendUserId", required=true)String sendUserId,
			@RequestParam(value="receiveUserIds", required=true)String receiveUserIds){
		if(!StringUtil.isNullOrEmpty(receiveUserIds)){
			String [] receiverIds = receiveUserIds.split(",");
			msgInfo.setSender(userService.getById(sendUserId));
			for(String receiverId : receiverIds){
				msgInfo.setReceiver(userService.getById(receiverId));
				msgInfo.setStatus("未读");
				msgInfo.setHandled("F");
				outMsgInfoService.add(msgInfo);
			}
		}
		
		return "redirect:msg-info-list-send.do";
	}


	@RequestMapping(value = "msg-info-remove")
	public String remove(Model model, String[] selectedItem,
			RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			outMsgInfoService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes, "删除成功");
		return "redirect:msg-info-list-send.do";
	}

	@RequestMapping(value = "msg-info-delete")
	public String delete(Model model, String id,
			RedirectAttributes redirectAttributes) {
		outMsgInfoService.delete(id);
		messageHelper.addFlashMessage(redirectAttributes, "删除成功");
		return "redirect:msg-info-list-send.do";
	}
	
	@RequestMapping(value = "msg-info-mark-read")
	public String mark(Model model, String id,
			RedirectAttributes redirectAttributes) {
		OutMsgInfo msgInfo = outMsgInfoService.getById(id);
		msgInfo.setStatus("已读");
		outMsgInfoService.modify(msgInfo);
		messageHelper.addFlashMessage(redirectAttributes, "标记成功");
		return "redirect:msg-info-list-receive.do";
	}
	
	@RequestMapping(value = "msg-info-reply")
	public String reply(Model model, String id) {
		OutMsgInfo msgInfo = outMsgInfoService.getById(id);
		if(!msgInfo.getStatus().equals("已读")){
			msgInfo.setStatus("已读");
			outMsgInfoService.modify(msgInfo);
			
		}
		model.addAttribute("msgInfo", msgInfo);
		return "/content/out/msg-info-reply";
	}
	
	@RequestMapping(value = "msg-info-reply-save")
	public String replySave(Model model, OutMsgInfo msgInfo, 
			@RequestParam(value="replayMsgId", required=true)String replayMsgId, 
			@RequestParam(value="sendUserId", required=true)String sendUserId, 
			@RequestParam(value="receiveUserId", required=true)String receiveUserId,
			 RedirectAttributes redirectAttributes) {
		OutMsgInfo replyMsgInfo = outMsgInfoService.getById(replayMsgId);
		replyMsgInfo.setStatus("已回复");
		outMsgInfoService.modify(replyMsgInfo);
		
		msgInfo.setSender(userService.getById(sendUserId));
		msgInfo.setReceiver(userService.getById(receiveUserId));
		msgInfo.setStatus("未读");
		msgInfo.setHandled("F");
		outMsgInfoService.add(msgInfo);
		messageHelper.addFlashMessage(redirectAttributes, "回复成功");
		return "redirect:msg-info-list-receive.do";
	}
	
	@RequestMapping(value = "msg-info-done-batch-reply")
	@ResponseBody
	public String doneBatchReply(String content, String[] outMsgInfoId){
		if(outMsgInfoService.doneBatchReply(content, outMsgInfoId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@RequestMapping(value = "msg-info-done-settle-read")
	@ResponseBody
	public String doneSettleRead(String[] outMsgInfoId){
		if(outMsgInfoService.doneSettleRead(outMsgInfoId)){
			return "success";
		}else{
			return "error";
		}
	}
}
